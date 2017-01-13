import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

import com.heroku.sdk.jdbc.DatabaseUrl;

public class Account 
{
	public String Id;
	public String Name;
	public String Type;
	public String AccountSource;
	public String AccountStatus;
	public Boolean IsActive;
	public Double AnnualRevenue;
	public String BatchUpload;
	public String Branch;
	public String Brand;
	public String BusinessType;
	public Double CreditLimit;
	public String CustomerCode;
	public String CustomerStatus;
	
	
	public Account(ResultSet rs)
	{
		try 
		{
			Id = rs.getString("Id");
			Name = rs.getString("Name");
			Type = rs.getString("Type");
			AccountSource = rs.getString("AccountSource");
			AccountStatus = rs.getString("Account_Status__c");
			IsActive = rs.getBoolean("Active__c");
			AnnualRevenue = rs.getDouble("AnnualRevenue");
			BatchUpload = rs.getString("Batch_Upload_Type__c");
			Branch = rs.getString("Branch__c");
			BusinessType = rs.getString("Business_Type__c");
			CreditLimit = rs.getDouble("Credit_Limit__c");
			CustomerCode = rs.getString("Brand__c");
			CustomerStatus = rs.getString("Brand__c");
		} 
		catch (SQLException e) {}
		
	}
		
}