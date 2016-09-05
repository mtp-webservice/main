package model;

import java.util.Date;

public class ExerciseSet {
	 public ExerciseSet() {

	    }

	    public ExerciseSet(int id, int weight, int reps, int setNo, String exerciseName, int exerciseId,
	    		int userId, Date date, int trainingSetId) {
	        this.id = id;
	        this.weight = weight;
	        this.reps = reps;
	        this.setNo = setNo;
	        this.exerciseName = exerciseName;
	        this.exerciseId = exerciseId;
	        this.userId = userId;
	        this.date = date;
	        this.trainingSetId = trainingSetId;
	    }

	    private int id;
	    private int exerciseId;
	    private String exerciseName;
	    private int weight;
	    private int reps;
	    private int setNo;
	    private int userId;
	    private Date date;
	    private int trainingSetId;

	    public String getExerciseName() {
	        return exerciseName;
	    }

	    public void setExerciseName(String exerciseName) {
	        this.exerciseName = exerciseName;
	    }

	    public int getId() {
	        return id;
	    }

	    public void setId(int id) {
	        this.id = id;
	    }

	    public int getWeight() {
			return weight;
		}

		public void setWeight(int weight) {
	        this.weight = weight;
	    }

	    public int getReps() {
	        return reps;
	    }

	    public void setReps(int reps) {
	        this.reps = reps;
	    }

	    public int getSetNo() {
	        return setNo;
	    }
	    
	    public void setSetNo(int setNo) {
			this.setNo = setNo;
		}

		public int getExerciseId() {
	        return exerciseId;
	    }

	    public void setExerciseId(int exerciseId) {
	        this.exerciseId = exerciseId;
	    }

		public int getUserId() {
			return userId;
		}

		public void setUserId(int userId) {
			this.userId = userId;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		} 
		
		public void setTrainingSetId(int trainingSetId){
			this.trainingSetId = trainingSetId;
		}
		
		public int getTrainingSetId(){
			return trainingSetId;
		}
}
