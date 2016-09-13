package mtp.webservice;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
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
    @Path("/getchartdata")  
    @Produces(MediaType.APPLICATION_JSON) 
	 public String getChartData(@QueryParam("userId") int userId, @QueryParam("typeId") int typeId) throws SQLException{
    	
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
	            
	            String query = beginQuery+ " where userId ="+userId+" and typeId ="+ typeId+ " order by date desc "
	            		+ " limit 10";
	            
	            SimpleDateFormat dt1 = new SimpleDateFormat("dd.MM");
	            
	            ResultSet rs = stmt.executeQuery(query);
	            while (rs.next()) {
	            	
	            	Measure measure = new Measure();
	            	measure.setGroupId(rs.getInt("groupId"));
	            	measure.setTypeId(rs.getInt("typeId"));
	            	measure.setName(rs.getString("name"));
	            	measure.setValue(rs.getInt("value"));
	            	measure.setDate(dt1.format(rs.getDate("date")));
	            	measure.setUnit(rs.getString("unit"));
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
	
	@GET
    @Path("/getprevioustrainingchart")  
    @Produces(MediaType.APPLICATION_JSON) 
	 public String getPreviousTraningChart(@QueryParam("userId") int userId, @QueryParam("exerciseId") int exerciseId, @QueryParam("date") String date) throws SQLException{
    	
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
	            	beginQuery = "SELECT * FROM `trainings` ";
	            }
	            else if(dbType.equals("POSTGRES"))
	            {
	            	beginQuery = "SELECT * FROM public.trainings ";
	            }
	            
	            int id = exerciseId +1;
	            
	            String query = beginQuery+ "WHERE date < '"+date+"' and exerciseId="+id+" ORDER BY date desc LIMIT 5";
	            
	            SimpleDateFormat dt1 = new SimpleDateFormat("dd.MM");
	            
	            ResultSet rs = stmt.executeQuery(query);
	            while (rs.next()) {
	            	
	            	Measure measure = new Measure();
	            	measure.setGroupId(rs.getInt("setNo"));
	            	measure.setValue(rs.getInt("weight"));
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
	
	
	@GET
	@Path("/savebodymeasures")
	@Produces(MediaType.APPLICATION_JSON) 
	public String saveBodyMeasures(@QueryParam("weight") float weight, @QueryParam("bicep") float bicep, 
						@QueryParam("chest") float chest, @QueryParam("waist") float waist,
						@QueryParam("thigh") float thigh, @QueryParam("neck") float neck, @QueryParam("bodyFat") float bodyFat,
						@QueryParam("userId") int userId) throws SQLException{
		
		Connection dbConn = null;
		Gson gson = new Gson();
		
		 try {
	            try {
	                dbConn = DBConnection.createConnection();
	            } catch (Exception e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	            Statement stmt = dbConn.createStatement();
	            
	          
	    // weight
	            
	    CallableStatement cStmt = dbConn.prepareCall("{call udpate_or_insert_bodyMeasures(?, ?, ?, ?, ?)}");
	    cStmt.setInt(1, 1);
	    cStmt.setFloat(2, weight);
	    cStmt.setInt(3, userId);
	    cStmt.setString(4, Constants.measureWeight);
	    cStmt.setString(5, Constants.unitKg);
	    
	    cStmt.execute();
	    
	    // bicep
	    
	    cStmt.setInt(1, 2);
	    cStmt.setFloat(2, bicep);
	    cStmt.setInt(3, userId);
	    cStmt.setString(4, Constants.measureBicep);
	    cStmt.setString(5, Constants.unitCm);
	    
	    cStmt.execute();
	    
	    // chest
	    
	    cStmt.setInt(1, 3);
	    cStmt.setFloat(2, chest);
	    cStmt.setInt(3, userId);
	    cStmt.setString(4, Constants.measureChest);
	    cStmt.setString(5, Constants.unitCm);
	    
	    cStmt.execute();
	    
	    // waist 
	    
	    cStmt.setInt(1, 4);
	    cStmt.setFloat(2, waist);
	    cStmt.setInt(3, userId);
	    cStmt.setString(4, Constants.measureWaist);
	    cStmt.setString(5, Constants.unitCm); 
	    
	    cStmt.execute();
	 
	    // thigh
	    
	    cStmt.setInt(1, 5);
	    cStmt.setFloat(2, thigh);
	    cStmt.setInt(3, userId);
	    cStmt.setString(4, Constants.measureThigh);
	    cStmt.setString(5, Constants.unitCm);
	    
	    cStmt.execute();
	    
	    // neck
	    
	    cStmt.setInt(1, 6);
	    cStmt.setFloat(2, neck);
	    cStmt.setInt(3, userId);
	    cStmt.setString(4, Constants.measureNeck);
	    cStmt.setString(5, Constants.unitCm);
	    
	    cStmt.execute();
	    
	    // body fat
	    
	    cStmt.setInt(1, 7);
	    cStmt.setFloat(2, bodyFat);
	    cStmt.setInt(3, userId);
	    cStmt.setString(4, Constants.measureBodyFat);
	    cStmt.setString(5, Constants.unitPercent);
	    
	    cStmt.execute();
		
        return gson.toJson("");
        
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
	            	beginQuery = "SELECT * FROM measures WHERE id IN ("
	            			+ "SELECT MAX(id) FROM measures Where userid ="
	            			+ userId
	            			+ " GROUP BY typeId) ORDER BY typeId";
	            }
	            else if(dbType.equals("POSTGRES"))
	            {
	            	beginQuery = "SELECT * FROM public.measures ";
	            }
	            
	            String query = beginQuery;
	            
	            ResultSet rs = stmt.executeQuery(query);
	            while (rs.next()) {
	            	
	            	Measure measure = new Measure();
	            	measure.setGroupId(rs.getInt("groupId"));
	            	measure.setTypeId(rs.getInt("typeId"));
	            	measure.setName(rs.getString("name"));
	            	measure.setValue(rs.getInt("value"));
	            	measure.setUnit(rs.getString("unit"));
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
