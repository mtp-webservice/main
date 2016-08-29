package mtp.webservice;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import model.Product;

@Path("/meals")
public class Meals {
	
	@GET
	@Path("/test")
	@Produces(MediaType.APPLICATION_JSON) 
	public String testService(){
		return "OK";
	}
	
	@GET
	@Path("/getmealsheaders")
	@Produces(MediaType.APPLICATION_JSON) 
	public String getMealsHeaders(@QueryParam("date") String date, @QueryParam("userId") int userId) throws SQLException{
		
		Connection dbConn = null;
		Gson gson = new Gson();
		int[] caloriesArray = new int[5];
		
		caloriesArray[0] = 0;
		caloriesArray[1] = 0;
		caloriesArray[2] = 0;
		caloriesArray[3] = 0;
		caloriesArray[4] = 0;
		
        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Statement stmt = dbConn.createStatement();
            
            String dbType = Constants.dbType;
            String beginQuery = "";
            
            if(dbType.equals("MYSQL"))
            {
            	beginQuery = "SELECT meal, SUM(calories) as `suma` FROM `meals` ";
            }
            else if(dbType.equals("POSTGRES"))
            {
            	beginQuery = "SELECT * FROM public.meals ";
            }
            
            String query = beginQuery+ " where DATE(date) = DATE('" + date+ "') and userid = "+userId+" GROUP BY meal";
            
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
            	
            	int meal = rs.getInt("meal");
            	int calories = rs.getInt("suma");
            	
            	caloriesArray[meal-1] = calories;		
            }
            
            
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
		
