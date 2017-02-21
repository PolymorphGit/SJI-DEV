import java.net.URISyntaxException;
import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

public class BOMHeader {

	public String Id;
	public String Name;
	public Double BaseQTY;
	public String BaseUnit;
	public String FormulationNumber;
	public Double SG = 0.0;
	public Double TotalMovingPrice;
	public String Status;
	public String Plant;
	
	public String BulkId;
	public Bulk bulk;
	
	public Boolean isMaster = false;
	public String MaterialId;
	public Material Material;
	
	public ArrayList<BOMDetail> listBOMDetail;
	
	public BOMHeader(ResultSet rs)
	{
		LoadData(rs);
	}
	
	public BOMHeader(String id)
	{
		QueryData("SELECT * FROM salesforce.BOM_Header__c where SFID='" + id + "'");
	}
	
	public BOMHeader(Material mat)
	{
		isMaster = true;
		Material = mat;
		MaterialId = mat.Id;
		QueryData("SELECT * FROM salesforce.Master_BOM_Header__c where Material__c='" + mat.Id + "'");
	}
	
	private void QueryData(String cmd)
	{
		ResultSet rs = DataManager.Query(cmd);
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
			BaseQTY = rs.getDouble("Base_Quantity__c");
			Plant = rs.getString("Plant__c");
			
			if(isMaster == false)
			{
				BulkId = rs.getString("Formula__c");
				BaseUnit = rs.getString("Base_Unit__c");
				FormulationNumber = rs.getString("Formulation_Number__c");
				TotalMovingPrice = rs.getDouble("Total_Moving_Price_KG__c");
				SG = rs.getDouble("SG__c");
				Status = rs.getString("Status__c");
			}
			else
			{
				BaseUnit = rs.getString("Base_Unit_of_Measure__c");
				Status = rs.getString("BOM_Status__c");
			}
			
			listBOMDetail = new ArrayList<BOMDetail>();
			LoadBOMDetail();
		} 
		catch (SQLException e) {}
	}

	private void LoadBOMDetail()
	{
		try 
		{
			ResultSet rs = DataManager.Query("SELECT * FROM salesforce.Leaf_BOM__c where BOM_Header__c = '" + Id + "' or Master_BOM_Header__c = '" + Id + "'");
			while (rs.next()) 
	        {
				BOMDetail newBOMDetail = new BOMDetail(rs);
				newBOMDetail.linkBOMHeader(this);
				listBOMDetail.add(newBOMDetail);
	        }
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void linkBulk(Bulk b)
	{
		if(b.Id.equals(BulkId))
		{
			bulk = b;
			if(SG != 0)
			{
				SG = b.RDHierarchy.SGMax;
			}
		}
	}
}
