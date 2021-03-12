package DataAccess;

import Models.PersonModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Accesses the Persons Table with various editing abilities
 */
public class PersonDao {
    final private Connection conn;
    public PersonDao(Connection conn){
        this.conn = conn;
    }
    /**
     * adds Person object to the table
     * @param p : Person object to be added
     * @return boolean : insertion success, could be false if object is not unique
     */
    public void addPerson(PersonModel p) throws DataAccessException {
        String sql = "INSERT INTO Persons (personID, associatedUsername, firstName, lastName, gender, " +
                "fatherID, motherID, spouseID) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getPersonID());
            stmt.setString(2, p.getAssociatedUsername());
            stmt.setString(3, p.getFirstName());
            stmt.setString(4, p.getLastName());
            stmt.setString(5, p.getGender());
            stmt.setString(6, p.getFatherID());
            stmt.setString(7, p.getMotherID());
            stmt.setString(8, p.getSpouseID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e.toString());
        }
    }

    /**
     * deletes all persons from the table
     */
    public void clearPersons() throws DataAccessException {
        String sql = "DELETE FROM Persons";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing Users table");
        }
    }
    /**
     * gets Person by ID
     * @param id: String id
     * @return Person object with that User ID
     */
    public PersonModel getPersonByID(String id) throws DataAccessException {
        PersonModel personModel;
        ResultSet rs = null;
        String sql = "SELECT * FROM Persons WHERE personID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                personModel = new PersonModel(rs.getString("personID"), rs.getString("associatedUsername"), rs.getString("firstName"),
                        rs.getString("lastName"), rs.getString("gender"), rs.getString("fatherID"),
                        rs.getString("motherID"), rs.getString("spouseID"));

                return personModel;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException(e.toString());
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
     * gets list of persons by username
     * @param username: String username
     * @return List of person objects with that Username
     */
    public List<PersonModel> getFamily(String username) throws DataAccessException {
        String sql = "SELECT * FROM Persons WHERE Persons.associatedUsername = ?";
        ResultSet rs = null;
        List<PersonModel> personModels = null;
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, username);

            rs = stmt.executeQuery();
            personModels = new ArrayList<PersonModel>();
            while(rs.next()){
                PersonModel p = new PersonModel(rs.getString(1), rs.getString(2),
                        rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),
                        rs.getString(7),rs.getString(8));
                personModels.add(p);
            }

        }catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Error encountered while obtaining family data");
        }finally{
            if(rs != null){
                try {
                    rs.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return personModels;
    }
    /**
     *  deletes Person by ID
     * @param id: String id
     * @return boolean success of deletion, false if Event does not exist
     */
    public void deletePerson(String id) throws DataAccessException {
        if(getPersonByID(id) == null){
            System.out.println("No such person to delete : " + id);
            return;
        }
        String sql = "DELETE FROM Persons WHERE personID = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,id);
            stmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing Person with ID " + id);
        }
    }

}
