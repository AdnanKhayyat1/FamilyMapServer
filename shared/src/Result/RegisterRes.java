package Result;
/**
 *  Register Result JSON object
 */
public class RegisterRes extends ParentRes {
    private String authToken;
    private String userName;
    private String personID;

    /**
     *
     * @param isSuccess API response success
     * @param authToken Generated authentication token
     * @param userName acquired username
     * @param personID acquired password
     */
    public RegisterRes(boolean isSuccess, String authToken, String userName, String personID) {
        super(isSuccess, null);
        this.authToken = authToken;
        this.userName = userName;
        this.personID = personID;
    }
    public RegisterRes(boolean isSuccess, String msg) {
        super(isSuccess, msg);
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
