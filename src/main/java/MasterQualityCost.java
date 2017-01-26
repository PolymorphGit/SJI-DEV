import java.net.URISyntaxException;
import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

public class MasterQualityCost {

	public String Id;
	public String Name;
	public Double Cost;
	
	public MasterQualityCost(ResultSet rs)
	{
		LoadData(rs);
	}
	
	public MasterQualityCost(String id)
	{
		ResultSet rs = DataManager.Query("SELECT * FROM salesforce.Master_Quality_Cost__c where SFID='" + id + "'");
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
			Cost = rs.getDouble("Cost__c");
		} 
		catch (SQLException e) {}
	}
}
