import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

import com.heroku.sdk.jdbc.DatabaseUrl;

public class FG 
{
	public String Id;
	public String Name;
	public Double AnnualVolume;
	public Double LaunchVolume;
	public Double MOQ1;
	public Double MOQ2;
	public Double MOQ3;
	public Double UnitPackSize;
	
	public String npdId;
	public NPD npd;
	
	public FG(ResultSet rs)
	{
		LoadData(rs);
	}
	
	public FG(String id)
	{
		ResultSet rs = DataManager.Query("SELECT * FROM salesforce.Sourcing__c where NPD__c in (" + id + ")");
		LoadData(rs);
	}
	
	private void LoadData(ResultSet rs)
	{
		try {
			Id = rs.getString("SFID");
			Name = rs.getString("Name");
			AnnualVolume = rs.getDouble("Annual_Volume_Summary__c");
			LaunchVolume =  rs.getDouble("Launch__c");
			MOQ1 = rs.getDouble("Finished_Goods_MOQ_Max__c");
			MOQ2 = rs.getDouble("Finished_Goods_MOQ_Min__c");
			MOQ3 = rs.getDouble("Finished_Goods_MOQ_Option_3__c");
			UnitPackSize = rs.getDouble("Unit_Pack_Size__c");
			
			npdId = rs.getString("NPD__c");
		} 
		catch (SQLException e) {}
	}
	
	public void linkNPD(NPD newNPD)
	{
		if(npdId == newNPD.Id)
		{
			npd = newNPD;
		}
	}
}
