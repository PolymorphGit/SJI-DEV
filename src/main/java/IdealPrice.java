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

	private String QueryCmd = "";
	
	public IdealPrice(String listNPD)
	{	
		DictNPD = new HashMap<String, NPD>();		
        LoadData(DataManager.Query("SELECT * FROM salesforce.NPD__c where NPD__c in (" + listNPD + ")"));

        QueryCmd = "SELECT * FROM salesforce.NPD__c where NPD__c in (" + listNPD + ")";
	}
	
	public IdealPrice(Account newAcc)
	{
		account = newAcc;
		DictNPD = new HashMap<String, NPD>();
        LoadData(DataManager.Query("SELECT * FROM salesforce.NPD__c where Account_Name__c='" + newAcc.Id + "'"));

        QueryCmd = "SELECT * FROM salesforce.NPD__c where Account_Name__c='" + newAcc.Id + "'";
	}
	
	private void LoadData(ResultSet rs)
	{
		String listID = "";
		try {
			while (rs.next()) 	
			{
				NPD newNPD = new NPD(rs);
				DictNPD.put(newNPD.Id, newNPD);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public String getResult()
	{
		String output = "Query: " + QueryCmd + "<br/> ";
		for(NPD npd : DictNPD.values())
		{
			output += "NPD Id: " + npd.Id + "<br/>";
			
			for(FG fg : npd.listFG)
			{
				//output += " - FG Name: " + fg.Name + ", MOQ: " + fg.MOQ1 + ", Annual: " + fg.AnnualVolume + ", Launch:" + fg.LaunchVolume + "<br/>";
			}
			
		}
		return output;
		//return "Success";
	}
}
