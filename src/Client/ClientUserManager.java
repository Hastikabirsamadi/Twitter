package Client;

import Model.PersonalInfo;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientUserManager {
    private static HashMap<String,String> countries = new HashMap<>();
    private static Scanner input = new Scanner(System.in);
    private static boolean flag = false;
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
                1.Profile
                2.Search
                3.Timeline
                4.Tweet
                5.Exit
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

    public static void addInfo(ObjectOutputStream out, ObjectInputStream in) throws IOException, ClassNotFoundException, InterruptedException {
        System.out.println("please complete or edit your personal information:");
        System.out.println("write '-' if you want to leave the field empty");
        System.out.println("write 'exit' in any field to exit");
        System.out.println("Bio (write 'finish' to finish) :");
        StringBuilder bio;
        while (true) {
            bio = new StringBuilder();
            String temp = " ";
            while (!temp.equals("finish")) {
                temp = input.nextLine();
                if (!temp.equals("finish")) {
                    bio.append(temp);
                    bio.append('\n');
                }
            }
            if (bio.length() > 160) {
                System.out.println("You can at last enter 160 characters!!!");
                continue;
            }
            break;
        }
//        ArrayList<Character> bioChars = new ArrayList<>();
//         while (bioChars.size() <= 160) {
//             if (input.next().charAt(0) == '*') {
//                 break;
//             }
//             bioChars = bioChars.add();
//             temp = new String(bioChars);
        if (bio.toString().equals("exit\n")){
            return;
        }
        System.out.println("Location:");
        String location = input.nextLine();
        if (location.equals("exit")){
            return;
        }
        System.out.println("Website:");
        String website = input.nextLine();
        if (website.equals("exit")){
            return;
        }
        PersonalInfo personalInfo = new PersonalInfo(website, location, bio);
        out.writeObject(personalInfo);
        Thread.sleep(300);
        String res = (String) in.readObject();
        System.out.println(res);
    }
}
