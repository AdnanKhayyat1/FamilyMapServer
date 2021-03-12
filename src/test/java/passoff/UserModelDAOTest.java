package passoff;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDao;
import Models.UserModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class UserModelDAOTest {
    private Database db;
    private UserModel bestUserModel;

    @BeforeEach
    public void setUp(){
        db = new Database();
        bestUserModel = new UserModel("JD363", "JD@gmail.com", "JD12345",
                "john", "doe", "male", "33643");
    }
    @AfterEach
    public void tearDown() throws Exception {
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }
    @Test
    public void insertPass() throws Exception{
        UserModel compareTest = null;
        try{
            Connection conn = db.openConnection();

            UserDao uDao = new UserDao(conn);
            uDao.RegisterUser(bestUserModel);
            compareTest = uDao.GetUserByID(bestUserModel.getPersonID());
            db.closeConnection(true);
        } catch (DataAccessException e){
            db.closeConnection(false);
        }

        assertNotNull(compareTest);
        assertEquals(bestUserModel, compareTest);
    }
    @Test
    public void insertNull() throws Exception{
        boolean itWorks = true;
        try{
            Connection conn = db.openConnection();
            UserDao uDao = new UserDao(conn);
            UserModel sameID = new UserModel(null, "JD@gmail.com", null,
                    "john", null, "male", "33643");
            uDao.RegisterUser(sameID);
            db.closeConnection(true);
        }catch(DataAccessException e){
            db.closeConnection(false);
            itWorks = false;
        }
        assertFalse(itWorks);
    }
    @Test
    public void insertSameIDFail() throws Exception{
        boolean itWorks = true;
        try{
            Connection conn = db.openConnection();
            UserDao uDao = new UserDao(conn);
            uDao.RegisterUser(bestUserModel);
            UserModel sameID = new UserModel("JD363", "JD@gmail.com", "JD12345",
                    "john", "doe", "male", "33643");
            uDao.RegisterUser(sameID);
            db.closeConnection(true);
        }catch(DataAccessException e){
            db.closeConnection(false);
            itWorks = false;
        }
        assertFalse(itWorks);
    }
    @Test
    public void getPass() throws Exception{
        UserModel compareTest = null;
        UserModel u1 = new UserModel("user1", "u1@gmail.com", "xxx",
                "adam", "doe", "male", "12345");
        UserModel u2 = new UserModel("user2", "u2@gmail.com", "xxx",
                "mary", "doe", "female", "34561");
        try{
            Connection conn = db.openConnection();
            UserDao uDao = new UserDao(conn);
            uDao.RegisterUser(bestUserModel);
            uDao.RegisterUser(u1);
            uDao.RegisterUser(u2);
            compareTest = uDao.GetUserByID(u1.getPersonID());
            db.closeConnection(true);
        } catch (DataAccessException e){
            db.closeConnection(false);
        }

        assertNotNull(compareTest);
        assertEquals(u1, compareTest);
    }
    @Test
    public void getFail() throws Exception{
        String randomID = "00000";
        UserModel compareUserModel = bestUserModel;
        try{
            Connection conn = db.openConnection();
            UserDao uDao = new UserDao(conn);

            compareUserModel = uDao.GetUserByID(randomID);
            db.closeConnection(true);
        } catch (DataAccessException e){
            db.closeConnection(false);
        }
        assertNull(compareUserModel);
    }
    @Test
    public void clearPass() throws DataAccessException {
        UserModel compareTest = null;
        UserModel compareTest1 = null;
        UserModel compareTest2 = null;
        UserModel u1 = new UserModel("user1", "u1@gmail.com", "xxx",
                "adam", "doe", "male", "12345");
        UserModel u2 = new UserModel("user2", "u2@gmail.com", "xxx",
                "mary", "doe", "female", "34561");
        try{
            Connection conn = db.openConnection();
            UserDao uDao = new UserDao(conn);
            uDao.RegisterUser(bestUserModel);
            uDao.RegisterUser(u1);
            uDao.RegisterUser(u2);
            uDao.clearUsers();
            compareTest = uDao.GetUserByID(bestUserModel.getPersonID());
            compareTest1 = uDao.GetUserByID(u1.getPersonID());
            compareTest2 = uDao.GetUserByID(u2.getPersonID());

            db.closeConnection(true);
        } catch (DataAccessException e){
            db.closeConnection(false);
        }

        assertNull(compareTest);
        assertNull(compareTest1);
        assertNull(compareTest2);


    }
    @Test
    public void deletePass() throws DataAccessException {
        UserModel compareTest = null;
        UserModel compareTest1 = null;
        UserModel compareTest2 = null;
        UserModel u1 = new UserModel("user1", "u1@gmail.com", "xxx",
                "adam", "doe", "male", "12345");
        UserModel u2 = new UserModel("user2", "u2@gmail.com", "xxx",
                "mary", "doe", "female", "34561");
        try{
            Connection conn = db.openConnection();
            UserDao uDAO = new UserDao(conn);
            uDAO.RegisterUser(u1);
            uDAO.RegisterUser(u2);
            uDAO.RegisterUser(bestUserModel);
            uDAO.deleteUser(u1.getPersonID());
            uDAO.deleteUser(u2.getPersonID());
            compareTest = uDAO.GetUserByID(u1.getPersonID());
            compareTest1 = uDAO.GetUserByID(u2.getPersonID());
            compareTest2 = uDAO.GetUserByID(bestUserModel.getPersonID());
            db.closeConnection(true);
        } catch (DataAccessException e){
            db.closeConnection(false);
        }
        assertNull(compareTest);
        assertNull(compareTest1);
        assertNotNull(compareTest2);
    }
    @Test
    public void duplicateDeleteFail() throws DataAccessException {
        UserModel compareTest = null;
        UserModel u1 = new UserModel("user1", "u1@gmail.com", "xxx",
                "adam", "doe", "male", "12345");
        UserModel u2 = new UserModel("user2", "u2@gmail.com", "xxx",
                "mary", "doe", "female", "34561");
        try{
            Connection conn = db.openConnection();
            UserDao uDAO = new UserDao(conn);
            uDAO.RegisterUser(u1);
            uDAO.RegisterUser(u2);
            uDAO.deleteUser(u1.getPersonID());
            uDAO.deleteUser(u1.getPersonID());
            compareTest = uDAO.GetUserByID(u1.getPersonID());

            db.closeConnection(true);
        } catch (DataAccessException e){
            db.closeConnection(false);
        }
        assertNull(compareTest);
    }
    @Test
    public void getUsernamePass() throws DataAccessException {
        UserModel compareTest = null;
        try{
            Connection conn = db.openConnection();
            UserDao uDAO = new UserDao(conn);
            uDAO.RegisterUser(bestUserModel);
            compareTest = uDAO.getUserByUsername(bestUserModel.getUserName());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        assertNotNull(compareTest);
        assertEquals(bestUserModel, compareTest);
    }
    @Test
    public void getUsernameFail() throws DataAccessException {
        UserModel compareTest = null;
        try{
            Connection conn = db.openConnection();
            UserDao uDAO = new UserDao(conn);
            compareTest = uDAO.getUserByUsername(bestUserModel.getUserName());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        assertNull(compareTest);
    }
}
