import java.sql.ResultSet;
import java.sql.SQLException;

public class RDHierarchy {
	
	public String Id;
	public String Name;
	public String MachineType;
	public double SGMax;
	public double Scrap;
	
	public RDHierarchy(ResultSet rs) {
		LoadData(rs);
	}
	
	public RDHierarchy(String id)
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
			
			Id = rs.getString("SFID");
			Name = rs.getString("name");
			MachineType = rs.getString("machine_type__c");
			SGMax = rs.getDouble("sg_max__c");
			Scrap = rs.getDouble("scrap__c");
			
		} catch(SQLException sql) {
			sql.getMessage();	
		}
	}
}
