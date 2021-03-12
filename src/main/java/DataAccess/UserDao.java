package DataAccess;

import Models.UserModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Accesses the Users Table with various editing abilities
 */
public class UserDao {
    private final Connection conn;

    public UserDao(Connection conn) {
        this.conn = conn;
    }

    public void RegisterUser(UserModel u) throws DataAccessException {
        String sql = "INSERT INTO Users (personID, email, userName, password, firstName, " +
                "lastName, gender) VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, u.getPersonID());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getUserName());
            stmt.setString(4, u.getPassword());
            stmt.setString(5, u.getFirstName());
            stmt.setString(6, u.getLastName());
            stmt.setString(7, u.getGender());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e.toString());
        }
    }
    public UserModel GetUserByID(String id) throws DataAccessException {
        UserModel userModel;
        ResultSet rs = null;
        String sql = "SELECT * FROM Users WHERE personID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                userModel = new UserModel(rs.getString("username"), rs.getString("email"), rs.getString("password"),
                        rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("personID"));

                return userModel;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event");
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
    public void clearUsers() throws DataAccessException {
        String sql = "DELETE FROM Users";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing Users table");
        }

    }
    /** deletes User by ID
     * @param id: String id
     * @return boolean success of deletion, false if Event does not exist
     */
    public void deleteUser(String id) throws DataAccessException {
        if(GetUserByID(id) == null){
            return;
        }
        String sql = "DELETE FROM Users WHERE personID = " + id;
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing User with ID " + id);
        }
    }

    /**
     * gets User by username
     * @param username: String username
     * @return User object with that Username
     */
    public UserModel getUserByUsername(String username) throws DataAccessException {
        UserModel userModel;
        ResultSet rs = null;
        String sql = "SELECT * FROM Users WHERE userName = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                userModel = new UserModel(rs.getString("userName"), rs.getString("email"), rs.getString("password"),
                        rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("personID"));

                return userModel;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding User");
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

}
