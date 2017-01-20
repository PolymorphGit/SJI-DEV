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
	public String accId;
	
	
	public ExchangeRate(ResultSet rs)
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
			accId = rs.getString("Account__c");
		} 
		catch (SQLException e) {}

	}

}
