package Service;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDAO;
import Models.EventModel;
import Result.EventIDRes;

import java.sql.Connection;

/**
 * Get event by unique ID service handler, requires authToken and event ID
 */
public class GetEventID {
    public EventIDRes getEventByID(String AuthToken, String eventID) throws DataAccessException {
        Database db = new Database();
        EventModel e = null;
        EventIDRes eRes = null;
        try {
            Connection conn = db.openConnection();
            EventDAO eDao = new EventDAO(conn);
            AuthTokenDao auDao = new AuthTokenDao(conn);
            String username = auDao.GetUsernameByToken(AuthToken);
            if(!auDao.AuthenticateUser(AuthToken)){
                throw new DataAccessException("error : bad or outdated auth token");
            }
            e = eDao.getEventByID(eventID);
            if(e == null || !e.getUsername().equals(username)){
                throw new DataAccessException("error : no such event");
            }
            else{
                eRes = new EventIDRes(e, true, null);
                db.closeConnection(true);
            }
        } catch (DataAccessException ex) {
            eRes = new EventIDRes(false, ex.toString());
            ex.printStackTrace();
            db.closeConnection(false);
        }
        return eRes;
    }
}
