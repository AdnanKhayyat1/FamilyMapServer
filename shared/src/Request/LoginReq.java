package Request;
/**
 *  Login Request JSON object
 */
public class LoginReq {
    public String userName;
    public String password;
    public LoginReq(String u, String p){
        userName = u;
        password = p;
    }
}
