package mtp.webservice;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
//Path: http://localhost/<appln-folder-name>/register
@Path("/register")
public class Register {
    // HTTP Get Method
    @GET
    // Path: http://localhost/<appln-folder-name>/register/doregister
    @Path("/doregister")  
    // Produces JSON as response
    @Produces(MediaType.APPLICATION_JSON) 
    // Query parameters are parameters: http://localhost/<appln-folder-name>/register/doregister?name=pqrs&username=abc&password=xyz
    public String doLogin(@QueryParam("name") String name, @QueryParam("username") String uname, @QueryParam("password") String pwd){
        String response = "";
        //System.out.println("Inside doLogin "+uname+"  "+pwd);
        int retCode = registerUser(name, uname, pwd);
        if(retCode == 0){
        	BaseJsonResponse obj = new BaseJsonResponse("register",true);
            response = obj.toJson();
        }else if(retCode == 1){
        	BaseJsonResponse obj = new BaseJsonResponse("register",false, "You are already registered");
            response = obj.toJson();
        }else if(retCode == 2){
        	BaseJsonResponse obj = new BaseJsonResponse("register",false, "Special Characters are not allowed in Username and Password");
            response = obj.toJson();
        }else if(retCode == 3){
        	BaseJsonResponse obj = new BaseJsonResponse("register",false, "Error occured");
            response = obj.toJson();
        }
        return response;
 
    }
 
    private int registerUser(String name, String uname, String pwd){
        System.out.println("Inside checkCredentials");
        int result = 3;
        if(Utility.isNotNull(uname) && Utility.isNotNull(pwd)){
            try {
                if(DBConnection.insertUser(name, uname, pwd,Constants.dbType)){
                    System.out.println("RegisterUSer if");
                    result = 0;
                }
            } catch(SQLException sqle){
                System.out.println("RegisterUSer catch sqle");
                //When Primary key violation occurs that means user is already registered
                if(sqle.getErrorCode() == 1062){
                    result = 1;
                } 
                //When special characters are used in name,username or password
                else if(sqle.getErrorCode() == 1064){
                    System.out.println(sqle.getErrorCode());
                    result = 2;
                }
            }
            catch (Exception e) {
                // TODO Auto-generated catch block
                System.out.println("Inside checkCredentials catch e ");
                result = 3;
            }
        }else{
            System.out.println("Inside checkCredentials else");
            result = 3;
        }
 
        return result;
    }
 
}
