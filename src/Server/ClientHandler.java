package Server;
import Model.PersonalInfo;
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
                        ServerUserManager.writeFile(ServerUserManager.getUsers());
                        exit();
                        break outer;
                    }
                    if (userChoice.equals("1")) {
                        System.out.println("user is signing up...");
                        User tempUser = (User) in.readObject();
                        if (ServerUserManager.checkSignUp(tempUser, out)) {
                            ServerUserManager.signUp(tempUser);
                            user = tempUser;
                            System.out.println("user " + user.getUsername() + " signed up successfully :)");
                            out.writeObject("signed up successfully!");
                            break;
                        }
                    }
                    else if(userChoice.equals("2")) {
                        System.out.println("user is signing in...");
                        User tempUser = (User) in.readObject();
                        if(ServerUserManager.checkSignIn(tempUser, out)) {
                            ServerUserManager.signIn(tempUser);
                            user = ServerUserManager.getUsers().get(tempUser.getUsername());
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
                    System.out.println("getting user's choice after sign in and sign up :)");
                    userChoice = (String) in.readObject();
                    if(userChoice.equals("1")) {
                        System.out.println("user is adding bio...");
                        out.writeObject(user.getPersonalInfo());
                        System.out.println("user's personal info is sent :)");
                        String editAnswer = (String) in.readObject();
                        if(editAnswer.equals("1")) {
                            String info = (String) in.readObject();
                            System.out.println(info);
                           // user.setPersonalInfo(info);
                            System.out.println("user '" + user.getUsername() + "' changed their personal info");
                            out.writeObject("Personal info edited successfully!");
                        }
                        continue;
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
