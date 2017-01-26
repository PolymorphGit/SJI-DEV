import java.net.URISyntaxException;
import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

public class StandardConvertUnit {

	public String Id;
	public String AlternativeUnit;
	public Double AlternativeRate;
	public String BaseUnit;
	public Double BaseRate;
	
	public StandardConvertUnit(ResultSet rs)
	{
		LoadData(rs);
	}
	
	public StandardConvertUnit(String id)
	{
		ResultSet rs = DataManager.Query("SELECT * FROM salesforce.Standard_Convert_Unit__c where SFID='" + id + "'");
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
			AlternativeUnit = rs.getString("Alternative_Unit__c");
			AlternativeRate = rs.getDouble("Alternative_Rate__c");
			BaseUnit = rs.getString("Base_Unit__c");
			BaseRate = rs.getDouble("Base_Rate__c");
		} 
		catch (SQLException e) {}
	}
	
}
