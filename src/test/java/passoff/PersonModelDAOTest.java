package passoff;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Models.PersonModel;
import DataAccess.PersonDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PersonModelDAOTest {
    private Database db;
    private PersonModel bestPersonModel;

    @BeforeEach
    public void setUp(){
        db = new Database();
        bestPersonModel = new PersonModel("12345", "johndoe1", "John", "Doe", "m",
                "11111", "22222", "33333");
    }
    @AfterEach
    public void tearDown() throws Exception {
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }
    @Test
    public void insertPass() throws Exception{
        PersonModel compareTest = null;
        try{
            Connection conn = db.openConnection();
            PersonDao pDAO = new PersonDao(conn);
            pDAO.addPerson(bestPersonModel);
            compareTest = pDAO.getPersonByID(bestPersonModel.getPersonID());
            db.closeConnection(true);
        } catch (DataAccessException e){
            db.closeConnection(false);
        }

        assertNotNull(compareTest);
        assertEquals(bestPersonModel, compareTest);
    }

    @Test
    public void insertNull() throws Exception{
        boolean itWorks = true;
        try{
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            PersonModel nullUser = new PersonModel("12344", null, null, null,
                    "f", "14124", "3241", "4234");
            pDao.addPerson(nullUser);
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
            PersonDao pDao = new PersonDao(conn);
            pDao.addPerson(bestPersonModel);
            PersonModel sameID = new PersonModel("12345", null, null, null,
                    "f", "14124", "3241", "4234");
            pDao.addPerson(sameID);
            db.closeConnection(true);
        }catch(DataAccessException e){
            db.closeConnection(false);
            itWorks = false;
        }
        assertFalse(itWorks);
    }

    @Test
    public void getPass() throws Exception{
        PersonModel compareTest = null;
        PersonModel compareTest2 = null;
        PersonModel compareTest3 = null;
        PersonModel p1 = new PersonModel("1", "person1", "John", "Doe", "m",
                "11111", "22222", "33333");
        PersonModel p2 = new PersonModel("2", "person2", "John", "Doe", "m",
                "11111", "22222", "33333");
        try{
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            pDao.addPerson(bestPersonModel);
            pDao.addPerson(p1);
            pDao.addPerson(p2);
            compareTest = pDao.getPersonByID(p1.getPersonID());
            compareTest2 = pDao.getPersonByID(p2.getPersonID());
            compareTest3 = pDao.getPersonByID(bestPersonModel.getPersonID());
            db.closeConnection(true);
        } catch (DataAccessException e){
            db.closeConnection(false);
        }
        assertNotNull(compareTest3);
        assertNotNull(compareTest);
        assertNotNull(compareTest2);
        assertEquals(p1, compareTest);
        assertEquals(p2, compareTest2);
        assertEquals(bestPersonModel, compareTest3);
    }

    @Test
    public void getFail() throws Exception{
        String nullID = "00000";
        PersonModel comparePersonModel = bestPersonModel;
        try{
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);

             comparePersonModel = pDao.getPersonByID(nullID);
            db.closeConnection(true);
        } catch (DataAccessException e){
            db.closeConnection(false);
        }
        assertNull(comparePersonModel);
    }
    @Test
    public void deletePass() throws DataAccessException {
        PersonModel comparer = null;
        try{
            Connection conn = db.openConnection();
            PersonDao pDAO = new PersonDao(conn);
            pDAO.addPerson(bestPersonModel);
            PersonModel p1 = new PersonModel("1", "person1", "John", "Doe", "m",
                    "11111", "22222", "33333");
            //pDAO.addPerson(p1);
            pDAO.deletePerson(bestPersonModel.getPersonID());
            comparer = pDAO.getPersonByID(bestPersonModel.getPersonID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        assertNull(comparer);
    }
    @Test
    public void deleteFail() throws DataAccessException {
        PersonModel comparer = null;
        try{
            Connection conn = db.openConnection();
            PersonDao pDAO = new PersonDao(conn);
            pDAO.addPerson(bestPersonModel);
            pDAO.deletePerson("00000");
            comparer = pDAO.getPersonByID(bestPersonModel.getPersonID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        assertNotNull(comparer);
    }
    @Test
    public void clearPass() throws DataAccessException {
        PersonModel compareTest = null;
        PersonModel compareTest1 = null;
        PersonModel compareTest2 = null;
        PersonModel p1 = new PersonModel("1", "person1", "John", "Doe", "m",
                "11111", "22222", "33333");
        PersonModel p2 = new PersonModel("2", "person2", "John", "Doe", "m",
                "11111", "22222", "33333");
        try{
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            pDao.addPerson(bestPersonModel);
            pDao.addPerson(p1);
            pDao.addPerson(p2);
            pDao.clearPersons();
            compareTest = pDao.getPersonByID(bestPersonModel.getPersonID());
            compareTest1 = pDao.getPersonByID(p1.getPersonID());
            compareTest2 = pDao.getPersonByID(p2.getPersonID());

            db.closeConnection(true);
        } catch (DataAccessException e){
            db.closeConnection(false);
        }
        assertNull(compareTest);
        assertNull(compareTest1);
        assertNull(compareTest2);
    }
    @Test
    public void getFamilyPass() throws DataAccessException {
        PersonModel p1 = new PersonModel("12345", "johndoe1", "John", "Doe", "m",
                "11111", "22222", "33333");
        PersonModel p2 = new PersonModel("12222", "johndoe1", "Merry", "Doe", "f",
                "212121", "343434", "565656");
        PersonModel p3 = new PersonModel("99999", "anotheruser1", "Merry", "Doe", "f",
                "212121", "343434", "565656");
        List<PersonModel> personModelList = null;
        try{
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            pDao.addPerson(p1);
            pDao.addPerson(p2);
            pDao.addPerson(p3);
            personModelList = pDao.getFamily("johndoe1");
            db.closeConnection(true);
        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        assertNotNull(personModelList);
        assertFalse(personModelList.contains(p3));
        assertTrue(personModelList.contains(p1));
        assertTrue(personModelList.contains(p2));

    }
    @Test
    public void getFamilyEmpty() throws DataAccessException {

        List<PersonModel> personModelList = null;
        try{
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            personModelList = pDao.getFamily("johndoe1");
            db.closeConnection(true);
        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        assertTrue(personModelList.size() == 0);


    }

}
