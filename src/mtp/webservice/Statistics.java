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

import model.Measure;

@Path("/stats")
public class Statistics {
	
	@GET
    @Path("/getcurrent")  
    @Produces(MediaType.APPLICATION_JSON) 
	 public String getCurrent(@QueryParam("userId") int userId) throws SQLException{
    	
		Connection dbConn = null;
		ArrayList<Measure> measureList = new ArrayList<Measure>();
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
	            	beginQuery = "SELECT * FROM `measures` ";
	            }
	            else if(dbType.equals("POSTGRES"))
	            {
	            	beginQuery = "SELECT * FROM public.measures ";
	            }
	            
	            String query = beginQuery+ " where userId ="+userId;
	            
	            ResultSet rs = stmt.executeQuery(query);
	            while (rs.next()) {
	            	
	            	Measure measure = new Measure();
	            	measure.setGroupId(rs.getInt("groupId"));
	            	measure.setTypeId(rs.getInt("typeId"));
	            	measure.setName(rs.getString("name"));
	            	measure.setValue(rs.getString("value"));
	            	measureList.add(measure);
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
			
			return gson.toJson(measureList); 
	 }
}
