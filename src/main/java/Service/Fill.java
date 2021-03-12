package Service;

import DataAccess.*;
import FamilyMap.JsonUtils;
import FamilyMap.Location;
import Models.EventModel;
import Models.PersonModel;
import Models.UserModel;
import Request.FillReq;
import Result.FillRes;
import models.Event;
import models.Person;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Service handler that allows for fulfillment of data
 */
public class Fill {
    private String username;
    private int generations;
    private JsonUtils util;
    private Random rand;

    private int numEvents;
    private int numPeople;

    private PersonDao pDao;
    private EventDAO eDao;
    private UserDao uDao;
    /**
     * Service handler that allows for fulfillment of data
     * @param r FillRequest object for JSON mapping
     * @return FillResult object for response (Web APIs)
     */
    public FillRes fill(FillReq r) throws DataAccessException, IOException {
        numEvents = 0;
        numPeople = 0;
        rand = new Random();
        util = new JsonUtils();
        username = r.username;
        generations = r.generation;
        Database db = new Database();
        FillRes fres = null;
        try {
            Connection conn = db.openConnection();
            pDao = new PersonDao(conn);
            eDao = new EventDAO(conn);
            uDao = new UserDao(conn);
            List<PersonModel> persons = pDao.getFamily(r.username);
            for (int i = 0; i < persons.size(); i++) {
                pDao.deletePerson(persons.get(i).getPersonID());
            }
            List<EventModel> events = eDao.getEventsByUsername(r.username);
            for (int i = 0; i < events.size(); i++) {
                eDao.deleteEventByID(events.get(i).getEventID());
            }
            UserModel u = uDao.getUserByUsername(r.username);
            if(u == null){
                throw new DataAccessException("User is null error");
            }
            PersonModel userPerson = new PersonModel(u);
            numPeople++;
            int yr = (int)(rand.nextDouble() * 500) + 1800;
            generateEvents(userPerson, yr);
            recFamilyTree(userPerson, generations, yr-30);
            db.closeConnection(true);
            fres = new FillRes("Successfully added " + numPeople + " persons and " +
                   numEvents + " events to the database.", true);
        }
        catch (DataAccessException | SQLException e){
            fres = new FillRes("error : " + e.toString(), false);
            e.printStackTrace();
            db.closeConnection(false);
        }
        return fres;
    }
    private PersonModel recFamilyTree(PersonModel person, int gens, int yr) throws DataAccessException, SQLException {
        if(gens <= 0){
            pDao.addPerson(person);
            numPeople++;
            return null;
        }
        gens--;
        int birthYear =yr;
        PersonModel father = generatePerson(true, birthYear);
        PersonModel mother = generatePerson(false, birthYear);
        if(father != null && mother != null)
        {
            marry(father, mother);
            person.setFatherID(father.getPersonID());
            person.setMotherID(mother.getPersonID());
            father = recFamilyTree(father, gens, yr- 30);
            mother = recFamilyTree(mother, gens, yr - 30);
        }
        pDao.addPerson(person);
        numPeople++;
        return person;
    }
    private void marry(PersonModel dad, PersonModel mum) throws SQLException, DataAccessException {
        List<EventModel> events = eDao.getEventsByPersonID(dad.getPersonID());
        if(events != null && events.size() > 0)
        {
            Collections.sort(events);
            int marriageYear = events.get(0).getYear() + (int)(rand.nextDouble() * 5) + 30;
            for (int i=0; i < events.size(); i++) {
                if(events.get(i).getEventType().equals("death")){
                    int deathYear = events.get(i).getYear();
                    if(deathYear <= marriageYear){
                        marriageYear = deathYear - 5;
                    }
                }
            }


            EventModel marriage = makeEvent(dad, "marriage", marriageYear);

            marriage.setPersonID(mum.getPersonID());
            marriage.setEventID(UUID.randomUUID().toString());
            eDao.insertEvent(marriage);
            numEvents++;
            dad.setSpouseID(mum.getPersonID());
            mum.setSpouseID(dad.getPersonID());
        }

    }
    private PersonModel generatePerson(boolean isMale, int year) throws DataAccessException {
        String personID = UUID.randomUUID().toString();
        String lastName = util.getRandomSNames();
        String firstName = null;
        String gender = null;
        if(isMale){
            firstName = util.getRandomMNames();
            gender = "m";
        }
        else{
            firstName = util.getRandomFName();
            gender = "f";
        }
        PersonModel person = new PersonModel(personID, username, firstName, lastName,
                gender, null, null, null);
        generateEvents(person, year);
        return person;

    }
    private void generateEvents(PersonModel person, int birthYearStart) throws DataAccessException {
        int birthYear = ((int)(rand.nextDouble() * 8) + birthYearStart) - 4;
        int deathYear = birthYear + (int)(rand.nextDouble() * 75) + 20;

        int baptismYear = rand.nextInt(deathYear - birthYear) + birthYear;

        if(rand.nextDouble() > (Math.abs(2020 - birthYear)/birthYear)) {
            makeEvent(person, "birth", birthYear);
        }
        if(rand.nextDouble() > (Math.abs(2020 - deathYear)/deathYear)) {
            makeEvent(person, "death", deathYear);
        }
        if(rand.nextDouble() > (Math.abs(2020 - baptismYear) / baptismYear) * 4.0) {
            makeEvent(person, "baptism", baptismYear);
        }
    }
    private EventModel makeEvent(PersonModel p, String eType, int yr) throws DataAccessException {
        String eventID = UUID.randomUUID().toString();
        Location loc = util.getRandomLocation();
        EventModel e = new EventModel(eventID,username,p.getPersonID(),loc.getLatitude(),
                loc.getLongitude(), loc.getCountry(), loc.getCity(),eType, yr);
        eDao.insertEvent(e);
        numEvents++;
        return e;
    }
}
