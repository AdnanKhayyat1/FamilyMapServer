package Server;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class RequestHandler implements HttpHandler {
    public void writeToJSON(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
    public boolean authenticateToken(String token) throws DataAccessException {
        Database db = new Database();
        Connection conn = db.openConnection();
        AuthTokenDao auDao = new AuthTokenDao(conn);
        boolean result = auDao.AuthenticateUser(token);
        db.closeConnection(true);
        return result;
    }
}
