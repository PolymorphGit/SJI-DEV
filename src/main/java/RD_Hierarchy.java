import java.sql.ResultSet;
import java.sql.SQLException;

public class RD_Hierarchy {
	
	public String id;
	public String name;
	public String machineType;
	public double sgMax;
	public double scrap;
	
	public RD_Hierarchy(ResultSet rs) {
		LoadData(rs);
	}
	
	public RD_Hierarchy(String id)
	{
		ResultSet rs = DataManager.Query("Select * from salesforce.RD_Hierarchy__c where SFID='" + id + "'");
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
			
			id = rs.getString("SFID");
			name = rs.getString("name");
			machineType = rs.getString("machine_type__c");
			sgMax = rs.getDouble("sg_max__c");
			scrap = rs.getDouble("scrap__c");
			
		} catch(SQLException sql) {
			sql.getMessage();	
		}
	}
}
