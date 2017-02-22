import java.net.URISyntaxException;
import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

public class PackagingOptionMOQ {

	public String Id;
	public String Name;
	public Double MOQ;
	public Double Amortization;
	public Double Decoration;
	public Double Tooling;
	public Double Cost;
	public String OrderUnit;
	
	public String packagingOptionId;
	public PackagingOption packagingOption;
	
	public PackagingOptionMOQ(ResultSet rs)
	{
		LoadData(rs);
	}
	
	public PackagingOptionMOQ(String id)
	{
		ResultSet rs = DataManager.Query("SELECT * FROM salesforce.Packaging_Option_MOQ__c where SFID='" + id + "'");
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
			MOQ = rs.getDouble("MOQ__c");
			Amortization = rs.getDouble("Amortization__c");
			Decoration = rs.getDouble("Decoration_Cost__c");
			Tooling = rs.getDouble("Tooling_Cost__c");
			Cost = rs.getDouble("Cost__c");
			OrderUnit = rs.getString("Order_Unit__c");
			
			packagingOptionId = rs.getString("Packaging_Option__c");
		} 
		catch (SQLException e) {}
	}
	
	public void linkPackagingOption(PackagingOption po)
	{
		if(po.Id.equals(packagingOptionId))
		{
			packagingOption = po;
		}
	}
}
