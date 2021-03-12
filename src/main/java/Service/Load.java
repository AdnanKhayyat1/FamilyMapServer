package Service;

import DataAccess.*;
import Models.EventModel;
import Models.PersonModel;
import Models.UserModel;
import Request.LoadReq;
import Result.LoadRes;
import models.User;

import java.beans.EventSetDescriptor;
import java.sql.Connection;
import java.util.List;

public class Load {
    /**
     * Loads a certain object
     * @param req : Sends a Load request JSON POST API
     * @return Load result
     */
    public LoadRes load(LoadReq req) throws DataAccessException {
        Database db = new Database();
        LoadRes lRes = null;
        List<UserModel> users = req.users;
        List<PersonModel> persons = req.persons;
        List<EventModel> events = req.events;
        try {
            Connection conn = db.openConnection();
            db.clearTables();
            UserDao uDao = new UserDao(conn);
            for (int i = 0; i < users.size(); i++) {
                uDao.RegisterUser(users.get(i));
            }
            PersonDao pDao = new PersonDao(conn);
            for (int i = 0; i < persons.size(); i++) {
                pDao.addPerson(persons.get(i));
            }
            EventDAO eDao = new EventDAO(conn);
            for (int i = 0; i < events.size(); i++) {
                eDao.insertEvent(events.get(i));
            }
            db.closeConnection(true);
            String message = "Successfully added " + users.size() + " users, " + persons.size() + " persons, and " + events.size() + " events to the database.";
            lRes = new LoadRes(true, message);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            lRes = new LoadRes(false, e.toString());
            e.printStackTrace();
        }
        return lRes;
    }
}
