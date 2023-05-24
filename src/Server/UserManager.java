package Server;

import Model.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.regex.*;

public class UserManager {

    private static HashMap<String, User> users = new HashMap<>();
    public static String showMenu() {

        return """
                PLease choose an option :\s
                1.sign up
                2.sign in""";
    }
    private static boolean checkUserNameDuplication(String userName) {
        return !users.containsKey(userName);
    }
    private static boolean checkEmailFormat (String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    private static boolean checkEmailDuplication(String email) {
        for(User user : users.values()) {
            if(user.getEmail().equals(email)) {
                return false;
            }
        }
        return true;
    }
    private static boolean checkPhoneNumberLength(PhoneNumber phoneNumber) {

        return phoneNumber.getNumber().length() == 10;
    }
    private static boolean checkPhoneNumberDuplication(PhoneNumber phoneNumber) {
        for(User user : users.values()) {
            if(user.getPhoneNumber().equals(phoneNumber)) {
                return false;
            }
        }
        return true;
    }
    private static boolean checkPasswordLength(String password) {
        return password.length() >= 8;
    }
    private static boolean checkPasswordFormat(String password) {
        return password.matches(".*[A-Z].*") && password.matches(".*[a-z].*");
    }

    public static boolean checkSignUp() {
        return false;
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
