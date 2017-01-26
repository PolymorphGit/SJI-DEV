import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

import com.heroku.sdk.jdbc.DatabaseUrl;


public class ExchangeRate
{
	public String Id;
	public Double RatioFrom;
	public Double BuyRate;
	public String Currency;
	public String Name;
	public Date StartDate;
	public Date EndDate ;
	public Double SellRate;
	public Double RatioTo;
	public Double BuyRateAfterRatio;
	public Double SellRateAfterRatio;
	public String accId;
	
	
	public ExchangeRate(ResultSet rs)
	{
		LoadData(rs);
	}
	
	public ExchangeRate(String id)
	{
		ResultSet rs = DataManager.Query("SELECT * FROM salesforce.Exchange_Rate__c where SFID='" + id + "'");
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
		try 
		{
			Id = rs.getString("SFID");
			RatioTo = rs.getDouble("Ratio_To__c");
			RatioFrom = rs.getDouble("Ratio_From__c");
			BuyRate = rs.getDouble("Buy_Rate__c");
			Name = rs.getString("Name");
			StartDate = rs.getDate("Start_Date__c");
			EndDate = rs.getDate("End_Date__c");
			Currency = rs.getString("CurrencyIsoCode");
			SellRate = rs.getDouble("Sell_Rate__c");
			
			BuyRateAfterRatio = RatioFrom == 0 ? BuyRate : (RatioTo / RatioFrom) * BuyRate;
			SellRateAfterRatio = RatioFrom == 0 ? SellRate : (RatioTo / RatioFrom) * SellRate;
		} 
		catch (SQLException e) {}
	}
	
	public String getData()
	{
		return "Currency: " + Currency + ", Buy: " + BuyRateAfterRatio + ", Sell: "+ SellRateAfterRatio;
	}
}
