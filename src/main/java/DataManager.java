import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.management.Query;

import com.heroku.sdk.jdbc.DatabaseUrl;

class DataManager
{
	private Connection connection = null;
	private Statement stmt = null;
	
	public DataManager()
	{
		ConnectDB();
	}
	
	private void ConnectDB()
	{
		try {
			connection = DatabaseUrl.extract().getConnection();
			stmt = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ResultSet Query(String cmd)
	{
		try
		{
			return stmt.executeQuery(cmd);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
