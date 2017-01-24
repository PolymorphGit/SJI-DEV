import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

import com.heroku.sdk.jdbc.DatabaseUrl;

public class NPD {

	public String Id;
	public String Name;
	public Date LaunchDate;
	public String Plant;
	public String PlantCode;
	public String ProjectName;
	public String ProjectType;
	
	public String accId;
	public Account account;
	public ArrayList<FG> listFG;
	
	public NPD(ResultSet rs)
	{		
		LoadData(rs);
	}
	
	public NPD(String NPDId)
	{
		ResultSet rs = DataManager.Query("SELECT * FROM salesforce.NPD__c where Account_Name__c='" + NPDId + "'");
		try {
			if(rs.next())
			{
				LoadData(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void LoadData(ResultSet rs)
	{
		try {
			Id = rs.getString("SFID");
			Name = rs.getString("Name");
			LaunchDate = rs.getDate("Launching_Date__c");
			Plant = rs.getString("Plant__c");
			PlantCode =  Plant.substring(0, 4);
			ProjectName = rs.getString("Project_Name__c");
			ProjectType = rs.getString("Project_Type__c");
			
			accId = rs.getString("Account__c");
			LoadFG();
		} 
		catch (SQLException e) {}
	}
	
	public void linkAccount(Account acc)
	{
		if(accId == acc.Id)
		{
			account = acc;
		}
	}
	
	private void LoadFG()
	{
		try 
		{
			listFG = new ArrayList<FG>();
			ResultSet rs = DataManager.Query("SELECT * FROM salesforce.Sourcing__c where NPD__c = '" + Id + "'");
			while (rs.next()) 
	        {
				//FG newFG = new FG(rs);
				//newFG.linkNPD(this);
				//listFG.add(newFG);
	        }
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
