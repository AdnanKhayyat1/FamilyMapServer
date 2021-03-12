package Server;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDao;
import Models.UserModel;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;

public abstract class PostRequestHandler extends RequestHandler {
    public String readInputFromJSON(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }
    public boolean checkUserNameExists(String username) throws DataAccessException {
        Database db = new Database();
        boolean isThere = false;
        try {
            Connection conn = db.openConnection();
            UserDao uDao = new UserDao(conn);
            UserModel u = uDao.getUserByUsername(username);
            if (u == null) {
                isThere = false;
            } else {
                isThere = true;
            }
            db.closeConnection(true);
        } catch (DataAccessException e){
            db.closeConnection(false);
            e.printStackTrace();
        }
        return isThere;
    }
}
