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
	private String listID = "";
	
	public IdealPrice(String listNPD)
	{	
		DictNPD = new HashMap<String, NPD>();		
		QueryCmd = "SELECT * FROM salesforce.NPD__c where NPD__c in (" + listNPD + ")";
        LoadData(QueryCmd);
	}
	
	public IdealPrice(Account newAcc)
	{
		account = newAcc;
		DictNPD = new HashMap<String, NPD>();

        QueryCmd = "SELECT * FROM salesforce.NPD__c where Account_Name__c='" + newAcc.Id + "'";
        LoadData(QueryCmd);
	}
	
	private void LoadData(String cmd)
	{
		try {
			ResultSet rs = DataManager.Query(cmd);
			Integer i = 1;
			listID = "ID: "; 
			while (rs.next()) 	
			{	
				listID += " No." + i + " ";
				NPD newNPD = new NPD(rs);
				DictNPD.put(newNPD.Id, newNPD);
				listID += newNPD.Id + ", ";
				i += 1;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			listID += "Catch : " + e1.getMessage();
			e1.printStackTrace();
		}
		listID += "<br/>";
	}
	
	public String getResult()
	{
		String output = "Query: " + QueryCmd + "<br/> ";
		output += listID;
		for(NPD npd : DictNPD.values())
		{
			output += "NPD Id: " + npd.Id + "<br/>";
			
			for(FG fg : npd.listFG)
			{
				output += " - FG Name: " + fg.Name + ", MOQ: " + fg.MOQ1 + ", Annual: " + fg.AnnualVolume + ", Launch:" + fg.LaunchVolume + "<br/>";
			}
			
		}
		return output;
		//return "Success";
	}
}
