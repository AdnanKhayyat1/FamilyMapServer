package Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDao;
import Models.UserModel;
import Request.FillReq;
import Request.LoginReq;
import Request.RegisterReq;
import Result.FillRes;
import Result.LoginRes;
import Result.RegisterRes;
import models.User;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.Connection;
import java.util.UUID;

public class Register {
    public static final int DEFAULT_NUM_GENS = 4;
    /**
     * allows the user to register
     * @param r  POST JSON object of RegisterRequest
     * @return Response object for register
     */
    public RegisterRes register(RegisterReq r) throws DataAccessException {
        Database db = new Database();
        Fill filler = new Fill();
        RegisterRes regRes = null;
        try {
            Connection conn = db.openConnection();
            UserDao uDao = new UserDao(conn);
            UserModel u = new UserModel(r.userName, r.email, r.password, r.firstName, r.lastName, r.gender, UUID.randomUUID().toString());
            if(uDao.getUserByUsername(r.userName) != null){
                throw new DataAccessException("error : User already registered");
            }
            uDao.RegisterUser(u);
            db.closeConnection(true);
            FillRes fRes = filler.fill(new FillReq(u.getUserName(), DEFAULT_NUM_GENS));
            if(fRes.isSuccess()){
                Login log = new Login();
                LoginReq logReq = new LoginReq(u.getUserName(), u.getPassword());
                LoginRes logRes = log.login(logReq);
                if(logRes.isSuccess()){
                    regRes = new RegisterRes(true, logRes.getAuthToken(), logRes.getUserName(), logRes.getPersonID());
                }
                else{
                    throw new DataAccessException("error : login not successful");
                }
            }
            else{
                throw new DataAccessException("error : registeration not successful");
            }

        }catch (DataAccessException | IOException e){
            regRes = new RegisterRes(false,e.toString());
            db.closeConnection(false);
            e.printStackTrace();
        }
        return regRes;
    }
}
