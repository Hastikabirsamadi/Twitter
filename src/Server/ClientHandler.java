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

    @Override
    public void run() {
        try {
            String userChoice;
            while (true) {
                userChoice = (String) in.readObject();
                switch (userChoice) {
                    case "1" -> {
                        System.out.println("user is signing up!");
                        String userName = (String) in.readObject();
                        User newUser = (User) in.readObject();
                        if(UserManager.checkSignUp(user, in, out)) {
                            UserManager.signUp(newUser);
                            System.out.println("user " + user.getUsername() + " signed upn successfully :)");
                            out.writeObject(user.getUsername() + " signed up successfully :)");

                        }
                    }
                    case "2" -> {
                        break;
                    }
                }
            }
        } catch (IOException e) {
            //TODO: handle later
        } catch (ClassNotFoundException e) {
            //TODO: handle later
            //throw new RuntimeException(e);
        }
    }
}
