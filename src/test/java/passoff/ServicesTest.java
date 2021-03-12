package passoff;

import DataAccess.*;
import Models.AuthToken;
import Models.EventModel;
import Models.PersonModel;
import Models.UserModel;
import Request.FillReq;
import Request.LoadReq;
import Request.LoginReq;
import Request.RegisterReq;
import Result.*;
import Service.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ServicesTest {
    Database db;
    final String UNIQUE_EVENT_ID = "bb123";

    @BeforeEach
    public void SetUp() throws DataAccessException {
        /*
            Populate the database
         */
        db = new Database();
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
        UserDao uDao = new UserDao(db.getConnection());
        AuthTokenDao auDao = new AuthTokenDao(db.getConnection());
        UserModel u = new UserModel("user1", "email1",
                "pass1", "Mike", "Wazowski",
                "m","mike-wazowski");
        AuthToken auth = new AuthToken("secret", "user1", "mike-wazowski");
        uDao.RegisterUser(u);
        auDao.addAuthToken(auth);
        EventDAO eDao = new EventDAO(db.getConnection());
        EventModel e = new EventModel(UNIQUE_EVENT_ID, "user1", "mike-wazowski", 1.0f,
                -5.0f, "USA", "Provo", "testing", 2020);
        eDao.insertEvent(e);
        PersonDao pDao = new PersonDao(db.getConnection());
        PersonModel p = new PersonModel("per1", "user1", "Another",
                "Person", "f", null, null, null);
        pDao.addPerson(p);
        db.closeConnection(true);
    }
    @AfterEach
    public void tearDown() throws Exception {
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);

    }
    @DisplayName("Clear pass")
    @Test
    public void clearPass() throws DataAccessException {

    }
    @DisplayName("Load pass")
    @Test
    public void loadPass() throws DataAccessException, SQLException {
        Load ld = new Load();
        LoadReq LReq = new LoadReq(new ArrayList<UserModel>(),new ArrayList<PersonModel>(), new ArrayList<EventModel>());
        LReq.events = new ArrayList<EventModel>();
        EventModel e = new EventModel("Biking_123A", "Gale", "Gale123A",
                10.3f, 10.3f, "Japan", "Ushiku",
                "Biking_Around", 2016);
        LReq.events.add(e);
        LoadRes LRes = ld.load(LReq);
        assertTrue(LRes.isSuccess());
        EventDAO eDao = new EventDAO(db.getConnection());
        List<EventModel> events = eDao.getEventsByPersonID("Gale123A");
        db.closeConnection(true);
        assertTrue(events.size() == 1);
    }
    @DisplayName("Load fail")
    @Test
    public void loadFail() throws DataAccessException, SQLException {
        Load ld = new Load();
        LoadReq LReq = new LoadReq(new ArrayList<UserModel>(),new ArrayList<PersonModel>(), new ArrayList<EventModel>());
        LReq.events = new ArrayList<EventModel>();
        EventModel e = new EventModel(null, "Gale", "Gale123A",
                10.3f, 10.3f, "Japan", "Ushiku",
                "Biking_Around", 2016);
        LReq.events.add(e);
        LoadRes LRes = ld.load(LReq);
        assertFalse(LRes.isSuccess());
        EventDAO eDao = new EventDAO(db.getConnection());
        List<EventModel> events = eDao.getEventsByPersonID("Gale123A");
        db.closeConnection(true);
        assertTrue(events.isEmpty());
    }
    @DisplayName("Login pass")
    @Test
    public void loginPass() throws DataAccessException {
        Login login = new Login();
        LoginReq loginReq = new LoginReq("user1", "pass1");
        LoginRes logRes = login.login(loginReq);
        assertTrue(logRes.isSuccess());
        assertEquals("mike-wazowski", logRes.getPersonID());
    }
    @DisplayName("Login fail")
    @Test
    public void loginFail() throws DataAccessException {
        Login login = new Login();
        LoginReq loginReq = new LoginReq("fakeUser", "non-existant");
        LoginRes logRes = login.login(loginReq);
        assertFalse(logRes.isSuccess());
        assertNull(logRes.getAuthToken());
    }
    @DisplayName("Register pass")
    @Test
    public void registerPass() throws DataAccessException, SQLException {
        Register rg = new Register();
        RegisterReq rReq = new RegisterReq();
        rReq.userName = "user2";
        rReq.password = "pass2";
        rReq.email = "email22";
        rReq.firstName = "Another";
        rReq.lastName = "User";
        rReq.gender = "f";
        RegisterRes rRes = rg.register(rReq);
        assertTrue(rRes.isSuccess());

        Connection conn = db.openConnection();
        EventDAO eDao = new EventDAO(conn);
        PersonDao pDao = new PersonDao(conn);
        UserDao userDao = new UserDao(conn);
        assertFalse(pDao.getFamily("user2").isEmpty());
        assertFalse(eDao.getEventsByPersonID(rRes.getPersonID()).isEmpty());
        assertNotNull(userDao.getUserByUsername(rRes.getUserName()));
        db.closeConnection(true);
    }
    @DisplayName("Register fail (duplicate registration)")
    @Test
    public void registerFail() throws DataAccessException, SQLException {
        Register rg = new Register();
        RegisterReq rReq = new RegisterReq();
        rReq.userName = "user1"; //user already registered
        rReq.password = "pass2";
        rReq.email = "email22";
        rReq.firstName = "Another";
        rReq.lastName = "User";
        rReq.gender = "f";
        RegisterRes rRes = rg.register(rReq);
        assertFalse(rRes.isSuccess());
    }
    @DisplayName("Fill pass")
    @Test
    public void fillPass() throws IOException, DataAccessException, SQLException {
        Fill fill = new Fill();
        FillReq fReq = new FillReq("user1", 1);
        FillRes fRes = fill.fill(fReq);
        assertTrue(fRes.isSuccess());
        Connection conn = db.openConnection();
        EventDAO eDao = new EventDAO(conn);
        PersonDao pDao = new PersonDao(conn);
        assertFalse(pDao.getFamily("user1").isEmpty());
        assertFalse(eDao.getEventsByPersonID("mike-wazowski").isEmpty());
        db.closeConnection(true);
    }
    @DisplayName("Fill fail (non-existent user)")
    @Test
    public void fillFail() throws IOException, DataAccessException, SQLException {
        Fill fill = new Fill();
        FillReq fReq = new FillReq("non-existent", 1);
        FillRes fRes = fill.fill(fReq);
        assertFalse(fRes.isSuccess());
        if(fRes.isSuccess()) {
            Connection conn = db.openConnection();
            EventDAO eDao = new EventDAO(conn);
            PersonDao pDao = new PersonDao(conn);
            assertTrue(pDao.getFamily("user1").isEmpty());
            db.closeConnection(true);
        }
    }

    @DisplayName("Get event by ID Pass")
    @Test
    public void getEventIdPass() throws DataAccessException {
        GetEventID getter = new GetEventID();
        EventIDRes eIdRes = getter.getEventByID("secret", UNIQUE_EVENT_ID);
        assertTrue(eIdRes.isSuccess());
        assertEquals("mike-wazowski", eIdRes.getPersonID());
    }
    @DisplayName("Get event by ID Fail")
    @Test
    public void getEventIdFail() throws DataAccessException {
        GetEventID getter = new GetEventID();
        EventIDRes eIdRes = getter.getEventByID("outdated-token", UNIQUE_EVENT_ID);
        assertFalse(eIdRes.isSuccess());
    }
    @DisplayName("Get all events Pass")
    @Test
    public void getEventsPass() throws DataAccessException {
        GetEvents getter = new GetEvents();
        EventAllRes allRes = getter.getEvents("secret");
        assertTrue(allRes.isSuccess());
        assertFalse(allRes.getData().isEmpty());
    }
    @DisplayName("Get all events Fail")
    @Test
    public void getEventsFail() throws DataAccessException {
        GetEvents getter = new GetEvents();
        EventAllRes allRes = getter.getEvents("out-dated-token");
        assertFalse(allRes.isSuccess());
    }
    @DisplayName("Get person by ID Pass")
    @Test
    public void getPersonIdPass() throws DataAccessException {
        GetPersonID getter = new GetPersonID();
        PersonIDRes pIdRes = getter.getPersonById( "per1", "secret");
        assertTrue(pIdRes.isSuccess());
        assertEquals("user1", pIdRes.getAssociatedUsername());
    }
    @DisplayName("Get person by ID Fail")
    @Test
    public void getPersonIdFail() throws DataAccessException {
        GetPersonID getter = new GetPersonID();
        PersonIDRes pIdRes = getter.getPersonById( "fake_person", "secret");
        assertFalse(pIdRes.isSuccess());
        assertNull(pIdRes.getAssociatedUsername());
    }
    @DisplayName("Get all persons Pass")
    @Test
    public void getPersonsPass() throws DataAccessException {
        GetPersons getter = new GetPersons();
        PersonAllRes allRes = getter.getPersons("secret");
        assertTrue(allRes.isSuccess());
        assertFalse(allRes.getData().isEmpty());
    }
    @DisplayName("Get all persons fail")
    @Test
    public void getPersonsFail() throws DataAccessException {
        GetPersons getter = new GetPersons();
        PersonAllRes allRes = getter.getPersons("outdated-token");
        assertFalse(allRes.isSuccess());

    }


}
