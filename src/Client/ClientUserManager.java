package Client;

import Model.PhoneNumber;
import Model.User;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientUserManager {
    private static HashMap<String,String> countries = new HashMap<>();
    //ArrayList<User> users = new ArrayList<>();
    public static String showMenu() {

        return """
                PLease choose an option :\s
                1.sign up
                2.sign in
                3.Exit""";
    }

    public static void signUp(ObjectOutputStream out) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("please enter your information");
        System.out.print("Username: ");
        String userName = scanner.nextLine();
        System.out.println();
        System.out.print("firstname: ");
        String name = scanner.nextLine();
        System.out.println();
        System.out.print("last name: ");
        String lastName = scanner.nextLine();
        System.out.println();
        System.out.println("Enter your email or phone number:");
        String emailOrNumber = scanner.nextLine();
        System.out.println();
        System.out.print("password: ");
        String pass = scanner.nextLine();
        System.out.println();
        System.out.println("repeat your password: ");
        String passRepetition = scanner.nextLine();
        System.out.println();
        showCountries();
        System.out.println("please enter the number of your country:");
        String num = scanner.nextLine();
        getCountry(num);
        if (!checkEmailFormat(emailOrNumber) && !checkPhoneNumberLength(emailOrNumber)){
            throw new IllegalArgumentException("invalid format for email or phone number!");
        }
        if (!checkPasswordLength(pass)){
            throw new IllegalArgumentException("password must be at least 8 characters!");
        }
        if (!checkPasswordFormat(pass)){
            throw new IllegalArgumentException("The format of the password is wrong!");
        }
    }

    public static boolean checkEmailFormat (String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private static boolean checkPhoneNumberLength(String phoneNumber) {
        return phoneNumber.length() == 11;
    }

//    private static boolean checkPhoneNumberFormat(String phoneNumber) {
//        return phoneNumber.startsWith("09");
//    }

    private static boolean checkPasswordLength(String password) {
        return password.length() >= 8;
    }
    private static boolean checkPasswordFormat(String password) {
        return password.matches(".*[A-Z].*") && password.matches(".*[a-z].*");
    }
    private static void showCountries() {
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
    private static String getCountry(String num){
        for (String temp : countries.keySet()) {
            if (num.equals(temp)){
                return countries.get(temp);
            }
        }
        return "try again";
    }
}
