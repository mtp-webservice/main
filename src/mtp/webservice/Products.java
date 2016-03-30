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

import model.Product;

@Path("/products")
public class Products {

	@GET
	@Path("/getproducts")
	@Produces(MediaType.APPLICATION_JSON) 
	public String getMeals(@QueryParam("search") String search) throws SQLException{
	
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
            	beginQuery = "SELECT * FROM `products` ";
            }
            else if(dbType.equals("POSTGRES"))
            {
            	beginQuery = "SELECT * FROM public.products ";
            }
            
            String query = beginQuery+ " where name like '%" + search+ "%' ";
            
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
            	
            	Product food = new Product();
            	food.setId(rs.getInt("id"));
            	food.setName(rs.getString("name"));
            	food.setProteins(rs.getInt("proteins"));
            	food.setCarbs(rs.getInt("carbs"));
            	food.setFat(rs.getInt("fat"));
            	food.setCalories(rs.getInt("calories"));
            	food.setFactor(rs.getInt("factor"));
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
}
