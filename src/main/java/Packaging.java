import java.net.URISyntaxException;
import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

public class Packaging {

	public String Id;
	public String Name;
	public String PackagingName;
	public String BaseUnitOfMeasure;
	public String OrderUnit;
	public Double TargetCost;
	public Double Amortization;
	public Double DecorationCost;
	public Double ToolingCost;
	public Double MOQ;
	public Double MinimumOrder;
	
	public Double SelectMOQ;
	public Double SelectCost;
	
	public Boolean IsFree;
	public Double CountOption;
	public Double CountOptionMOQ;
	
	public String PackagingTypeId;
	public MasterPackagingType PackagingType;
	public String ProductInfoId;
	public ProductInfo ProductInfo;
	public String npdId;
	public NPD npd;
	
	public Packaging(ResultSet rs)
	{
		LoadData(rs);
	}
	
	public Packaging(String id)
	{
		ResultSet rs = DataManager.Query("SELECT * FROM salesforce.Packaging__c where SFID='" + id + "'");
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
			PackagingName = rs.getString("Packaging_Name__c");
			BaseUnitOfMeasure = rs.getString("Base_Unit_of_Measure__c");
			OrderUnit = rs.getString("Order_Unit__c");
			TargetCost = rs.getDouble("Costing__c");
			Amortization = rs.getDouble("Amortization__c");
			DecorationCost = rs.getDouble("Decoration_Cost__c");
			ToolingCost = rs.getDouble("Tooling_Cost__c");
			MOQ = rs.getDouble("MOQ__c");
			MinimumOrder = rs.getDouble("Minimum_Order__c");
			
			SelectMOQ = rs.getDouble("Selected_MOQ__c");
			SelectCost = rs.getDouble("Selected_Cost__c");
			
			IsFree = rs.getBoolean("Is_Free__c"); 
			CountOption = rs.getDouble("Count_Option__c");
			CountOptionMOQ = rs.getDouble("Count_Option_MOQ__c");
			
			PackagingTypeId = rs.getString("Master_Packaging_Type__c");
			ProductInfoId = rs.getString("Product_Information_Record__c");
			npdId = rs.getString("NPD__c");
		} 
		catch (SQLException e) {}
	}
}
