package Result;

import Models.EventModel;

/**
 * Event getter by ID Json response object
 */
public class EventIDRes extends ParentRes {
    private String associatedUsername;
    private String eventID;
    private String personID;
    private Float latitude;
    private Float longitude;
    private String country;
    private String city;
    private String eventType;
    private Integer year;


    /**
     *
     * @param associatedUsername String of event's username associated with user
     * @param eventID String of event's unique ID
     * @param personID String of person's unique ID associated with this event
     * @param latitude String of event's latitude
     * @param longitude String of event's longitude
     * @param country String of event's country
     * @param city String of event's city
     * @param eventType String of event's type
     * @param year String of event's year
     */
    public EventIDRes(String associatedUsername, String eventID, String personID, float latitude,
                      float longitude, String country, String city, String eventType, int year) {
        super(false, "Description of the error");
        this.associatedUsername = associatedUsername;
        this.eventID = eventID;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }
    public EventIDRes(EventModel e, boolean success, String msg) {
        super(success, msg);
        this.associatedUsername = e.getUsername();
        this.eventID = e.getEventID();
        this.personID = e.getPersonID();
        this.latitude = e.getLatitude();
        this.longitude = e.getLongitude();
        this.country = e.getCountry();
        this.city = e.getCity();
        this.eventType = e.getEventType();
        this.year = e.getYear();
    }
    public EventIDRes(boolean success, String msg) {
        super(success, msg);
        latitude = null;
        longitude = null;
        year = null;

    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
