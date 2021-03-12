package Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Result.ClearRes;
/**
 * Clears all databases with clear() method
 */
public class Clear {
    /**
     * Clears all databases with clear() method
     */
    public ClearRes clear() throws DataAccessException {
        Database db = new Database();
        ClearRes cr = null;
        try {
            db.openConnection();
            db.clearTables();
            db.closeConnection(true);
            cr = new ClearRes(true, "Clear succeeded");
        }
        catch(DataAccessException e){
            db.closeConnection(false);
            cr = new ClearRes(false, e.toString());
        }
        return cr;
    }
}
