package Models;

import java.util.Objects;

public class EventModel implements Comparable<EventModel> {
    private String eventType;
    private String personID;
    private String city;
    private String country;
    private float latitude;
    private float longitude;
    private int year;
    private String eventID;
    private String associatedUsername;







    public EventModel(String eventID, String username, String personID, float latitude, float longitude,
                      String country, String city, String eventType, int year) {
        this.eventID = eventID;
        this.associatedUsername = username;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }


    /**
     * Get the Event ID
     */
    public String getEventID() {
        return eventID;
    }


    /**
     * Set the Event ID
     */
    public void setEventID(String eventID) {
        this.eventID = eventID;
    }


    /**
     * Get the user's name
     */
    public String getUsername() {
        return associatedUsername;
    }


    /**
     * Set the user's name
     */
    public void setUsername(String username) {
        this.associatedUsername = username;
    }


    /**
     * Get the Person's ID
     */
    public String getPersonID() {
        return personID;
    }


    /**
     * Set the Person's ID
     */
    public void setPersonID(String personID) {
        this.personID = personID;
    }


    /**
     * Get the Latitude
     */
    public float getLatitude() {
        return latitude;
    }


    /**
     * Set the Latitude
     */
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }


    /**
     * Get the Longitude
     */
    public float getLongitude() {
        return longitude;
    }


    /**
     * Set the Longitude
     */
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }


    /**
     * Get the Country
     */
    public String getCountry() {
        return country;
    }


    /**
     * Set the Country
     */
    public void setCountry(String country) {
        this.country = country;
    }


    /**
     * Get the City
     */
    public String getCity() {
        return city;
    }


    /**
     * Set the City
     */
    public void setCity(String city) {
        this.city = city;
    }


    /**
     * Get Event Type
     */
    public String getEventType() {
        return eventType;
    }


    /**
     * Set the Event Type
     */
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }


    /**
     * Get the Year
     */
    public int getYear() {
        return year;
    }


    /**
     * Set the Year
     */
    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventModel eventModel = (EventModel) o;
        return Objects.equals(eventID, eventModel.eventID) &&
                Objects.equals(associatedUsername, eventModel.associatedUsername) &&
                Objects.equals(personID, eventModel.personID) &&
                Objects.equals(latitude, eventModel.latitude) &&
                Objects.equals(longitude, eventModel.longitude) &&
                Objects.equals(country, eventModel.country) &&
                Objects.equals(city, eventModel.city) &&
                Objects.equals(eventType, eventModel.eventType) &&
                Objects.equals(year, eventModel.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventID, associatedUsername, personID, latitude, longitude, country, city, eventType, year);
    }

    @Override
    public int compareTo(EventModel o) {
        if(o == null)
            return -1;
        return Integer.compare(this.year, o.getYear());
    }
}

