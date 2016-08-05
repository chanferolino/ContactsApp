package main.ferolino.contactsapp.models;

/**
 * Created by Christian on 8/4/2016.
 */
public class Contacts {
    private String firstName;
    private String lastName;
    private String contactNo;

    public Contacts(String firstName, String lastName, String contactNo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNo = contactNo;
    }

    public Contacts() {
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

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }
}
