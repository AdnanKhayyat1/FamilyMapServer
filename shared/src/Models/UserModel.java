package Models;

import java.util.Objects;

/**
 * User database model
 */
public class UserModel {
    private String userName;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String gender;
    private String personID;

    /**
     * Constructor for User model
     * @param userInput : username string
     * @param emailInput : email string
     * @param passInput : password string
     * @param firstNameInput : first name string
     * @param lastNmeInput : last name string
     * @param genderInput : gender string
     * @param IDinput : id string (unique)
     */
    public UserModel(String userInput, String emailInput, String passInput, String firstNameInput, String lastNmeInput, String genderInput, String IDinput){
        userName = userInput;
        email = emailInput;
        password = passInput;
        firstName = firstNameInput;
        lastName = lastNmeInput;
        gender = genderInput;
        personID = IDinput;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserModel userModel = (UserModel) o;
        return Objects.equals(userName, userModel.userName) &&
                Objects.equals(email, userModel.email) &&
                Objects.equals(password, userModel.password) &&
                Objects.equals(firstName, userModel.firstName) &&
                Objects.equals(lastName, userModel.lastName) &&
                Objects.equals(gender, userModel.gender) &&
                Objects.equals(personID, userModel.personID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, email, password, firstName, lastName, gender, personID);
    }
}
