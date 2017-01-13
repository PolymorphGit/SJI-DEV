import java.net.URISyntaxException;
import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

import com.heroku.sdk.jdbc.DatabaseUrl;

class IdealPrice 
{
	private Connection connection = null;
	private Statement stmt = null;
	private ArrayList<NPD> listNPD;
	private Account account;
	private ArrayList<FG> listFG;
	
	public IdealPrice(ArrayList<NPD> newNPD)
	{
		ConnectDB();		
		listNPD = newNPD;
		
		LoadData();
	}
	
	public IdealPrice(Account newAcc)
	{
		ConnectDB();		
		account = newAcc;
		listNPD = new ArrayList<NPD>();
		
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM salesforce.NPD__c where Account_Name__c='" + newAcc.Id + "'");
			
	        while (rs.next()) 
	        {
	        	listNPD.add(new NPD(rs));
	        }
	        
	        LoadData();
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void ConnectDB()
	{
		try {
			connection = DatabaseUrl.extract().getConnection();
			stmt = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void LoadData()
	{
		String listID = "";
		for(NPD npd : listNPD)
		{
			listID += npd.Id + ", ";
		}
		try 
		{
			ResultSet rs = stmt.executeQuery("SELECT * FROM salesforce.FG__c where NPD__c in (" + listID + ")");
			while (rs.next()) 
	        {
	        	listFG.add(new FG(rs));
	        }
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getResult()
	{
		String output = "";
		for(FG fg : listFG)
		{
			output += fg.Name + "(" + fg.AnnualVolume + "), ";
		}
		
		return output;
		//return "Success";
	}
}
