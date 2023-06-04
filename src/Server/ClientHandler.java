package Server;
import Model.PersonalInfo;
import Model.Tweet;
import Model.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private User user;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            in = new ObjectInputStream(clientSocket.getInputStream());
            out = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch(IOException e) {
            //TODO: handle later
        }
    }

    protected void exit() {
        try {
            out.writeObject("bye :(");
            out.flush();
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void run() {

        String userChoice;
        outer : while (true) {
            while (true) {
                try {
                    System.out.println("getting user's choice");
                    userChoice = (String) in.readObject();
                    if (userChoice.equals("3")) {
                        ServerManager.writeFile(ServerManager.getUsers());
                        exit();
                        break outer;
                    }
                    if (userChoice.equals("1")) {
                        System.out.println("user is signing up...");
                        User tempUser = (User) in.readObject();
                        if (ServerManager.checkSignUp(tempUser, out)) {
                            ServerManager.signUp(tempUser);
                            user = tempUser;
                            System.out.println("user " + user.getUsername() + " signed up successfully :)");
                            out.writeObject("signed up successfully!");
                            break;
                        }
                    }
                    else if(userChoice.equals("2")) {
                        System.out.println("user is signing in...");
                        User tempUser = (User) in.readObject();
                        if(ServerManager.checkSignIn(tempUser, out)) {
                            ServerManager.signIn(tempUser);
                            user = ServerManager.getUsers().get(tempUser.getUsername());
                            System.out.println("user '" + user.getUsername() + "' signed in successfully :)");
                            out.writeObject("signed in successfully!");
                            break;
                        }
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                    try {
                        out.writeObject(e.getMessage());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } catch (IOException e) {
                    //TODO: handle later
                   // e.printStackTrace();
                    break outer;
                } catch (ClassNotFoundException e) {
                    //TODO: handle later
                    e.printStackTrace();
                    //throw new RuntimeException(e);
                }
            }
            while (true) {
                try {
                    System.out.println("getting user's choice after sign in or sign up :)");
                    userChoice = (String) in.readObject();
                    if(userChoice.equals("1")) {
                        System.out.println("user is editing personal info...");
                        out.writeObject(user.getPersonalInfo());
                        System.out.println("user's personal info is sent :)");
                        String answer = (String) in.readObject();
                        if(answer.equals("1")) {
                            String ifExit = (String) in.readObject();
                            if(ifExit.equals("ok")) {
                                PersonalInfo info = (PersonalInfo) in.readObject();
                                user.setPersonalInfo(info);
                                System.out.println("user '" + user.getUsername() + "' changed their personal info");
                                out.writeObject("Personal info edited successfully!");
                            }
                        }
                        else if(answer.equals("2")) {
                            System.out.println("showing user's tweets...");
                            out.writeObject(user.getTweets());
                        }
                    }
                    else if(userChoice.equals("2")) {
                        System.out.println("user is searching...");
                        String word = (String) in.readObject(); // receiving word to search
                        if(!word.equals("exit")) {
                            out.writeObject(ServerManager.searchUser(word)); //sending an arraylist of found users for client
                            String searchChoice = (String) in.readObject();
                            if(searchChoice.equals("1")) {
                                User temp = (User) in.readObject();
                                if(user.checkFollow(temp, out)) {
                                    user.follow(temp);
                                    out.writeObject(temp.getUsername() + "is followed successfully");
                                    System.out.println(temp.getUsername() + "is followed successfully :)");
                                }
                            }
                        }
                    }
                    else if(userChoice.equals("4")) {
                        System.out.println("user is adding a tweet...");
                        String ifExit = (String) in.readObject();
                        if(ifExit.equals("ok")) {
                            Tweet tweet = (Tweet) in.readObject();
                            user.tweet(tweet);
                            System.out.println("user '" + user.getUsername() + "' added tweet successfully");
                            out.writeObject("tweet added successfully!");
                        }
                    }
                    else if(userChoice.equals("5")) {
                        break;
                    }
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
