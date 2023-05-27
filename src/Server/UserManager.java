package Server;

import Model.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.regex.*;

public class UserManager {
    private static ObjectInputStream in;
    private static HashMap<String, User> users = new HashMap<>();
    private static boolean checkUserNameDuplication(String userName) {
        return !users.containsKey(userName);
    }
    private static boolean checkEmailDuplication(String email) {
        for(User user : users.values()) {
            if(user.getEmail().equals(email)) {
                return false;
            }
        }
        return true;
    }
    private static boolean checkPhoneNumberDuplication(String phoneNumber) {
        for (User user : users.values()) {
            if (user.getPhoneNumber().equals(phoneNumber)) {
                return false;
            }
        }
        return true;
    }
    public static boolean checkSignUp(User user, ObjectInputStream in, ObjectOutputStream out) throws IOException {
        if(checkUserNameDuplication(user.getUsername())) {
            if(checkEmailDuplication(user.getEmail())) {
                if(checkPhoneNumberDuplication(user.getPhoneNumber())) {
                    return true;
                }
                else {
                    System.out.println("This phone number is already taken!");
                    return false;
                }
            }
            else {
                System.out.println("This email is already taken!");
                return false;
            }
        }
        else {
            out.writeObject("This username is already taken!");
            return false;
        }
    }
    public static void signUp(User user) throws IOException {
        users.put(user.getUsername(), user);
    }

    public static HashMap<String, User> getUsers() {
        return users;
    }

    public static void setUsers(HashMap<String, User> users) {
        UserManager.users = users;
    }
}
