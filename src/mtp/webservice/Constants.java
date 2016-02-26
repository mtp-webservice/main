package mtp.webservice;

public class Constants {
	public static String dbClass = "com.mysql.jdbc.Driver";
	private static String dbName= "MTP";
    public static String dbUrl = "jdbc:mysql://localhost:3306/"+dbName;
    public static String dbUser = "root";
	public static String dbPwd = "1234";
	
	public static String dbClassPg = "org.postgresql.Driver";
    public static String dbUserPg = "luke";
    public static String dbUrlPg = "jdbc:postgresql://localhost:5432/"+dbName;
    
    public static String dbType = "POSTGRES";
    
}
