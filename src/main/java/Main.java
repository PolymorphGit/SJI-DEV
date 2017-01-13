import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

import java.net.URI;
import java.net.URISyntaxException;

import static spark.Spark.*;
import spark.template.freemarker.FreeMarkerEngine;
import spark.ModelAndView;
import static spark.Spark.get;

import com.heroku.sdk.jdbc.DatabaseUrl;

public class Main {

  public static void main(String[] args) {

    port(Integer.valueOf(System.getenv("PORT")));
    staticFileLocation("/public");

    get("/hello", (req, res) -> "Hello World");

    get("/", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("message", "Hello World!");

            return new ModelAndView(attributes, "index.ftl");
        }, new FreeMarkerEngine());

    get("/Test", (req, res) -> {
      Connection connection = null;
      Map<String, Object> attributes = new HashMap<>();
      try {
        connection = DatabaseUrl.extract().getConnection();

        Statement stmt = connection.createStatement();
        //stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
        //stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
        //ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");
        ResultSet rs = stmt.executeQuery("SELECT * FROM salesforce.Account");

        ArrayList<String> output = new ArrayList<String>();
        while (rs.next()) {
          Account acc = new Account(rs);
          output.add("Name: " + acc.Name + ", Type: " + acc.Type + ", Status: " + acc.AccountStatus);
        }

        attributes.put("results", output);
        return new ModelAndView(attributes, "db.ftl");
      } catch (Exception e) {
        attributes.put("message", "There was an error: " + e);
        return new ModelAndView(attributes, "error.ftl");
      } finally {
        if (connection != null) try{connection.close();} catch(SQLException e){}
      }
    }, new FreeMarkerEngine());

    
    get("/IdealPrice", (req, res) ->{
    	String id= req.queryParams("id");
    	return CalculateIdealPricebyAccount(id);
    });
      
  }
  
  public static String CalculateIdealPricebyAccount(String id)
  {
	if(id == null)
	{
		  return "ID is NULL";
	}
	else if(id == "")
	{
		  return "ID is Blank";
	}
	  
	Connection connection = null;
	Account acc = null;
	IdealPrice idealPrice = null;
		   
	try {
		connection = DatabaseUrl.extract().getConnection();
		Statement stmt = connection.createStatement();
	    ResultSet rs = stmt.executeQuery("SELECT * FROM salesforce.Account where SFID='" + id + "'");
	    while (rs.next()) 
	    {
	        acc = new Account(rs);
	        return acc.Id;
	        //idealPrice = new IdealPrice(acc);
	    }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (URISyntaxException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      
	//return idealPrice.getResult();
	return "Success";
  }

}
