package model;

public class Product {
	
	 private int id;
	    private int meal;
	    private String name;
	    private int amount;
	    private String unit;
	    private int proteins;
	    private int carbs;
	    private int fat;
	    private int calories;
	    private int factor;

	    public Product(int id, int meal, String name, int amount, String unit,
	    		int proteins, int carbs, int fat, int calories, int factor)
	    {
	        this.id = id;
	        this.meal = meal;
	        this.name = name;
	        this.amount = amount;
	        this.unit = unit;
	        this.calories = calories;
	        this.fat = fat;
	        this.carbs = carbs;
	        this.proteins = proteins;
	        this.factor = factor;
	    }

	    public Product() {
			// TODO Auto-generated constructor stub
		}

	    public int getProteins()
	    {
	    	return proteins;
	    }
	    
	    public int getCarbs()
	    {
	    	return carbs;
	    }
	    
	    public int getFat()
	    {
	    	return fat;
	    }
	    
	    public int getCalories()
	    {
	    	return calories;
	    }
	    
		public int getAmount() {
	        return amount;
	    }

	    public int getId() {
	        return id;
	    }

	    public int getMeal() {
	        return meal;
	    }

	    public String getName() {
	        return name;
	    }

	    public String getUnit() {
	        return unit;
	    }

	    public void setAmount(int amount) {
	        this.amount = amount;
	    }

	    public void setId(int id) {
	        this.id = id;
	    }

	    public void setMeal(int meal) {
	        this.meal = meal;
	    }

	    public void setUnit(String unit) {
	        this.unit = unit;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

		public void setProteins(int proteins) {
			this.proteins = proteins;
		}

		public void setCarbs(int carbs) {
			this.carbs = carbs;
		}

		public void setFat(int fat) {
			this.fat = fat;
		}

		public void setCalories(int calories) {
			this.calories = calories;
		}

		public int getFactor() {
			return factor;
		}

		public void setFactor(int factor) {
			this.factor = factor;
		}
		
		
	    
	    
	

}
