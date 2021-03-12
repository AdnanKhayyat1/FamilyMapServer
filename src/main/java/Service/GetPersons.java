package Service;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDao;
import Models.PersonModel;
import Result.PersonAllRes;
import Result.PersonIDRes;

import java.sql.Connection;
import java.util.List;

/**
 * Getter of all persons service handler, requires authToken
 */
public class GetPersons {
    public PersonAllRes getPersons(String authToken) throws DataAccessException {
        Database db = new Database();
        PersonAllRes pRes = null;
        List<PersonModel> persons = null;
        try {
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            AuthTokenDao auDao = new AuthTokenDao(conn);

            if(!auDao.AuthenticateUser(authToken)){
                throw new DataAccessException(" error : Bad or outdated authentication token, please try again");
            }
            else {
                String username = auDao.GetUsernameByToken(authToken);
                if (username != null){
                    persons = pDao.getFamily(username);
                    pRes = new PersonAllRes(persons, true, null);
                    db.closeConnection(true);
                }
                else{
                    throw new DataAccessException("No such username");
                }

            }
        } catch (DataAccessException e){
            pRes = new PersonAllRes(null, false, e.toString());
            db.closeConnection(false);
        }
        return pRes;
    }
}
