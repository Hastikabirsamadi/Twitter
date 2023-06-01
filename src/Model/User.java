package Model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class User implements Serializable {

    //ya email ya phoneNumber bayad bashe
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String country;
    private Date birthDate;
    private LocalDate signupDate;
    private Date lastModificationDate;
    private boolean SignedUp;
    private boolean SignedIn;
    private PersonalInfo bio;

    public User(String username, String password, String firstName, String lastName, String email, String phoneNumber,
                String country, String birthDate) throws ParseException {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.country = country;
        this.birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(birthDate);
        this.signupDate = LocalDate.now();
        this.SignedUp = false;
        this.SignedIn = false;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setBio(StringBuilder webSite, String location, StringBuilder details) {
        this.bio = new PersonalInfo(webSite, location, details);
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getSignupDate() {
        return signupDate;
    }

    public void setSignupDate(LocalDate signupDate) {
        this.signupDate = signupDate;
    }

    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(Date lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    public boolean isSignedUp() {
        return SignedUp;
    }

    public void setSignedUp(boolean signedUp) {
        SignedUp = signedUp;
    }

    public boolean isSignedIn() {
        return SignedIn;
    }

    public void setSignedIn(boolean signedIn) {
        SignedIn = signedIn;
    }
}
