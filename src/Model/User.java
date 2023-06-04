package Model;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
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
    private PersonalInfo personalInfo;
    private ArrayList<Tweet> tweets = new ArrayList<>();
    private ArrayList<User> followers = new ArrayList<>();
    private ArrayList<User> followings = new ArrayList<>();

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
        this.personalInfo = new PersonalInfo("-", "-", new StringBuilder("-"));
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public void setPersonalInfo(PersonalInfo personalInfo) {
        this.personalInfo =  personalInfo;
        this.personalInfo.setUser(this);
    }
    public void tweet(Tweet tweet) {
        this.tweets.add(tweet);
    }

    public boolean checkFollow(User user, ObjectOutputStream out) {
            for (User followedUser : followings) {
                if (followedUser.equals(user)) {
                    try {
                        out.writeObject("You have already followed this user!");
                        return false;
                    }
                    catch (IOException e) {
                        System.out.println("IO Exception in check follow method");
                        return false;
                    }
                }
            }
         try {
             out.writeObject("success");
         } catch (IOException e) {
             System.out.println("IO Exception in check follow method");
             return false;
         }
        return true;
    }
    public void follow(User user) {
        //this follows user
        this.followings.add(user);
        user.followers.add(this);
    }

    public ArrayList<User> getFollowers() {
        return followers;
    }

    public ArrayList<User> getFollowings() {
        return followings;
    }

    public ArrayList<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(ArrayList<Tweet> tweets) {
        this.tweets = tweets;
    }

    public PersonalInfo getPersonalInfo() {
        return personalInfo;
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

//    @Override
//    public String toString() {
//        return "User{" +
//                "username='" + username + '\'' +
//                ", firstName='" + firstName + '\'' +
//                ", lastName='" + lastName + '\'' +
//                '}';
//    }

    public String showSearchUser(){
        return this.firstName+" "+this.lastName+"\n"+this.username;
    }
}
