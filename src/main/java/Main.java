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

	DataManager.init();
    port(Integer.valueOf(System.getenv("PORT")));
    staticFileLocation("/public");

    get("/hello", (req, res) -> "Hello World");

    get("/", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("message", "Hello World!");

            return new ModelAndView(attributes, "index.ftl");
        }, new FreeMarkerEngine());

    get("/Test", (req, res) -> {
      Map<String, Object> attributes = new HashMap<>();
      try {
        //DataManager.Query("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
        //DataManager.Query("INSERT INTO ticks VALUES (now())");
        //ResultSet rs = DataManager.Query("SELECT tick FROM ticks");
        ResultSet rs = DataManager.Query("SELECT * FROM salesforce.Account");

        ArrayList<String> output = new ArrayList<String>();
        while (rs.next()) {
          Account acc = new Account(rs);
          output.add("Id: " + acc.Id + "Name: " + acc.Name + ", Type: " + acc.Type + ", Status: " + acc.AccountStatus);
        }

        attributes.put("results", output);
        return new ModelAndView(attributes, "db.ftl");
      } 
      catch (SQLException e) 
      {
    	  attributes.put("message", "There was an error: " + e);
          return new ModelAndView(attributes, "error.ftl");
      }
    }, new FreeMarkerEngine());
    
    get("/IdealPrice", (req, res) ->{
    	String id= req.queryParams("id");
    	return CalculateIdealPricebyAccount(id);
    });
    
    get("/ExchangeRate", (req, res) ->{
      	String id= req.queryParams("id");
      	return id;
    });
    
    get("/ExchangeRate", (req, res) ->{
      	String name = req.queryParams("Name");
      	return name;
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
	
	Account acc = new Account(id);
	//IdealPrice idealPrice = new IdealPrice(acc);
      
	//return idealPrice.getResult();
	return "Success";
  }

}
