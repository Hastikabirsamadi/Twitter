package Client;

import Model.PersonalInfo;
import Model.Tweet;
import Model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientManager {
    private static HashMap<String,String> countries = new HashMap<>();
    private static Scanner input = new Scanner(System.in);
    private static boolean flag = false;
    public static void showMenu() {
        System.out.println("""
                PLease choose an option :\s
                1.sign up
                2.sign in
                3.Exit""");
    }

    public static void showMainMenu(){
        System.out.print("""
                Please choose an option :\s
                1.Profile
                2.Search
                3.Timeline
                4.Tweet
                5.Exit
                """);
    }

    public static void showSearchMenu(){
        System.out.print("""
                Please choose an option :\s
                1.Follow
                2.Unfollow
                3.Block
                4.Unblock
                5.Exit
                """);
    }

    public static void showProfileMenu(){
        System.out.print("""
                Please choose an option :\s
                1.Edit personal info
                2.Show tweets
                3.Exit
                """);
    }


    public static boolean checkEmailFormat (String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean checkPhoneNumberLength(String phoneNumber) {
        return phoneNumber.length() == 11;
    }

    public static boolean checkPhoneNumberFormat(String phoneNumber) {
        return phoneNumber.startsWith("09");
    }

    public static boolean checkPasswordLength(String password) {
        return password.length() >= 8;
    }
    public static boolean checkPasswordFormat(String password) {
        return password.matches(".*[A-Z].*") && password.matches(".*[a-z].*");
    }
    public static void showCountries() {
        try(BufferedReader br = new BufferedReader(new FileReader("country.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(" ");
                countries.put(split[0],split[1]);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        for (String temp :countries.keySet()) {
            System.out.print( temp +" "+ countries.get(temp) + "\n" );
        }
    }
    public static String getCountry(String num){
        for (String temp : countries.keySet()) {
            if (num.equals(temp)){
                return countries.get(temp);
            }
        }
        return "try again";
    }

    public static void addInfo(ObjectOutputStream out, ObjectInputStream in) throws IOException, ClassNotFoundException, InterruptedException {
        System.out.println("please complete or edit your personal information:");
        System.out.println("write '-' if you want to leave the field empty");
        System.out.println("write 'exit' in any field to exit");
        System.out.println("Bio (write 'finish' to finish) :");
        StringBuilder bio;
        String location = "-";
        String website = "-";
        while (true) {
            bio = new StringBuilder();
            String temp = " ";
            while (!temp.equals("finish")) {
                temp = input.nextLine();
                if (!temp.equals("finish")) {
                    bio.append(temp);
                    bio.append('\n');
                }
            }
            if (bio.length() > 160) {
                System.out.println("You can at last enter 160 characters!!!");
                continue;
            }
            break;
        }
        if (bio.toString().equals("exit\n")){
            out.writeObject("exit");
            return;
        }
        System.out.println("Location:");
        location = input.nextLine();
        if (location.equals("exit")){
            out.writeObject("exit");
            return;
        }
        System.out.println("Website:");
        website = input.nextLine();
        if (website.equals("exit")){
            out.writeObject("exit");
            return;
        }
        out.writeObject("ok");
        PersonalInfo personalInfo = new PersonalInfo(website, location, bio);
        out.writeObject(personalInfo);
        Thread.sleep(300);
        String res = (String) in.readObject();
        System.out.println(res);
    }

    public static void addTweet(ObjectOutputStream out, ObjectInputStream in) throws IOException, ClassNotFoundException, InterruptedException {
        System.out.println("Please enter your tweet:");
        System.out.println("write 'exit' to exit");
        System.out.println("write 'finish' to finish the tweet:");
        StringBuilder body;
        while (true) {
            body = new StringBuilder();
            String temp = " ";
            while (!temp.equals("finish")) {
                temp = input.nextLine();
                if (!temp.equals("finish")) {
                    body.append(temp);
                    body.append('\n');
                }
            }
            if (body.length() > 280) {
                System.out.println("Your tweet has more than 280 characters!!!");
                System.out.println("Try again!");
                continue;
            }
            break;
        }
        if (body.toString().equals("exit\n")){
            out.writeObject("exit");
            return;
        }
        out.writeObject("ok");
        Tweet tweet = new Tweet(body, 0,0,0);
        out.writeObject(tweet);
        Thread.sleep(300);
        String res = (String) in.readObject();
        System.out.println(res);
    }

    public static void showTweet(ObjectInputStream in) throws IOException, ClassNotFoundException {
        ArrayList<Tweet> tweets = new ArrayList<>();
        tweets = (ArrayList<Tweet>) in.readObject();
        if (tweets.size() == 0){
            System.out.println("No tweets!");
            return;
        }
        for (Tweet tweet : tweets){
            System.out.println(tweet.toString());
        }
    }

    public static User searchUser(ObjectOutputStream out, ObjectInputStream in) throws IOException, ClassNotFoundException {
        User temp;
        System.out.println("Enter an username or firstname or lastname to find it:");
        System.out.println("write 'exit' in the field to exit:");
        String word = input.nextLine();
        out.writeObject(word);
        if (!word.equals("exit")) {
            ArrayList<User> foundUsers;
            HashMap<String, User> foundUsers2 = new HashMap<>();
            foundUsers = (ArrayList<User>) in.readObject();
            for (int i = 0; i < foundUsers.size(); i++) {
                foundUsers2.put(String.valueOf(i + 1), foundUsers.get(i));
            }
            if (foundUsers.size() == 0) {
                System.out.println("Not Found!");
            }
            else {
                int counter = 1;
                for (User user : foundUsers) {
                    System.out.println(counter + "- " + user.showSearchUser());
                    counter++;
                }
                System.out.println("Please choose a user:");
                System.out.println("write 'exit' in the field to exit:");
                String choice = input.nextLine();
                temp = foundUsers2.get(choice);
                out.writeObject(temp);
                User temp2;
                temp2 = (User) in.readObject();
                if (!choice.equals("exit")) {
                    System.out.println(temp2.getFirstName() + " " + temp2.getLastName() + "\n" +
                            temp2.getUsername()+ "\n" + temp2.getPersonalInfo().toString() + "\n" +
                            "Followers : "+ temp2.getFollowers().size() + "   " + "Following : " + temp2.getFollowings().size());
                    if (temp2.getTweets().size() == 0){
                        System.out.println("No tweets!");
                    }
                    else {
                        for (Tweet tweet : temp2.getTweets()) {
                            System.out.println(tweet.toString());
                        }
                    }
                    return temp2;
                }
            }
        }
        return null;
    }
    public static void followOrUnfollow(User user, ObjectOutputStream out, ObjectInputStream in){
        try {
            //user is the user who you want to follow
            out.writeObject(user);
            //res is the answer sent from server that tells you if you can follow user or not
            String res = in.readObject().toString();
            if(res.equals("success")) {
                System.out.println(in.readObject());
            }
            else {
                System.out.println(res);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //For showing User's profile when you search and choose them
    public static void showProfile(User user){
//        System.out.println(user.getFirstName() + " " + user.getLastName() + "\n" +
//                user.getUsername()+ "\n" + user.getPersonalInfo().toString() + "\n" +
//                "Followers : "+ user.getFollowers().size() + "   " + "Following : " + user.getFollowings().size());
//        if (user.getTweets().size() == 0){
//            System.out.println("No tweets!");
//            return;
//        }
//        for(Tweet tweet : user.getTweets()) {
//            System.out.println(tweet.toString());
//        }
    }
}
