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
		try {
			Id = rs.getString("SFID");
			Name = rs.getString("Name");
			LaunchDate = rs.getDate("Launching_Date__c");
			Plant = rs.getString("Plant__c");
			PlantCode =  Plant.substring(0, 4);
			ProjectName = rs.getString("Project_Name__c");
			ProjectType = rs.getString("Project_Type__c");
			
			accId = rs.getString("Account__c");
			listFG = new ArrayList<FG>();
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
	
	public void AddFG(FG newFG)
	{
		if(listFG == null)
		{
			listFG = new ArrayList<FG>();
		}
		listFG.add(newFG);
		//newFG.linkNPD(this);
	}
}
