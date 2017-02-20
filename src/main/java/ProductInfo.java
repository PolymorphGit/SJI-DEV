import java.net.URISyntaxException;
import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

public class ProductInfo {

	public String Id;
	public Double MovingPrice;
	public String OrderUnit;
	public Double PackSize;
	public String Plant;
	public String ProcumentPlant;
	public Double StandardPrice;
	public Double StandardQuantity;
	
	public String MaterialId;
    public Material Material;
    
    public ArrayList<Scale> listScale;
	
	public ProductInfo(ResultSet rs)
	{
		LoadData(rs);
	}
	
	public ProductInfo(String id)
	{
		ResultSet rs = DataManager.Query("SELECT * FROM salesforce.Product_Catalog__c where SFID='" + id + "'");
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
			MovingPrice = rs.getDouble("Moving_Price__c");
			OrderUnit = rs.getString("Order_Unit__c");
			PackSize = rs.getDouble("Pack_Size__c");
			Plant = rs.getString("Plant__c");
			ProcumentPlant = rs.getString("Procument_Plant__c");
			StandardPrice = rs.getDouble("Standard_Price__c");
			StandardQuantity = rs.getDouble("Standard_Quantity__c");
			
			MaterialId = rs.getString("Raw_Material__c");
			
			listScale = new ArrayList<Scale>();
			LoadScales();
		} 
		catch (SQLException e) {}
	}
	
	public void linkMaterial(Material mat)
	{
		if(mat.Id.equals(MaterialId))
		{
			Material = mat;
		}
	}
	
	private void LoadScales()
	{
		try 
		{
			ResultSet rs = DataManager.Query("SELECT * FROM salesforce.Scale__c where product_catalog__c = '" + Id + "'");
			while (rs.next()) 
	        {
				Scale newScale = new Scale(rs);
				newScale.linkProductInfo(this);
				listScale.add(newScale);
	        }
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
