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

import model.Food;

@Path("/meals")
public class Meals {
	
	@GET
	@Path("/getmeals")
	@Produces(MediaType.APPLICATION_JSON) 
	public String getMeals(@QueryParam("date") String date) throws SQLException{
		
		Connection dbConn = null;
		ArrayList<Food> foodList = new ArrayList<Food>();
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
            
            String query = beginQuery+ " where DATE(date) = DATE('" + date+ "') ";
            
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
            	
            	Food food = new Food();
            	food.setId(rs.getInt("id"));
            	food.setName(rs.getString("name"));
            	food.setMeal(rs.getInt("meal"));
            	food.setAmount(rs.getInt("amount"));
            	food.setUnit(rs.getString("unit"));
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
			
	
	


	
