package Server;

import Model.*;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.*;

public class ServerUserManager {

    private static HashMap<String, User> users = new HashMap<>();
    Scanner scanner = new Scanner(System.in);

    public static void readFile() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("note.bin"))) {
            users = (HashMap<String, User>) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException | EOFException e) {
            //   System.out.println("No notes found!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void writeFile(HashMap<String, User> users) {
       try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("note.bin"))) {
           objectOutputStream.writeObject(users);
       }
       catch(FileNotFoundException e ) {
           System.out.println("no users!");
       }
       catch(IOException e) {
           e.printStackTrace();
       }
    }
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
        for(User user : users.values()) {
            if(user.getPhoneNumber().equals(phoneNumber)) {
                return false;
            }
        }
        return true;
    }
    public static boolean checkSignUp(User user, ObjectOutputStream out) {
        try {
            if (!checkUserNameDuplication(user.getUsername())) {
                out.writeObject("This username is already taken!");
                return false;
            }
            if (user.getEmail() != null && !checkEmailDuplication(user.getEmail())) {
                out.writeObject("This email is already taken!");
                return false;
            }
            if (user.getPhoneNumber() != null && !checkPhoneNumberDuplication(user.getPhoneNumber())) {
                out.writeObject("This phone number is already taken!");
                return false;
            }
            return true;
        } catch(IOException e) {
            e.printStackTrace();
            return false;
            //TODO: HANDLE LATER
        }
    }
    public static void signUp(User user) throws IOException {
        users.put(user.getUsername(), user);
    }

    public static HashMap<String, User> getUsers() {
        return users;
    }

    public static void setUsers(HashMap<String, User> users) {
        ServerUserManager.users = users;
    }
}
