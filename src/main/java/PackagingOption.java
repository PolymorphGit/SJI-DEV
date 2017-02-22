import java.net.URISyntaxException;
import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

public class PackagingOption {

	public String Id;
	public String Name;
	public String ColorFinish;
	public String Status;
	
	public String PackagingId;
	public Packaging packaging;
	
	public ArrayList<PackagingOptionMOQ> listMOQ;
	
	public PackagingOption(ResultSet rs)
	{
		LoadData(rs);
	}
	
	public PackagingOption(String id)
	{
		ResultSet rs = DataManager.Query("SELECT * FROM salesforce.Packaging_Option__c where SFID='" + id + "'");
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
			ColorFinish = rs.getString("Colours_Finishes__c");
			Status = rs.getString("Status__c");
			
			PackagingId = rs.getString("Packaging__c");
			listMOQ = new ArrayList<PackagingOptionMOQ>();
			//LoadMOQ();
		} 
		catch (SQLException e) {}
	}
	
	private void LoadMOQ()
	{
		try 
		{
			ResultSet rs = DataManager.Query("SELECT * FROM salesforce.Leaf_BOM__c where Packaging_Option_MOQ__c = '" + Id + "'");
			while (rs.next()) 
	        {
				PackagingOptionMOQ newPackMOQ = new PackagingOptionMOQ(rs);
				newPackMOQ.linkPackagingOption(this);
				listMOQ.add(newPackMOQ);
	        }
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void linkPackaging(Packaging p)
	{
		if(p.Id.equals(PackagingId))
		{
			packaging = p;
		}
	}
}
