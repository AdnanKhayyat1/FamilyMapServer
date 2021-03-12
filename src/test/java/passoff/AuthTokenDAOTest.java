package passoff;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import Models.AuthToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class AuthTokenDAOTest {
    private Database db;
    private AuthToken bestAuthToken;

    @BeforeEach
    public void setUp(){
        db = new Database();
        bestAuthToken = new AuthToken("432f843nf", "user1", "password123");
    }
    @AfterEach
    public void tearDown() throws Exception {
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }
    @DisplayName("Insert Successful test")
    @Test
    public void InsertPass() throws DataAccessException {
        AuthToken compareTest = null;
        try{
            Connection conn = db.openConnection();
            AuthTokenDao auDAO = new AuthTokenDao(conn);
            auDAO.addAuthToken(bestAuthToken);
            compareTest = auDAO.GetAuthTokenByUsername(bestAuthToken.getUserName());
            db.closeConnection(true);
        } catch (DataAccessException e){
            db.closeConnection(false);
        }

        assertNotNull(compareTest);
        assertEquals(bestAuthToken, compareTest);
    }
    @DisplayName("Insert Failure test (Duplicate Insertion)")
    @Test
    public void InsertFail() throws DataAccessException {
        AuthToken compareTest = null;
        boolean isFailure = false;
        try{
            Connection conn = db.openConnection();
            AuthTokenDao auDAO = new AuthTokenDao(conn);
            auDAO.addAuthToken(bestAuthToken);
            auDAO.addAuthToken(bestAuthToken);
            db.closeConnection(true);
        } catch (DataAccessException e){
            isFailure = true;
            db.closeConnection(false);
        }
        assertTrue(isFailure);
    }
    @DisplayName("Get username by authToken")
    @Test
    public void getUsernamePass() throws DataAccessException {
        String userName = null;
        try{
            Connection conn = db.openConnection();
            AuthTokenDao auDAO = new AuthTokenDao(conn);
            auDAO.addAuthToken(bestAuthToken);
            userName = auDAO.GetUsernameByToken(bestAuthToken.getAuthToken());
            db.closeConnection(true);
        } catch (DataAccessException e){
            db.closeConnection(false);
        }
        assertNotNull(userName);
        assertEquals("user1", userName);
    }
    @DisplayName("Get username by authToken (FAIL)")
    @Test
    public void getUsernameFail() throws DataAccessException {
        String userName = null;
        try{
            Connection conn = db.openConnection();
            AuthTokenDao auDAO = new AuthTokenDao(conn);
            userName = auDAO.GetUsernameByToken(bestAuthToken.getAuthToken());
            db.closeConnection(true);
        } catch (DataAccessException e){
            db.closeConnection(false);
        }
        assertNull(userName);
    }
    @DisplayName("Getter Successful test")
    @Test
    public void GetPass() throws DataAccessException {
        AuthToken compareTest = null;
        try{
            Connection conn = db.openConnection();
            AuthTokenDao auDAO = new AuthTokenDao(conn);
            auDAO.addAuthToken(bestAuthToken);
            compareTest = auDAO.GetAuthTokenByUsername(bestAuthToken.getUserName());
            db.closeConnection(true);
        } catch (DataAccessException e){
            db.closeConnection(false);
        }

        assertNotNull(compareTest);
        assertEquals(bestAuthToken, compareTest);
    }
    @DisplayName("Authenticate user pass")
    @Test
    public void AuthPass() throws DataAccessException {
        boolean isAuthed = false;
        try{
            Connection conn = db.openConnection();
            AuthTokenDao auDAO = new AuthTokenDao(conn);
            auDAO.addAuthToken(bestAuthToken);
            isAuthed = auDAO.AuthenticateUser(bestAuthToken.getAuthToken());
            db.closeConnection(true);
        } catch (DataAccessException e){
            db.closeConnection(false);
        }
        assertTrue(isAuthed);
    }
    @DisplayName("Authenticate user fail")
    @Test
    public void AuthFail() throws DataAccessException {
        boolean isAuthed = false;
        try{
            Connection conn = db.openConnection();
            AuthTokenDao auDAO = new AuthTokenDao(conn);
            isAuthed = auDAO.AuthenticateUser(bestAuthToken.getAuthToken());
            db.closeConnection(true);
        } catch (DataAccessException e){
            db.closeConnection(false);
        }
        assertFalse(isAuthed);
    }
    @DisplayName("Getter Failure test (Getter of non-existent object)")
    @Test
    public void GetFail() throws DataAccessException {
        AuthToken compareTest = null;
        try{
            Connection conn = db.openConnection();
            AuthTokenDao auDAO = new AuthTokenDao(conn);
            compareTest = auDAO.GetAuthTokenByUsername(bestAuthToken.getUserName());
            db.closeConnection(true);
        } catch (DataAccessException e){
            db.closeConnection(false);
        }
        assertNull(compareTest);
    }

    @DisplayName("Clear test")
    @Test
    public void clearTest() throws DataAccessException {
        AuthToken compareTest = null;
        try{
            Connection conn = db.openConnection();
            AuthTokenDao auDAO = new AuthTokenDao(conn);
            auDAO.addAuthToken(bestAuthToken);
            auDAO.clearAllTokens();
            compareTest = auDAO.GetAuthTokenByUsername(bestAuthToken.getUserName());
            db.closeConnection(true);
        } catch (DataAccessException e){
            db.closeConnection(false);
        }
        assertNull(compareTest);
    }
    @DisplayName("Delete test successful")
    @Test
    public void deletePass() throws DataAccessException {
        AuthToken compareTest = null;
        AuthToken compareTest1 = null;
        AuthToken au1 = new AuthToken("1", "user1", "password123");
        AuthToken au2 = new AuthToken("2", "user2", "password123");
        try{
            Connection conn = db.openConnection();
            AuthTokenDao auDAO = new AuthTokenDao(conn);
            auDAO.addAuthToken(au1);
            auDAO.addAuthToken(au2);
            auDAO.deleteAuth(au1.getUserName());
            compareTest = auDAO.GetAuthTokenByUsername(au1.getUserName());
            compareTest1 = auDAO.GetAuthTokenByUsername(au2.getUserName());
            db.closeConnection(true);
        } catch (DataAccessException e){
            db.closeConnection(false);
        }
        assertNull(compareTest);
        assertNotNull(compareTest1);
    }
    @DisplayName("Delete test fail (Deleting non-existent object")
    @Test
    public void deleteFail() throws DataAccessException {
        AuthToken compareTest = null;
        AuthToken compareTest1 = null;
        boolean isFailure = false;
        AuthToken au1 = new AuthToken("1", "user1", "password123");
        try{
            Connection conn = db.openConnection();
            AuthTokenDao auDAO = new AuthTokenDao(conn);
            auDAO.deleteAuth(au1.getUserName());
            db.closeConnection(true);
        } catch (DataAccessException e){
            isFailure = true;
            db.closeConnection(false);
        }
        assertTrue(isFailure);
    }

}
