import java.net.URISyntaxException;
import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

public class Material {

	public String Id;
	public String MaterialCode;
	public String MaterialName;
	public String BaseUnit;
	public Double MOQ;
	
	public ArrayList<ProductInfo> listProInfo;
	
	public Material(ResultSet rs)
	{
		LoadData(rs);
	}
	
	public Material(String id)
	{
		ResultSet rs = DataManager.Query("SELECT * FROM salesforce.Raw_Material__c where SFID='" + id + "'");
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
			MaterialCode = rs.getString("Raw_Material_Code__c");
			MaterialName = rs.getString("Raw_Material_Name_TH__c");
			BaseUnit = rs.getString("Base_Unit__c");
			MOQ = rs.getDouble("MOQ__c");
			
			listProInfo = new ArrayList<ProductInfo>();
			//LoadProductInfo();
		} 
		catch (SQLException e) {}
	}
	
	private void LoadProductInfo()
	{
		try 
		{
			ResultSet rs = DataManager.Query("SELECT * FROM salesforce.Product_Catalog__c where raw_material__c = '" + Id + "'");
			while (rs.next()) 
	        {
				ProductInfo newProductInfo = new ProductInfo(rs);
				newProductInfo.linkMaterial(this);
				listProInfo.add(newProductInfo);
	        }
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getResult()
	{
		String output; // = "Query: " + QueryCmd + "<br/> ";
		output = MaterialName + " (" + MaterialCode + ")<br/>";
		/*
		for(ProductInfo pro : listProInfo)
		{
			output += "Product Info Id: " + pro.Id + ", Plant: " + pro.Plant + "<br/>";
			
			for(Scale sc : pro.listScale)
			{
				output += " - Qty: " + sc.Quantity + ", Price: " + sc.UnitPriceAfterLandedCost + "<br/>";
			}
			
		}
		*/
		return output;
		//return "Success";
	}
	
	
}
