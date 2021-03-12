package Result;
/**
 *  Login Result JSON object
 */
public class LoginRes extends ParentRes {
    private String authToken;
    private String userName;
    private String personID;

    /**
     *
     * @param isSuccess API response success
     * @param authToken Generated authentication token
     * @param username acquired username
     * @param personID acquired password
     */
    public LoginRes(boolean isSuccess, String authToken, String username, String personID) {
        super(isSuccess, null);
        this.authToken = authToken;
        this.userName = username;
        this.personID = personID;
    }
    public LoginRes(boolean isSuccess, String msg) {
        super(false, msg);
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
