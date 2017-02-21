import java.net.URISyntaxException;
import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

public class BOMDetail {

	public String Id;
	public String Name;
	public Double Percent;
	public String BaseUnit;
	public String Plant;
	public Double Scrap;
	public String MaterialId;
	public Material material;
	public String ProductInfoId;
	public ProductInfo productInfo;
	
	public String bomHeaderId;
	public BOMHeader bomHeader;
	
	public BOMDetail(ResultSet rs)
	{
		LoadData(rs);
	}
	
	public BOMDetail(String id)
	{
		ResultSet rs = DataManager.Query("SELECT * FROM salesforce.Leaf_BOM__c where SFID='" + id + "'");
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
			Percent = rs.getDouble("Base_Quantity__c");
			BaseUnit = rs.getString("Base_Unit__c");
			Plant = rs.getString("Plant__c");
			Scrap = rs.getDouble("Scrap__c");
			
			MaterialId = rs.getString("Material__c");
			LoadMaterial();
			ProductInfoId = rs.getString("Product_Catalog__c");
			SelectProductInfo();
			
			if(rs.getString("Master_BOM_Header__c") != null)
			{
				bomHeaderId = rs.getString("Master_BOM_Header__c");
			}
			else
			{
				bomHeaderId = rs.getString("BOM_Header__c");
			}
		} 
		catch (SQLException e) {}
	}
	
	private void LoadMaterial()
	{
		try 
		{
			ResultSet rs = DataManager.Query("SELECT * FROM salesforce.Raw_Material__c where SFID = '" + MaterialId + "'");
			while (rs.next()) 
	        {
				Material newMaterial = new Material(rs);
				material = newMaterial;
	        }
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void SelectProductInfo()
	{
		for(ProductInfo pro : material.listProInfo)
		{
			//if(pro.Id.equals(ProductInfoId))
			/*
			if(pro.Plant != null && pro.Plant == Plant && pro.ProcumentPlant == Plant)
			{
				productInfo = pro;
			}
			else if(pro.Plant == bomHeader.Plant)
			{
				
			}
			*/
		}
	}
	
	public void linkBOMHeader(BOMHeader h)
	{
		if(h.Id.equals(bomHeaderId))
		{
			bomHeader = h;
		}
	}
}
