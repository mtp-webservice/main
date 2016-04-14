package mtp.webservice;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import model.ExerciseSet;
import model.Product;

@Path("/training")
public class Training {
	
	@GET
	@Path("/gettraining")
	@Produces(MediaType.APPLICATION_JSON) 
	public String getMeals(@QueryParam("date") String date, @QueryParam("userId") int userId) throws SQLException{
		
		Connection dbConn = null;
		ArrayList<ExerciseSet> exercises = new ArrayList<ExerciseSet>();
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
            
            String query = beginQuery+ " where DATE(date) = DATE('" + date+ "') and userid = "+userId;
            
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
            	
            	ExerciseSet exercise = new ExerciseSet();
            	exercise.setId(rs.getInt("id"));
            	exercise.setExerciseName(rs.getString("name"));
            	exercise.setExerciseId(rs.getInt("exerciseId"));
            	exercise.setReps(rs.getInt("reps"));
            	exercise.setSetNo(rs.getInt("setNo"));
            	exercise.setWeight(rs.getInt("weight"));
            	exercise.setUserId(rs.getInt("userId"));
            	
            	exercises.add(exercise);
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
        
        return gson.toJson(exercises);
	}
	
	
	@GET
	@Path("/addtraining")
	@Produces(MediaType.APPLICATION_JSON) 
	public String addTraining(@QueryParam("ex1") String ex1, @QueryParam("ex2") String ex2, 
						@QueryParam("ex3") String ex3,@QueryParam("ex4") String ex4,
						@QueryParam("ex5") String ex5,@QueryParam("date") String date,
						@QueryParam("userId") int userId) throws SQLException{
		
		Connection dbConn = null;
		Gson gson = new Gson();
		
		Type listOfTestObject = new TypeToken<List<ExerciseSet>>(){}.getType();
		List<ExerciseSet> ex1List = gson.fromJson(ex1, listOfTestObject);
		List<ExerciseSet> ex2List = gson.fromJson(ex2, listOfTestObject);
		List<ExerciseSet> ex3List = gson.fromJson(ex3, listOfTestObject);
		List<ExerciseSet> ex4List = gson.fromJson(ex4, listOfTestObject);
		List<ExerciseSet> ex5List = gson.fromJson(ex5, listOfTestObject);
		
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
            	beginQuery = "insert into trainings(date,userId,exerciseId,setNo,reps,weight,name) ";
            }
            else if(dbType.equals("POSTGRES"))
            {
            	beginQuery = "INSERT INTO public.trainings ";
            }
            
            for(ExerciseSet el : ex1List){ 
                String query = beginQuery+ " values('"+date+"',"+userId+",1,"+el.getSetNo()+","+el.getReps()+","+el.getWeight()+",'')";            
                int result = stmt.executeUpdate(query);
            }
            
            for(ExerciseSet el : ex2List){ 
                String query = beginQuery+ " values('"+date+"',"+userId+",2,"+el.getSetNo()+","+el.getReps()+","+el.getWeight()+",'')";            
                int result = stmt.executeUpdate(query);
            }
            
            for(ExerciseSet el : ex3List){ 
                String query = beginQuery+ " values('"+date+"',"+userId+",3,"+el.getSetNo()+","+el.getReps()+","+el.getWeight()+",'')";            
                int result = stmt.executeUpdate(query);
            }
            
            for(ExerciseSet el : ex4List){ 
                String query = beginQuery+ " values('"+date+"',"+userId+",4,"+el.getSetNo()+","+el.getReps()+","+el.getWeight()+",'')";            
                int result = stmt.executeUpdate(query);
            }
            
            for(ExerciseSet el : ex5List){ 
                String query = beginQuery+ " values('"+date+"',"+userId+",5,"+el.getSetNo()+","+el.getReps()+","+el.getWeight()+",'')";            
                int result = stmt.executeUpdate(query);
                if (result > 0)
                	response = "{\"result\": \"true\"}";
            }
            
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

}
