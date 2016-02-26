package mtp.webservice;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
 
public class DBConnection {
	
	
    /**
     * Method to create DB Connection
     * 
     * @return
     * @throws Exception
     */
    @SuppressWarnings("finally")
    public static Connection createConnection() throws Exception {
    	
    	String dbType = Constants.dbType;
    	
        Connection con = null;
        try {
        	
        	if(dbType.equals("MYSQL"))
        	{
                Class.forName(Constants.dbClass);
                con = DriverManager.getConnection(Constants.dbUrl, Constants.dbUser, Constants.dbPwd);
        	}
        	else if(dbType.equals("POSTGRES"))
        	{
                Class.forName(Constants.dbClassPg);
                con = DriverManager.getConnection(Constants.dbUrlPg, Constants.dbUserPg, Constants.dbPwd);
        	}

        
        } catch (Exception e) {
            throw e;
        } finally {
            return con;
        }
    }
    /**
     * Method to check whether username and pwd combination are correct
     * 
     * @param uname
     * @param pwd
     * @return
     * @throws Exception
     */
    public static boolean checkLogin(String uname, String pwd) throws Exception {
        boolean isUserAvailable = false;
        Connection dbConn = null;
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
            	beginQuery = "SELECT * FROM user ";
            }
            else if(dbType.equals("POSTGRES"))
            {
            	beginQuery = "SELECT * FROM public.user ";
            }
            
            String query = beginQuery+ "WHERE username = '" + uname
                    + "' AND password=" + "'" + pwd + "'";
            //System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                //System.out.println(rs.getString(1) + rs.getString(2) + rs.getString(3));
                isUserAvailable = true;
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
        return isUserAvailable;
    }
    /**
     * Method to insert username and pwd in DB
     * 
     * @param name
     * @param uname
     * @param pwd
     * @return
     * @throws SQLException
     * @throws Exception
     */
    public static boolean insertUser(String name, String uname, String pwd, String type) throws SQLException, Exception {
        boolean insertStatus = false;
        Connection dbConn = null;
        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Statement stmt = dbConn.createStatement();
            
            String beginQuery="";
   
			if(type.equals("POSTGRES"))
			{
				beginQuery = "INSERT into public.user";
			}
			else
			{
				beginQuery = "INSERT into user";
			}
                         
            String query = beginQuery+"(name, username, password) values('"+name+ "',"+"'"
                    + uname + "','" + pwd + "')";
            System.out.println(query);
            int records = stmt.executeUpdate(query);
            //System.out.println(records);
            //When record is successfully inserted
            if (records > 0) {
                insertStatus = true;
            }
        } catch (SQLException sqle) {
            //sqle.printStackTrace();
            throw sqle;
        } catch (Exception e) {
            //e.printStackTrace();
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
        return insertStatus;
    }
}
