package Service;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDao;
import Models.AuthToken;
import Models.PersonModel;
import Result.PersonIDRes;
import Server.Serializer;
import models.Person;

import java.sql.Connection;

/**
 * Get person by unique ID service handler, requires authToken and person ID
 */
public class GetPersonID {
    public PersonIDRes getPersonById(String personID, String token) throws DataAccessException {
        Database db = new Database();
        PersonModel p = null;
        PersonIDRes pRes = null;
        try {
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            AuthTokenDao auDao = new AuthTokenDao(conn);
            String username = auDao.GetUsernameByToken(token);
            if(!auDao.AuthenticateUser(token)){
                throw new DataAccessException(" error : Bad or outdated authentication token, please try again");
            }
            else {
                p = pDao.getPersonByID(personID);
                if(p == null || !p.getAssociatedUsername().equals(username)){
                    throw new DataAccessException(" error : No such person with ID " + personID);
                }
                pRes = new PersonIDRes(true, p.getAssociatedUsername(),
                        p.getPersonID(), p.getFirstName(), p.getLastName(),
                        p.getGender(), p.getFatherID(), p.getMotherID(), p.getSpouseID());
                db.closeConnection(true);
            }
        } catch (DataAccessException e){
            pRes = new PersonIDRes(false, e.toString());
            db.closeConnection(false);
        }
        return pRes;
    }
}
