import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Bulk {
	
	public String Id;
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
	
	public String NPDId;
	public NPD npd;
	
	public ArrayList<BOMHeader> listBOMH;
	
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
			
			Id = rs.getString("SFID");
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
			
			NPDId = rs.getString("NPD__c");
			
			listBOMH = new ArrayList<BOMHeader>();
			LoadBOMHeader();
			
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
	
	private void LoadBOMHeader()
	{
		try 
		{
			ResultSet rs = DataManager.Query("SELECT * FROM salesforce.BOM_Header__c where Formula__c = '" + Id + "'");
			while (rs.next()) 
	        {
				BOMHeader newBOMHeader = new BOMHeader(rs);
				newBOMHeader.linkBulk(this);
				listBOMH.add(newBOMHeader);
	        }
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void linkNPD(NPD newNPD)
	{
		if(newNPD.Id.equals(NPDId))
		{
			npd = newNPD;
		}
	}
	
	public String getData()
	{
		String output = "Name: " + Name + ", Quantity per Each" + QuantityPerEach + ", MaxFill: " + MaxFill + "<br/>";
		output += "RD Hierarchy: " + RDHierarchyId + ", SG: " + RDHierarchy.SGMax + "<br/>";
		
		for(BOMHeader bomH : listBOMH)
		{
			output += "BOM Number: " + bomH.Name + ", Status: " + bomH.Status + ", Base Unit: " + bomH.BaseUnit + "<br/>";
			
			for(BOMDetail bomD : bomH.listBOMDetail)
			{
				output += " - Material: " + bomD.material.MaterialCode + ", Percent: " + bomD.Percent + ", Scrap: " + bomD.Scrap + ", Plant: " + bomD.Plant + "<br/>";
			}
			
		}
		
		return output;
	}
}
