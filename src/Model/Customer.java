package Model;

public class Customer {

    private int customerId;
    private String personalNumber;
    private String firstName;
    private String lastName;
    private String userName;
    private String userPassword;
    private String muniplicity;

    public Customer() {
    }

    public Customer(int customerId, String personalNumber, String firstName, String lastName, String userName, String userPassword, String muniplicity) {
        this.customerId = customerId;
        this.personalNumber = personalNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.userPassword = userPassword;
        this.muniplicity = muniplicity;
    }


    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getMuniplicity() {
        return muniplicity;
    }

    public void setMuniplicity(String muniplicity) {
        this.muniplicity = muniplicity;
    }

    @Override
    public String toString() {
        return "Välkommen " + firstName + " " + lastName;
    }
}


