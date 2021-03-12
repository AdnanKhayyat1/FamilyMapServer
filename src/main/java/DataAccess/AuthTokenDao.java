package DataAccess;

import Models.AuthToken;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Accesses the AuthToken Table with various editing abilities
 */
public class AuthTokenDao {
    final private Connection conn;
    public AuthTokenDao(Connection conn){
        this.conn = conn;
    }
    /**
     * adds AuthToken object to the table
     * @param at : AuthToken object to be added
     * @return boolean : insertion success, could be false if object is not unique
     */
    public void addAuthToken(AuthToken at) throws DataAccessException {
        String sql = "INSERT INTO AuthTokens (token, username, personID) VALUES(?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, at.getAuthToken());
            stmt.setString(2, at.getUserName());
            stmt.setString(3, at.getPersonID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the AuthToken Database");
        }
        System.out.println("AuthToken added " + at.getAuthToken());
    }
    public String GetUsernameByToken(String token){
        String sql = "SELECT * FROM AuthTokens WHERE token = ?;";
        ResultSet rs = null;
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,token);
            rs = stmt.executeQuery();
            if(rs.next()){
                return rs.getString("username");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }
    public boolean AuthenticateUser(String t){
        if(t.isEmpty() || t.equals(null)){
            return false;
        }
        boolean isThere = true;
        ResultSet auth = null;
        String sql = "SELECT * FROM AuthTokens WHERE token = ?;";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, t);
            auth = stmt.executeQuery();
            if(auth.getRow() == 0 && !auth.isBeforeFirst()){
                isThere = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isThere;
    }
    /**
     * gets AuthToken by user ID
     * @param username: String username
     * @return AuthToken object with that User ID
     */
    public AuthToken GetAuthTokenByUsername(String username) throws DataAccessException {
        AuthToken token;
        ResultSet rs = null;
        String sql = "SELECT * FROM AuthTokens WHERE username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                token = new AuthToken(rs.getString("token"), rs.getString("username"), rs.getString("personID"));
                return token;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding authToken" + " " + e.toString());
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }
    /**
     *  deletes AuthToken by user ID
     * @param username: String username
     * @return boolean success of deletion, could be false if String id does not exist
     */
    public void deleteAuth(String username) throws DataAccessException {

        String sql = "DELETE FROM AuthTokens WHERE username = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            if(GetAuthTokenByUsername(username) == null){
                throw new DataAccessException("No such person to delete : " + username);

            }
            stmt.setString(1,username);
            stmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing Person with ID " + username);
        }
    }
    /**
     * deletes all AuthTokens
     */
    public void clearAllTokens() throws DataAccessException {
        String sql = "DELETE FROM AuthTokens";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing AuthTokens table");
        }
    }
}
