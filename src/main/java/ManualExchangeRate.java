import java.net.URISyntaxException;
import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

public class ManualExchangeRate {

	public String Id;
	public String Currency;
	public Double BuyRate;
	public Double SellRate;
	public Double RatioForm;
	public Double RatioTo;
	public Double BuyRateAfterRatio;
	public Double SellRateAfterRatio;
	public Boolean Approved;
	
	public String npdId;
	
	public ManualExchangeRate(ResultSet rs)
	{
		LoadData(rs);
	}
	
	public ManualExchangeRate(String id)
	{
		ResultSet rs = DataManager.Query("SELECT * FROM salesforce.Manual_Exchange_Rate__c where SFID='" + id + "'");
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
			Currency = rs.getString("Currency__c");
			BuyRate = rs.getDouble("Buy_Rate__c");
			SellRate = rs.getDouble("Sell_Rate__c");
			RatioForm = rs.getDouble("Ratio_From__c");
			RatioTo = rs.getDouble("Ratio_To__c");
			
			BuyRateAfterRatio = RatioForm == 0 ? BuyRate : BuyRate * (RatioTo / RatioForm);
			SellRateAfterRatio = RatioForm == 0 ? SellRate : SellRate * (RatioTo / RatioForm);
			
			npdId = rs.getString("NPD__c");
		} 
		catch (SQLException e) {}
	}
	
	public String getData()
	{
		return "Currency: " + Currency + ", Buy: " + BuyRateAfterRatio + ", Sell: " + SellRateAfterRatio;
	}
}
