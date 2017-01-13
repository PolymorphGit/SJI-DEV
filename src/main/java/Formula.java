import java.sql.ResultSet;
import java.sql.SQLException;

public class Formula {
	
	public String id;
	public String name;
	public String rdHire;
	public String qUnitPerEach;
	public String formulaBenchMark;
	public Double mMaxFill;
	public Integer each;
	public Double scrap;
	public String appShadBench;
	public String refer;
	
	public Formula(ResultSet rs) {
		
		try {
			
			id = rs.getString("SFID");
			name = rs.getString("name");
			rdHire = rs.getString("rd_hierarchy__c");
			qUnitPerEach = rs.getString("quantity_unit_per_each__c");
			formulaBenchMark = rs.getString("formula_benchmark__c");
			mMaxFill = rs.getDouble("max_fill_manual__c");
			
			if (mMaxFill == 0) {
				
				
			
			}
			
			each = rs.getInt("each__c");
			scrap = rs.getDouble("scrap__c");
			appShadBench = rs.getString("appearance_shade_benchmarks__c");
			refer = rs.getString("reference_code__c");
			
		} catch(SQLException e) {
			
			e.getMessage();
		}
		
	}
	
}
