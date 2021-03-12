package passoff;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDAO;
import Models.EventModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//We will use this to test that our insert method is working and failing in the right ways
public class EventModelDAOTest {
    private Database db;
    private EventModel bestEventModel;

    @BeforeEach
    public void setUp() throws DataAccessException {
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        //and a new event with random data
        bestEventModel = new EventModel("Biking_123A", "Gale", "Gale123A",
                10.3f, 10.3f, "Japan", "Ushiku",
                "Biking_Around", 2016);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        //Here we close the connection to the database file so it can be opened elsewhere.
        //We will leave commit to false because we have no need to save the changes to the database
        //between test cases
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void insertPass() throws DataAccessException {
        Connection conn = db.openConnection();
        EventModel compareTest = null;
        try {
            EventDAO eDao = new EventDAO(conn);
            eDao.insertEvent(bestEventModel);
            compareTest = eDao.getEventByID(bestEventModel.getEventID());
            db.closeConnection(true);
        }catch (DataAccessException e){
            db.closeConnection(false);
        }


        assertNotNull(compareTest);
        assertEquals(bestEventModel, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        Connection conn = db.openConnection();
        EventModel compareTest = null;
        boolean caughtBoolean = false;
        EventDAO eDao = new EventDAO(conn);
        try {

            eDao.insertEvent(bestEventModel);
            eDao.insertEvent(bestEventModel);
            db.closeConnection(true);
        }catch (DataAccessException e){
            caughtBoolean = true;
            db.closeConnection(false);
        }
        assertTrue(caughtBoolean);
    }
    @Test
    public void insertEmpty() throws DataAccessException {
        //lets do this test again but this time lets try to make it fail
        //if we call the method the first time it will insert it successfully
        EventModel anotherEventModel = new EventModel(null, "Gale", "Sydney1111",
                30.3f, 16.6f, "USA", "Provo",
                "Walking", 2016);
        Connection conn = db.openConnection();
        EventModel compareTest = null;
        boolean caughtBoolean = false;
        EventDAO eDao = new EventDAO(conn);
        try {
            eDao.insertEvent(anotherEventModel);
            db.closeConnection(true);
        }catch (DataAccessException e){
            caughtBoolean = true;
            db.closeConnection(false);
        }
        assertTrue(caughtBoolean);
    }
    @Test
    public void getPass() throws DataAccessException {
        Connection conn = db.openConnection();
        EventModel compareTest = null;
        EventDAO eDao = new EventDAO(conn);
        try {
            eDao.insertEvent(bestEventModel);
            compareTest = eDao.getEventByID(bestEventModel.getEventID());
            db.closeConnection(true);
        }catch (DataAccessException e){
            db.closeConnection(false);
        }
        assertNotNull(compareTest);
        assertEquals(compareTest, bestEventModel);
    }
    @Test
    public void getByUsernamePass() throws DataAccessException {
        Connection conn = db.openConnection();
        List<EventModel> compareTest = null;
        EventDAO eDao = new EventDAO(conn);
        try {
            eDao.insertEvent(bestEventModel);
            compareTest = eDao.getEventsByUsername(bestEventModel.getUsername());
            db.closeConnection(true);
        }catch (DataAccessException | SQLException e){
            db.closeConnection(false);
        }
        assertNotNull(compareTest);
        assertTrue(compareTest.size() == 1);
    }
    @Test
    public void getFail() throws DataAccessException {
        Connection conn = db.openConnection();
        EventModel compareTest = null;
        EventDAO eDao = new EventDAO(conn);
        try {
            compareTest = eDao.getEventByID(bestEventModel.getEventID());
            db.closeConnection(true);
        }catch (DataAccessException e){
            db.closeConnection(false);
        }
        assertNull(compareTest);
        assertNotEquals(compareTest, bestEventModel);
    }
    @Test
    public void getByUsernameFail() throws DataAccessException {
        Connection conn = db.openConnection();
        List<EventModel> compareTest = null;
        EventDAO eDao = new EventDAO(conn);
        try {
            compareTest = eDao.getEventsByUsername(bestEventModel.getUsername());
            db.closeConnection(true);
        }catch (DataAccessException | SQLException e){
            db.closeConnection(false);
        }
        assertNotNull(compareTest);
        assertTrue(compareTest.isEmpty());
    }
    @Test
    public void deletePass() throws DataAccessException {
        Connection conn = db.openConnection();
        boolean isFaulty = false;
        EventDAO eDao = new EventDAO(conn);
        try {
            eDao.insertEvent(bestEventModel);
            eDao.deleteEventByID(bestEventModel.getEventID());
            db.closeConnection(true);
        }catch (DataAccessException e){
            isFaulty = true;
            db.closeConnection(false);
        }
        assertFalse(isFaulty);
    }
    @Test
    @DisplayName("Deleting a non-existent event")
    public void deleteFail() throws DataAccessException {
        Connection conn = db.openConnection();
        boolean isFaulty = false;
        EventDAO eDao = new EventDAO(conn);
        try {
            eDao.deleteEventByID(bestEventModel.getEventID());
            db.closeConnection(true);
        }catch (DataAccessException e){
            isFaulty = true;
            db.closeConnection(false);
        }
        assertTrue(isFaulty);
    }
    @Test
    public void getEventsByPersonPass() throws DataAccessException {
        EventModel e1 = new EventModel("a", "Gale", "Gale123A",
                10.3f, 10.3f, "Japan", "Ushiku",
                "Biking_Around", 2016);
        EventModel e2 = new EventModel("b", "Gale", "Gale123A",
                10.3f, 10.3f, "Japan", "Ushiku",
                "Biking_Around", 2016);
        EventModel e3 = new EventModel("c", "zzzz", "zzzzz",
                10.3f, 10.3f, "Japan", "Ushiku",
                "Biking_Around", 2016);
        Connection conn = db.openConnection();
        EventDAO eDao = new EventDAO(conn);
        List<EventModel> eventModels = null;
        try {
            eDao.insertEvent(e1);
            eDao.insertEvent(e2);
            eDao.insertEvent(e3);
            eventModels = eDao.getEventsByPersonID("Gale123A");
            db.closeConnection(true);
        }catch (DataAccessException | SQLException e){
            db.closeConnection(false);
        }
        assertNotNull(eventModels);
        assertTrue(eventModels.size() == 2);
    }
    @Test
    public void getEventsByPersonFail() throws DataAccessException {
        Connection conn = db.openConnection();
        EventDAO eDao = new EventDAO(conn);
        List<EventModel> eventModels = null;
        try {
            eventModels = eDao.getEventsByPersonID("Gale123A");
            db.closeConnection(true);
        }catch (DataAccessException | SQLException e){
            db.closeConnection(false);
        }
        assertNotNull(eventModels);
        assertTrue(eventModels.size() == 0);
    }

}
