package Models;

import java.util.Objects;

/**
 * Authentication Token database model
 *
 */
public class AuthToken {
    /**
     * Unique authentication token
     */
    private String authToken;
    /**
     * username string
     */
    private String userName;
    /**
     * person ID String
     */
    private String personID;

    public AuthToken(String authToken, String userName, String password) {
        this.authToken = authToken;
        this.userName = userName;
        this.personID = password;
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

    public void setPersonID(String password) {
        this.personID = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken authToken1 = (AuthToken) o;
        return Objects.equals(authToken, authToken1.authToken) &&
                Objects.equals(userName, authToken1.userName) &&
                Objects.equals(personID, authToken1.personID);
    }
}