		return gson.toJson(caloriesArray);
	}
	
	@GET
	@Path("/getproductsformeal")
	@Produces(MediaType.APPLICATION_JSON) 
	public String getProductsForMeal(@QueryParam("date") String date, @QueryParam("userId") int userId, @QueryParam("mealId") int mealId) throws SQLException{
		
		Connection dbConn = null;
		ArrayList<Product> products = new ArrayList<Product>();
		Gson gson = new Gson();
		int meal = mealId + 1;
		
        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Statement stmt = dbConn.createStatement();
            
            String dbType = Constants.dbType;
            String beginQuery = "";
            
            if(dbType.equals("MYSQL"))
            {
            	beginQuery = "SELECT * FROM `meals` ";
            }
            else if(dbType.equals("POSTGRES"))
            {
            	beginQuery = "SELECT * FROM public.meals ";
            }
            
            String query = beginQuery+ " where DATE(date) = DATE('" + date+ "') and userid = "+userId+" and meal="+meal;
            
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
            	
            	Product food = new Product();
            	food.setId(rs.getInt("id"));
            	food.setName(rs.getString("name"));
            	food.setMeal(rs.getInt("meal"));
            	food.setAmount(rs.getInt("amount"));
            	food.setUnit(rs.getString("unit"));
            	food.setCalories(rs.getInt("calories"));
            	food.setUserId(rs.getInt("userId"));
            	
            	products.add(food);
            }
            
            
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
		
		return gson.toJson(products);
	}
	
	@GET
	@Path("/getmeals")
	@Produces(MediaType.APPLICATION_JSON) 
	public String getMeals(@QueryParam("date") String date, @QueryParam("userId") int userId) throws SQLException{
		
		Connection dbConn = null;
		ArrayList<Product> foodList = new ArrayList<Product>();
		Gson gson = new Gson();
		
        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Statement stmt = dbConn.createStatement();
            
            String dbType = Constants.dbType;
            String beginQuery = "";
            
            if(dbType.equals("MYSQL"))
            {
            	beginQuery = "SELECT * FROM `meals` ";
            }
            else if(dbType.equals("POSTGRES"))
            {
            	beginQuery = "SELECT * FROM public.meals ";
            }
            
            String query = beginQuery+ " where DATE(date) = DATE('" + date+ "') and userid = "+userId;
            
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
            	
            	Product food = new Product();
            	food.setId(rs.getInt("id"));
            	food.setName(rs.getString("name"));
            	food.setMeal(rs.getInt("meal"));
            	food.setAmount(rs.getInt("amount"));
            	food.setUnit(rs.getString("unit"));
            	food.setCalories(rs.getInt("calories"));
            	food.setUserId(rs.getInt("userId"));
            	
            	foodList.add(food);
            }
            
            
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
		
		return gson.toJson(foodList);
	}
	
	
	
	@GET
	@Path("/getsummary")
	@Produces(MediaType.APPLICATION_JSON) 
	public String getSummary(@QueryParam("date") String date, @QueryParam("userId") int userId) throws SQLException{
		
		Connection dbConn = null;
		float calories = 0,carbs = 0,proteins = 0,fat = 0;
		
		Gson gson = new Gson();
		
        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Statement stmt = dbConn.createStatement();
            
            String dbType = Constants.dbType;
            String beginQuery = "";
            
            if(dbType.equals("MYSQL"))
            {
            	beginQuery = "SELECT SUM( calories ) as `calories` , SUM( proteins ) as `proteins` , SUM( carbs ) as `carbs` , SUM( fat ) as `fat` FROM `meals` ";
            }
            else if(dbType.equals("POSTGRES"))
            {
            	beginQuery = "SELECT SUM( calories ) , SUM( proteins ) , SUM( carbs ) , SUM( fat ) FROM public.meals ";
            }
            
            String query = beginQuery+ " where DATE(date) = DATE('" + date+ "') and userId =" + userId;
            
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
            	
            	calories = rs.getFloat("calories");
            	proteins = rs.getFloat("proteins");
            	carbs = rs.getFloat("carbs");
            	fat = rs.getFloat("fat");      	
            }
            
            
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
        
        JsonObject object = new JsonObject();
        object.addProperty("proteins", proteins);
        object.addProperty("carbs", carbs);
        object.addProperty("fat", fat);
        object.addProperty("calories", calories);
		
		return object.toString();
	}
	
	
	@GET
	@Path("/additem")
	@Produces(MediaType.APPLICATION_JSON) 
	public String addItem(@QueryParam("mealId") int mealId, @QueryParam("productId") int productId,
			@QueryParam("amount") float amount, @QueryParam("calories") float calories,
			@QueryParam("fat") float fat, @QueryParam("carbs") float carbs,
			@QueryParam("proteins") float proteins, @QueryParam("date") String date,
			@QueryParam("unit") String unit, @QueryParam("productName") String productName, @QueryParam("userId") int userId) throws SQLException{
		
		Connection dbConn = null;
		Gson gson = new Gson();
		String response = "{\"result\": \"false\"}";
		
        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Statement stmt = dbConn.createStatement();
            
            String dbType = Constants.dbType;
            String beginQuery = "";
            
            if(dbType.equals("MYSQL"))
            {
            	beginQuery = "insert into meals(meal,name,productId,proteins,carbs,fat,calories,amount,unit,date,userId) ";
            }
            else if(dbType.equals("POSTGRES"))
            {
            	beginQuery = "INSERT INTO public.meals ";
            }
            
            String query = beginQuery+ " values("+mealId+",'"+productName+"',"+productId
            		+","+proteins+","+carbs+","+fat+","+calories+","+amount+",'"+unit+"','"+date+"',"+userId+")";
            
            int result = stmt.executeUpdate(query);
            if (result > 0)
            	response = "{\"result\": \"true\"}";
            	return gson.toJson(response);
            
            
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            } 
        }
		
	}
	
	
	@GET
	@Path("/removeitem")
	@Produces(MediaType.APPLICATION_JSON) 
	public String removeItem(@QueryParam("id") int id) throws SQLException{
	{
		Connection dbConn = null;
		Gson gson = new Gson();
		String response = "{\"result\": \"false\"}";
		
        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Statement stmt = dbConn.createStatement();
            
            String dbType = Constants.dbType;
            String beginQuery = "";
            
            if(dbType.equals("MYSQL"))
            {
            	beginQuery = "DELETE FROM `meals` ";
            }
            else if(dbType.equals("POSTGRES"))
            {
            	beginQuery = "DELETE FROM public.meals ";
            }
            
            String query = beginQuery+ " where id = " + id;
            
            int result = stmt.executeUpdate(query);
            if (result > 0)
            	response = "{\"result\": \"true\"}";
            	return gson.toJson(response);
            
            
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            } 
        }
	}
	
	}}
			
	
	


	
