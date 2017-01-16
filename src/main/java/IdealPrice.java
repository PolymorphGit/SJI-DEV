import java.net.URISyntaxException;
import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

import javax.management.Query;

import com.heroku.sdk.jdbc.DatabaseUrl;

class IdealPrice 
{
	private Connection connection = null;
	private Statement stmt = null;
	private HashMap<String, NPD> DictNPD;
	private Account account;
	
	private Integer count = 0;
	private String QueryCmd = "";
	
	public IdealPrice(HashMap<String, NPD> newNPD)
	{
		ConnectDB();		
		DictNPD = newNPD;
		
		LoadData();
	}
	
	public IdealPrice(Account newAcc)
	{
		ConnectDB();		
		account = newAcc;
		DictNPD = new HashMap<String, NPD>();
		
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM salesforce.NPD__c where Account_Name__c='" + newAcc.Id + "'");
			
	        while (rs.next()) 	
	        {
	        	NPD newNPD = new NPD(rs);
	        	DictNPD.put(newNPD.Id, newNPD);
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
		
		for(NPD npd : DictNPD.values())
		{
			listID += "'" + npd.Id + "', ";
		}
		listID = listID.length() > 2 ? listID.substring(0, listID.length() - 2) : listID;
		
		QueryCmd = "SELECT * FROM salesforce.Sourcing__c where NPD__c in (" + listID + ")";
		try 
		{
			count = 0;
			ResultSet rs = stmt.executeQuery("SELECT * FROM salesforce.Sourcing__c where NPD__c in (" + listID + ")");
			while (rs.next()) 
	        {
				FG newFG = new FG(rs);
				DictNPD.get(newFG.npdId).AddFG(newFG);
	        	count++;
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
		String output = "Query: " + QueryCmd + "<br/> " + "Count: " + count + "<br/> ";
		for(NPD npd : DictNPD.values())
		{
			output += "NPD Id: " + npd.Id + "<br/>";
			/*
			for(FG fg : npd.listFG)
			{
				output += " - FG Name: " + fg.Name + ", MOQ: " + fg.MOQ1 + ", Annual: " + fg.AnnualVolume + ", Launch:" + fg.LaunchVolume + "<br/>";
			}
			*/
		}
		return output;
		//return "Success";
	}
}
