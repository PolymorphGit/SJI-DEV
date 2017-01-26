import java.sql.ResultSet;
import java.sql.SQLException;

public class Bulk {
	
	public String id;
	public String Name;
	public Double QuantityPerEach;
	public String QuantityUnitPerEach;
	public String FormulaBenchMark;
	public Double ManualMaxFill;
	public Integer Each;
	public Double Scrap;
	public String AppearanceShadBench;
	public String ReferenceCode;
	
	public Double MaxFill;
	
	public String RDHierarchyId;
	public RDHierarchy RDHierarchy;
	
	public Bulk(ResultSet rs) {
		LoadData(rs);
	}
	
	public Bulk(String id)
	{
		ResultSet rs = DataManager.Query("SELECT * FROM salesforce.Formula__c where SFID='" + id + "'");
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
			Name = rs.getString("name");
			
			QuantityPerEach = rs.getDouble("Quantity_per_Each__c");
			QuantityUnitPerEach = rs.getString("quantity_unit_per_each__c");
			FormulaBenchMark = rs.getString("formula_benchmark__c");
			ManualMaxFill = rs.getDouble("max_fill_manual__c");
			Each = rs.getInt("each__c");
			Scrap = rs.getDouble("scrap__c");
			AppearanceShadBench = rs.getString("appearance_shade_benchmarks__c");
			ReferenceCode = rs.getString("reference_code__c");
					
			RDHierarchyId = rs.getString("rd_hierarchy__c");
			RDHierarchy rdh = new RDHierarchy(RDHierarchyId);
			linkRDHierarchy(rdh);
			
		} catch(SQLException e) {
			
			e.getMessage();
		}
	}
	
	public void linkRDHierarchy(RDHierarchy rdHi)
	{
		RDHierarchy = rdHi;
		if (ManualMaxFill == 0) 
		{
			if (RDHierarchy.MachineType.equals("Skincare"))
			{
				MaxFill = QuantityPerEach * (QuantityUnitPerEach.equals("ML") ? RDHierarchy.SGMax : 1);
				MaxFill += (MaxFill < 31 ? 2 : 4);
			}
			else
			{
				MaxFill = QuantityPerEach * (QuantityUnitPerEach.equals("ML") ? RDHierarchy.SGMax : 1) + 0.6;
			}
		}
		else
		{
			MaxFill = ManualMaxFill;
		}
	}
	
	public String getData()
	{
		String output = "Name: " + Name + ", Quantity per Each" + QuantityPerEach + ", MaxFill: " + MaxFill + "<br/>";
		output += "RD Hierarchy: " + RDHierarchyId + ", SG: " + RDHierarchy.SGMax;
		return output;
	}
}
