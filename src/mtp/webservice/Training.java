package mtp.webservice;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
	public String getTraining(@QueryParam("date") String date, @QueryParam("userId") int userId) throws SQLException{
		
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
            
            ExerciseSet max1 = Collections.max(ex1List, new WeightComp());    
            ExerciseSet max2 = Collections.max(ex2List, new WeightComp()); 
            ExerciseSet max3 = Collections.max(ex3List, new WeightComp()); 
            ExerciseSet max4 = Collections.max(ex4List, new WeightComp()); 
            ExerciseSet max5 = Collections.max(ex5List, new WeightComp()); 
            
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
            
            
            // insert max to measures 
            
            String maxQuery = "insert into measures(userId, groupId, date, typeId, name, value, unit) values("+
            userId+",2,"+"'"+date+"'";
            
            // insert max1
            
            String max1Query = maxQuery +","+7+",'"+Constants.measureSquats+"',"+max1.getWeight()+",'"+Constants.unitKg+"')";
            stmt.executeUpdate(max1Query);
            
            // insert max2
            
            String max2Query = maxQuery +","+8+",'"+Constants.measureBenchPress+"',"+max2.getWeight()+",'"+Constants.unitKg+"')";
            stmt.executeUpdate(max2Query);
            
            // insert max3
            
            String max3Query = maxQuery +","+9+",'"+Constants.measureBarbellRow+"',"+max3.getWeight()+",'"+Constants.unitKg+"')";
            stmt.executeUpdate(max3Query);
            
            // insert max4
            
            String max4Query = maxQuery +","+10+",'"+Constants.measureBarbellCurls+"',"+max4.getWeight()+",'"+Constants.unitKg+"')";
            stmt.executeUpdate(max4Query);
            
            // insert max5
            
            String max5Query = maxQuery +","+11+",'"+Constants.measureDips+"',"+max5.getWeight()+",'"+Constants.unitKg+"')";
            stmt.executeUpdate(max5Query);
            
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
	
	class WeightComp implements Comparator<ExerciseSet>{
		 
	    @Override
	    public int compare(ExerciseSet e1, ExerciseSet e2) {
	        return ((Integer)e1.getWeight()).compareTo(e2.getWeight());
	    }
	}
	 
	
}
