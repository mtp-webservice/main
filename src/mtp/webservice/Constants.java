package mtp.webservice;

public class Constants {
	public static String dbClass = "com.mysql.jdbc.Driver";
	private static String dbName= "androidmtp";
	public static String dbUrl = "jdbc:mysql://eu-cdbr-west-01.cleardb.com/heroku_8f609e29676dcdb?reconnect=true?useUnicode=yes&characterEncoding=UTF-8";
	public static String dbUser = "b9ca0e4a6a1bd9";
    public static String dbPwd = "f70feb74";
	
	//private static String dbName= "db6004191_mtp";
    //public static String dbUrl = "jdbc:mysql://mysql636.cp.az.pl/"+dbName+"?characterEncoding=UTF-8";
	//public static String dbUser = "u6004191_mtp";
    //public static String dbPwd = "]g45dPc<n2o,,aD,";
    
	public static String dbClassPg = "org.postgresql.Driver";
    public static String dbUserPg = "luke";
    public static String dbUrlPg = "jdbc:postgresql://localhost:5432/"+dbName;
    
    public static String dbType = "MYSQL";
    
    public static String unitCm = "cm";
    public static String unitKg = "kg";
    public static String unitPercent = "%";
    
    public static String measureWeight = "Waga";
    public static String measureBicep = "Obwód bicepsa";
    public static String measureChest = "Obwód klatki piersiowej";
    public static String measureWaist = "Obwód pasa";
    public static String measureThigh = "Obwód uda";
    public static String measureNeck = "Obwód szyi";
    public static String measureBodyFat = "Poziom tkanki tlusczowej";
    
    public static String measureBenchPress = "Wyciskanie sztangi poziomo";
    public static String measureBarbellRow = "Wios³owanie";
    public static String measureBarbellCurls = "Uginanie ramion ze sztang¹";
    public static String measureDips = "Pompki na porêczach";
    public static String measureSquats = "Przysiady ";
    
}
