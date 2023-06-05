package Client;
import Model.*;
import Server.ServerManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.ParseException;
import java.util.Scanner;


public class Client {
    private static Socket client;
    private static ObjectInputStream in;
    private static ObjectOutputStream out;
    private static ClientMessageReceiver clientMessageReceiver;
    private static Thread clientReceiverThread;
    private static Scanner scanner;
    private static User user;
    private static boolean done = false;
    private static boolean isSignIn = false;
    private static boolean isSignUP = false;

    public static void signUp() throws ParseException, IOException, InterruptedException, ClassNotFoundException {
        while (true) {
            try {
                String phone = null, email = null;
                Scanner scanner = new Scanner(System.in);
                System.out.println("please enter your information");
                System.out.println("write 'exit' in any field to exit");
                System.out.print("Username: ");
                String userName = scanner.nextLine();
                if (userName.equals("exit")){
                    return;
                }
                System.out.print("firstname: ");
                String name = scanner.nextLine();
                if (name.equals("exit")){
                    return;
                }
                System.out.print("last name: ");
                String lastName = scanner.nextLine();
                if (lastName.equals("exit")){
                    return;
                }
                System.out.print("email or phone number: ");
                String emailOrNumber = scanner.nextLine();
                if (emailOrNumber.equals("exit")){
                    return;
                }
                System.out.print("password: ");
                String pass = scanner.nextLine();
                if (pass.equals("exit")){
                    return;
                }
                System.out.print("repeat your password: ");
                String passRepetition = scanner.nextLine();
                if (passRepetition.equals("exit")){
                    return;
                }
                ClientManager.showCountries();
                System.out.println();
                System.out.print("please enter the number of your country: ");
                String num = scanner.nextLine();
                if (num.equals("exit")){
                    return;
                }
                String country = ClientManager.getCountry(num);
                System.out.print("Birth day: ");
                String day = scanner.nextLine();
                if (day.equals("exit")){
                    return;
                }
                System.out.print("Birth month: ");
                String month = scanner.nextLine();
                if (month.equals("exit")){
                    return;
                }
                System.out.print("Birth year: ");
                String year = scanner.nextLine();
                if (year.equals("exit")){
                    return;
                }
                String birthDate = year + "-" + month + "-" + day;
                if (ClientManager.checkEmailFormat(emailOrNumber)) {
                    email = emailOrNumber;
                } else if (ClientManager.checkPhoneNumberFormat(emailOrNumber)) {
                    if (ClientManager.checkPhoneNumberLength(emailOrNumber))
                        phone = emailOrNumber;
                    else {
                        throw new IllegalArgumentException("invalid format for phone number!");
                    }
                } else {
                    throw new IllegalArgumentException("invalid format for phone number or email!");
                }
                if (!ClientManager.checkPasswordLength(pass)) {
                    throw new IllegalArgumentException("password must be at least 8 characters!");
                }
                if (!ClientManager.checkPasswordFormat(pass)) {
                    throw new IllegalArgumentException("The format of the password is wrong!");
                }
                if (!pass.equals(passRepetition)) {
                    throw new IllegalArgumentException("invalid pass!");
                }
                user = new User(userName, pass, name, lastName, email, phone, country, birthDate);
                out.writeObject("1");
                Thread.sleep(500);
                out.writeObject(user);
                Thread.sleep(500);
                String temp = (String) in.readObject();
                if (temp.equals("signed up successfully!")) {
                    System.out.println(temp);
                    isSignUP = true;
                    break;
                }
                System.out.println(temp);
            }
            catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public static void signIn() throws ParseException {
        while (true) {
            System.out.println("Please enter your information to sign in:");
            System.out.println("write 'exit' in any field to exit");
            System.out.print("username: ");
            String userName = scanner.nextLine();
            if (userName.equals("exit")){
                return;
            }
            System.out.print("password: ");
            String pass = scanner.nextLine();
            if (pass.equals("exit")){
                return;
            }
            user = new User(userName, pass);
            try {
                out.writeObject("2");
                out.writeObject(user);
                Thread.sleep(500);
                String temp = (String) in.readObject();
                System.out.println(temp);
                if (temp.equals("You haven't signed up yet!")){
                    break;
                }
                else if (temp.equals("signed in successfully!")){
                    isSignIn = true;
                    break;
               }
            }
            catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void shutdown(){
        done = true;
        try {
            in.close();
            out.close();
            if (!client.isClosed()){
                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            outer:
            while (true) {
                scanner = new Scanner(System.in);
                client = new Socket("localhost", 9999);
                out = new ObjectOutputStream(client.getOutputStream());
                in = new ObjectInputStream(client.getInputStream());
                clientMessageReceiver = new ClientMessageReceiver(in);
                clientReceiverThread = new Thread(clientMessageReceiver);
                clientReceiverThread.start();
                while (true) {
                    ClientManager.showMenu();
                    String choice = scanner.nextLine();
                    //User chose to sign up
                    if (choice.equals("1")) {
                        signUp();
                        if (isSignUP) {
                            break;
                        }
                    }
                    //User chose to sign in
                    else if (choice.equals("2")){
                        signIn();
                        if (isSignIn){
                            break;
                        }
                    }
                    //user chose to exit from the primary menu
                    else if (choice.equals("3")){
                        out.writeObject(choice);
                        System.out.println((String) in.readObject());
                        client.close();
                        System.exit(0);
                    }
                }
                while (true){
                    ClientManager.showMainMenu();
                    String choice2 = scanner.nextLine();
                    out.writeObject(choice2);
                    //user chose to show the profile
                    if (choice2.equals("1")){
                        System.out.println((in.readObject()).toString());
                        int followerSize = (int)in.readObject();
                        int followingSize = (int) in.readObject();
                        System.out.println("Followers : " + followerSize + "   " + "Followings : " + followingSize);
                        ClientManager.showProfileMenu();
                        String ans = scanner.nextLine();
                        out.writeObject(ans);
                        //user chose to edit personal info
                        if (ans.equals("1")) {
                            ClientManager.addInfo(out, in);
                        }
                        //user chose to see his/hers tweets
                        else if (ans.equals("2")){
                            ClientManager.showTweet(in);
                        }
                    }
                    //user chose to search for another user
                    else if (choice2.equals("2")) {
                        User temp = ClientManager.searchUser(out, in);
                        if (temp != null) {
                            System.out.println(temp.getUsername() + "  followers : " + temp.getFollowers().size() + "  followings : " + temp.getFollowings().size());
                          //  ClientManager.showProfile(temp);
                            //Show the options after you search for users
                            ClientManager.showSearchMenu();
                            //get the option from the search menu
                            String ans = scanner.nextLine();
                            //give that option to the server
                            out.writeObject(ans);
                            //user chose to follow one of the users in the list of the given users from the server
                            if (ans.equals("1")) {
                                //temp is the user who is followed
                                ClientManager.followOrUnfollow(temp, out, in);
                            } else if (ans.equals("2")) {
                                //temp is the user who is Unfollowed
                                ClientManager.followOrUnfollow(temp, out, in);
                            }
                        }
                    }
                    //user chose to add tweet
                    else if (choice2.equals("4")){
                        ClientManager.addTweet(out,in);
                    }
                    //user chose to exit from the main menu
                    else if (choice2.equals("5")){
                        break;
                    }
                }
            }
        }
        catch (IOException | ClassNotFoundException | ParseException | InterruptedException e ) {
            shutdown();
        }
    }
}
