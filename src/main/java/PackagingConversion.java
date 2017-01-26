import java.net.URISyntaxException;
import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

public class PackagingConversion {

	public String Id;
	public String AlternativeUnit;
	public Double AlternativeRate;
	public String BaseUnit;
	public Double BaseRate;	
	
	public String PackagingId;
	public Packaging Packaging;
	
	public PackagingConversion(ResultSet rs)
	{
		LoadData(rs);
	}
	
	public PackagingConversion(String id)
	{
		ResultSet rs = DataManager.Query("SELECT * FROM salesforce.Packaging_Conversion__c where SFID='" + id + "'");
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
			AlternativeRate = rs.getDouble("Alternative_Rate__c");
			AlternativeUnit = rs.getString("Alternative_Unit__c");
			BaseRate = rs.getDouble("Base_Rate__c");
			BaseUnit = rs.getString("Base_Unit__c");
			
			PackagingId = rs.getString("Packaging__c");
		} 
		catch (SQLException e) {}
	}
	
}
