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
    
    get("/Account", (req, res) ->{
      	String id = req.queryParams("id");
      	Account acc = new Account(id);
      	return acc.getData();
    });
    
    get("/ExchangeRate", (req, res) ->{
      	String id = req.queryParams("id");
      	String isManual = req.queryParams("Manual");
      	
      	if(isManual.equals("TRUE"))
      	{
      		ManualExchangeRate mExR = new ManualExchangeRate(id);
      		return mExR.getData();
      	}
      	
      	ExchangeRate exR = new ExchangeRate(id);
      	return exR.getData();
    });
    
    get("/FG", (req, res) ->{
      	String id = req.queryParams("id");
      	FG fg = new FG(id);
      	return fg.getData();
    });
    
    get("/Bulk", (req, res) ->{
      	String id = req.queryParams("id");
      	Bulk bulk = new Bulk(id);
      	return bulk.getData();
    });
    
    get("/MasterPackaging", (req, res) ->{
      	String id = req.queryParams("id");
      	MasterPackagingType PackType = new MasterPackagingType(id);
      	return PackType.getData();
    });
    
    get("/MasterQuality", (req, res) ->{
      	String id = req.queryParams("id");
      	MasterQualityCost MasterQuality = new MasterQualityCost(id);
      	return MasterQuality.getData();
    });
    
    get("/MasterWareHouse", (req, res) ->{
      	String id = req.queryParams("id");
      	MasterWareHouse MasterWarehouse = new MasterWareHouse(id);
      	return MasterWarehouse.getData();
    });
    
    get("/Packaging", (req, res) ->{
    	String id = req.queryParams("id");
    	Packaging pack = new Packaging(id);
    	return pack.getData();
    });
    
    get("/Material", (req, res) ->{
      	String id = req.queryParams("id");
      	Material mat = new Material(id);
      	return mat.getResult();
    });
    
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
	
	Account acc = new Account(id);
	System.out.println("Hello, logs!" + acc.Id);
	IdealPrice idealPrice = new IdealPrice(acc);
      
	return idealPrice.getResult();
	//return id;
  }

}
