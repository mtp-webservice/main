package mtp.webservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
//Path: http://localhost/<appln-folder-name>/login
@Path("/login")
public class Login {
    // HTTP Get Method
    @GET
    // Path: http://localhost/<appln-folder-name>/login/dologin
    @Path("/dologin")
    // Produces JSON as response
    @Produces(MediaType.APPLICATION_JSON) 
    // Query parameters are parameters: http://localhost/<appln-folder-name>/login/dologin?username=abc&password=xyz
    public String doLogin(@QueryParam("username") String uname, @QueryParam("password") String pwd){
        String response = "";
        int userId = checkCredentials(uname, pwd);
             
        if(userId > 0){
        	BaseJsonResponse obj = new BaseJsonResponse("login",true,userId);
            response = obj.toJson();
        }else{
        	BaseJsonResponse obj = new BaseJsonResponse("login", false, "B³êdny adres e-mail lub has³o");
            response = obj.toJson();
        }
    return response;        
    }
 
    /**
     * Method to check whether the entered credential is valid
     * 
     * @param uname
     * @param pwd
     * @return
     */
    private int checkCredentials(String uname, String pwd){
        System.out.println("Inside checkCredentials");
        int result;
        if(Utility.isNotNull(uname) && Utility.isNotNull(pwd)){
            try {
                result = DBConnection.checkLogin(uname, pwd);
                //System.out.println("Inside checkCredentials try "+result);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                //System.out.println("Inside checkCredentials catch");
                result = 0;
            }
        }else{
            //System.out.println("Inside checkCredentials else");
            result = 0;
        }
 
        return result;
    }
 
}