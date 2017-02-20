import java.net.URISyntaxException;
import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

public class MasterWareHouse {

	public String Id;
	public String Name;
	public Double CostPerPallet;
	
	public MasterWareHouse(ResultSet rs)
	{
		LoadData(rs);
	}
	
	public MasterWareHouse(String id)
	{
		ResultSet rs = DataManager.Query("SELECT * FROM salesforce.Master_Ware_House__c where SFID='" + id + "'");
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
			CostPerPallet = rs.getDouble("Cost_per_Pallet__c");
		} 
		catch (SQLException e) {}
	}
	
	public String getData()
	{
		return "Name: " + Name + ", Cost per Pallet: " + CostPerPallet;
	}
}
