import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.management.Query;

import com.heroku.sdk.jdbc.DatabaseUrl;

public class DataManager
{
	private static Connection connection = null;
	
	public static void init()
	{
		ConnectDB();
	}
	
	private static void ConnectDB()
	{
		try {
			connection = DatabaseUrl.extract().getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ResultSet Query(String cmd)
	{
		try
		{
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(cmd);
			return rs;
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
