package Client;

import java.io.*;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientUserManager {
    private static HashMap<String,String> countries = new HashMap<>();
    //ArrayList<User> users = new ArrayList<>();
    public static void showMenu() {
        System.out.println("""
                PLease choose an option :\s
                1.sign up
                2.sign in
                3.Exit""");
    }

    public static void showMainMenu(){
        System.out.println("""
                Please choose an option :\s
                1.Bio
                2.Search
                3.Timeline
                4.Tweet
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
}
