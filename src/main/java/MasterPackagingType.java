import java.net.URISyntaxException;
import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

public class MasterPackagingType {

	public String Id;
	public String PackagingType;
	public String PrimaryPackagingType;
	public String SecondaryPackagingType;
	public Double Scrap;
	public String UnitMeasure;
	
	public MasterPackagingType(ResultSet rs)
	{
		LoadData(rs);
	}
	
	public MasterPackagingType(String id)
	{
		ResultSet rs = DataManager.Query("SELECT * FROM salesforce.Master_Packaging_Type__c where SFID='" + id + "'");
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
			PackagingType = rs.getString("Product_Type__c");
			PrimaryPackagingType = rs.getString("Primary_Packaging_Type__c");
			SecondaryPackagingType = rs.getString("Product_Type_Formula_del__c");
			Scrap = rs.getDouble("Scrap__c");
			UnitMeasure = rs.getString("Unit_of_Measure__c");
		} 
		catch (SQLException e) {}
	}
}
