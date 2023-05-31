package Server;
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
                    }
                    if (userChoice.equals("1")) {
                        System.out.println("user is signing up...");
                        user = (User) in.readObject();
                        System.out.println(user.toString());
                        if (ServerUserManager.checkSignUp(user, out)) {
                            ServerUserManager.signUp(user);
                            System.out.println("user " + user.getUsername() + " signed up successfully :)");
                            out.writeObject("signed up successfully!");
                            break;
                        }
                    }
                    else if(userChoice.equals("2")) {
                        System.out.println("user is signing in...");

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
        }
    }
}
