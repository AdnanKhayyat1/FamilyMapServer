package Request;

import Models.*;

import java.util.List;

/**
 *  Load Request JSON object
 */
public class LoadReq {
    public List<UserModel> users;
    public List<PersonModel> persons;
    public List<EventModel> events;
    public LoadReq(List<UserModel> u, List<PersonModel> p, List<EventModel> e){
        users = u;
        persons = p;
        events = e;
    }

}
