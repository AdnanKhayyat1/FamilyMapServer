package Models;

import java.util.Objects;

/**
 * Person database model
 *
 */
public class PersonModel {
    /**
     * person user ID
     */
    private String personID;
    /**
     * person username
     */
    private String associatedUsername;
    /**
     * person first name
     */
    private String firstName;
    /**
     * person last name
     */
    private String lastName;
    /**
     * person gender
     */
    private String gender;
    /**
     * person father's ID (Refers to another Person object)
     */
    private String fatherID;
    /**
     * person mother's ID (Refers to another Person object)
     */
    private String motherID;
    /**
     * person spouse's ID (Refers to another Person object)
     */
    private String spouseID;
    public PersonModel(String id, String associatedUsername, String firstName, String lastName, String gender,
                       String fatherID, String motherID, String spouseID){
        this.personID = id;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    public PersonModel(UserModel userModel){
        associatedUsername = userModel.getUserName();
        firstName = userModel.getFirstName();
        lastName = userModel.getLastName();
        gender = userModel.getGender();
        personID = userModel.getPersonID();
        fatherID = null;
        motherID = null;
        spouseID = null;
    }


    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonModel personModel = (PersonModel) o;
        return Objects.equals(personID, personModel.personID) &&
                Objects.equals(associatedUsername, personModel.associatedUsername) &&
                Objects.equals(firstName, personModel.firstName) &&
                Objects.equals(lastName, personModel.lastName) &&
                Objects.equals(gender, personModel.gender) &&
                Objects.equals(fatherID, personModel.fatherID) &&
                Objects.equals(motherID, personModel.motherID) &&
                Objects.equals(spouseID, personModel.spouseID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personID, associatedUsername, firstName, lastName, gender, fatherID, motherID, spouseID);
    }
}
