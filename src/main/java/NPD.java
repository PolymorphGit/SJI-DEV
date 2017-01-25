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
	
	public String Debug;
	
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
			Debug = "Get Id, ";
			Name = rs.getString("Name");
			Debug += "Get Name, ";
			LaunchDate = rs.getDate("Launching_Date__c");
			Debug += "Get Launch Date, ";
			Plant = rs.getString("Plant__c");
			Debug += "Get Plant, ";
			PlantCode =  Plant.substring(0, 4);
			Debug += "Set Plant Code, ";
			ProjectName = rs.getString("Project_Name__c");
			Debug += "Get Project Name, ";
			ProjectType = rs.getString("Project_Type__c");
			Debug += "Get Project Type, ";
			
			accId = rs.getString("Account__c");
			Debug += "Set Account Id, ";
			listFG = new ArrayList<FG>();
			Debug += "new ArrayList, ";
			LoadFG();
			Debug += "Load FG Finish";
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
			ResultSet rs = DataManager.Query("SELECT * FROM salesforce.Sourcing__c where NPD__c = '" + Id + "'");
			while (rs.next()) 
	        {
				FG newFG = new FG(rs);
				newFG.linkNPD(this);
				listFG.add(newFG);

				Debug += newFG.Id + ", ";
	        }
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
