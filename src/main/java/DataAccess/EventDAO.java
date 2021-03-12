package DataAccess;

import Models.EventModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {
    private final Connection conn;

    public EventDAO(Connection conn)
    {
        this.conn = conn;
    }

    public void insertEvent(EventModel eventModel) throws DataAccessException {
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO Events (eventID, personID, associatedUsername, latitude, longitude, " +
                "country, city, eventType, year) VALUES(?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, eventModel.getEventID());
            stmt.setString(2, eventModel.getPersonID());
            stmt.setString(3, eventModel.getUsername());
            stmt.setFloat(4, eventModel.getLatitude());
            stmt.setFloat(5, eventModel.getLongitude());
            stmt.setString(6, eventModel.getCountry());
            stmt.setString(7, eventModel.getCity());
            stmt.setString(8, eventModel.getEventType());
            stmt.setInt(9, eventModel.getYear());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e.toString());
        }
    }

    public EventModel getEventByID(String eventID) throws DataAccessException {
        EventModel eventModel;
        ResultSet rs = null;
        String sql = "SELECT * FROM Events WHERE eventID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                eventModel = new EventModel(rs.getString("eventID"), rs.getString("associatedUsername"),
                        rs.getString("personID"), rs.getFloat("latitude"), rs.getFloat("longitude"),
                        rs.getString("country"), rs.getString("city"), rs.getString("eventType"),
                        rs.getInt("year"));
                return eventModel;
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
    public void deleteEventByID(String eventID) throws DataAccessException {
//        if(getEventByID(eventID) == null){
//            System.out.println("No such event to delete (ID : " + eventID + ")");
//            return;
//        }
        String sql  = "DELETE FROM Events WHERE eventID = " + eventID + ";";
        try(Statement stmt = conn.createStatement()){
            if(getEventByID(eventID) == null) {
                throw new DataAccessException("No such event to delete (ID : " + eventID + ")");
            }
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<EventModel> getEventsByPersonID(String personID) throws SQLException {
        List<EventModel> eventModels = new ArrayList<EventModel>();
        ResultSet rs = null;
        String sql = "SELECT * FROM Events WHERE personID = ?;" ;
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            while(rs.next()){
                EventModel currEventModel = new EventModel(rs.getString("eventID"), rs.getString("associatedUsername"),
                        rs.getString("personID"), rs.getFloat("latitude"), rs.getFloat("longitude"),
                        rs.getString("country"), rs.getString("city"), rs.getString("eventType"),
                        rs.getInt("year"));
                eventModels.add(currEventModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error encountered while getting events by person ID " + personID);
        } finally {
            if(rs != null){
                try{
                    rs.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }

            }
        }
        return eventModels;
    }
    public List<EventModel> getEventsByUsername(String username) throws SQLException {
        List<EventModel> eventModels = new ArrayList<EventModel>();
        ResultSet rs = null;
        String sql = "SELECT * FROM Events WHERE associatedUsername = ?;" ;
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            while(rs.next()){
                EventModel currEventModel = new EventModel(rs.getString("eventID"), rs.getString("associatedUsername"),
                        rs.getString("personID"), rs.getFloat("latitude"), rs.getFloat("longitude"),
                        rs.getString("country"), rs.getString("city"), rs.getString("eventType"),
                        rs.getInt("year"));
                eventModels.add(currEventModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error encountered while getting events by person ID " + username);
        } finally {
            if(rs != null){
                try{
                    rs.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }

            }
        }
        return eventModels;
    }
}
