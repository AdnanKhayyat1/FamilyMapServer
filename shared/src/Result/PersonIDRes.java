package Result;

/**
 * Person getter by ID Json response object
 */
public class PersonIDRes extends ParentRes {
    private String associatedUsername;
    private String personID;
    private String firstName;
    private String lastName;
    private String gender;
    private String fatherID;
    private String motherID;
    private String spouseID;

    /**
     *
     * @param associatedUsername String of person's username associated with user
     * @param personID String of person's unique ID
     * @param firstName String of person's first name
     * @param lastName String of person's last name
     * @param gender String of person's gender
     * @param fatherID String of person's father ID string (optional)
     * @param motherID String of person's mother ID string (optional)
     * @param spouseID String of person's spouse ID string (optional)
     */
    public PersonIDRes(boolean isSuccess, String associatedUsername, String personID, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID) {
        super(isSuccess,null);
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }
    public PersonIDRes(boolean isSuccess, String msg) {
        super(isSuccess, msg);
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
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
}
