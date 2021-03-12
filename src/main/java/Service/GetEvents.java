package Service;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDAO;
import Models.EventModel;
import Result.EventAllRes;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Getter of all events service handler, requires authToken
 */
public class GetEvents {
    public EventAllRes getEvents(String AuthToken) throws DataAccessException {
        Database db = new Database();
        List<EventModel> events = null;
        EventAllRes allRes = null;
        try {
            Connection conn = db.openConnection();
            EventDAO eDao = new EventDAO(conn);
            AuthTokenDao auDao = new AuthTokenDao(conn);
            if (!auDao.AuthenticateUser(AuthToken)) {
                throw new DataAccessException("error : bad or outdated auth token");
            }
            String username = auDao.GetUsernameByToken(AuthToken);
            if(username != null) {
                events = eDao.getEventsByUsername(username);
                allRes = new EventAllRes(events, true, null);
                db.closeConnection(true);
            }
            else{
                throw new DataAccessException("error : no such username");
            }

        } catch (DataAccessException | SQLException e) {
            allRes = new EventAllRes(null, false, e.toString());
            db.closeConnection(false);
            e.printStackTrace();
        }
        return allRes;
    }
}
