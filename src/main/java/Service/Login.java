package Service;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDao;
import Models.AuthToken;
import Models.UserModel;
import Request.LoginReq;
import Result.LoginRes;
import models.User;

import java.sql.Connection;
import java.util.UUID;

public class Login {
    /**
     * allows the user to login
     * @param r : Sends a Login request JSON POST API
     * @return Login result
     */
    public LoginRes login(LoginReq r) throws DataAccessException {
        Database db = new Database();
        LoginRes LRes = null;
        try{
            Connection conn = db.openConnection();
            String uuid = UUID.randomUUID().toString();

            UserDao uDao = new UserDao(conn);
            AuthTokenDao auDao = new AuthTokenDao(conn);
            UserModel u = uDao.getUserByUsername(r.userName);
            if(u == null){
                throw new DataAccessException("error : " + r.userName + " does not exist, check your credentials");
//                LRes = new LoginRes(false, " error : " + r.userName + " does not exist, check your credentials");
//                db.closeConnection(false);
               // throw new DataAccessException("error : " + r.userName + " does not exist, check your credentials");
            }
            else if(!u.getPassword().equals(r.password)){
                throw new DataAccessException("error : " + "Wrong password entered");

            }
            else {
                AuthToken at = new AuthToken(uuid, u.getUserName(), u.getPersonID());
                auDao.addAuthToken(at);
                LRes = new LoginRes(true, uuid, u.getUserName(), u.getPersonID());
                db.closeConnection(true);
            }
        } catch (DataAccessException e) {
            LRes = new LoginRes(false, e.toString());
            db.closeConnection(false);
            e.printStackTrace();
        }
        return LRes;
    }

}
