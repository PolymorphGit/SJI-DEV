import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FGOption
{
	public String Id;
	public String Name;
	public ArrayList<Bulk> listBulk;
	public ArrayList<Packaging> listPack;
	
	public String fgId;
	public FG fg;
	
	public FGOption(ResultSet rs)
	{
		LoadData(rs);
	}
	
	public FGOption(String id)
	{
		ResultSet rs = DataManager.Query("SELECT * FROM salesforce.Material_Option__c where SFID='" + id + "'");
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
			
			listBulk = new ArrayList<Bulk>();
			LoadBulk();
			listPack = new ArrayList<Packaging>();
			LoadPack();
			
		}
		catch (SQLException e) {}
	}
	
	private void LoadBulk()
	{
		
	}
	
	private void LoadPack()
	{
		
	}
	
	public void linkFG(FG newFG)
	{
		if(fgId == newFG.Id)
		{
			fg = newFG;
		}
	}
	
	public String getData()
	{
		return "Name: ";
	}
}