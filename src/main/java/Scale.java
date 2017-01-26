import java.net.URISyntaxException;
import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

public class Scale {

	public String Id;
	public Double Count;
	public String Currency;
	public Double Customs;
	public Double Freight;
	public Double Insurance;
	public Double Shipping;
	public Double Inventory;
	public Double Price;
	public Double Quantity;
	public Double LandedCostPerUnit;
	public Double UnitPriceAfterLandedCost;
	
	public Boolean Active;
	
	public String ProductInfoId;
	public ProductInfo ProductInfo;
	
	public Scale(ResultSet rs)
	{
		LoadData(rs);
	}
	
	public Scale(String id)
	{
		ResultSet rs = DataManager.Query("SELECT * FROM salesforce.Scale__c where SFID='" + id + "'");
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
			Count = rs.getDouble("Count__c");
			Currency = rs.getString("CurrencyIsoCode");
			Customs = rs.getDouble("Customs__c");
			Freight = rs.getDouble("Freight__c");
			Insurance = rs.getDouble("Insurance__c");
			Shipping = rs.getDouble("Shipping__c");
			Inventory = rs.getDouble("Inventory__c");
			Price = rs.getDouble("Price__c");
			Quantity = rs.getDouble("Quantity__c");
			
			//LandedCostPerUnit = 
			
			Active = rs.getBoolean("Active__c");
			
			ProductInfoId = rs.getString("Product_Catalog__c");
		} 
		catch (SQLException e) {}
	}
	
	public String getData()
	{
		return "Currency: " + Currency + ", Price: " + Price;
	}
}
