package Client;
import Model.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.ParseException;
import java.util.Scanner;


public class Client {
    private static Socket client;
    private static ObjectInputStream in;
    private static ObjectOutputStream out;
    private static ClientMessageReceiver clientMessageReceiver;
    private static Thread clientReceiverThread;
    private static Scanner scanner;
    private static User user;
    private static boolean done = false;
    private static boolean flag = false;

    public static void signUp() throws ParseException, IOException, InterruptedException, ClassNotFoundException {
        while (true) {
            try {
                String phone = null, email = null;
                Scanner scanner = new Scanner(System.in);
                System.out.println("please enter your information");
                System.out.print("Username: ");
                String userName = scanner.nextLine();
                System.out.print("firstname: ");
                String name = scanner.nextLine();
                System.out.print("last name: ");
                String lastName = scanner.nextLine();
                System.out.print("email or phone number: ");
                String emailOrNumber = scanner.nextLine();
                System.out.print("password: ");
                String pass = scanner.nextLine();
                System.out.print("repeat your password: ");
                String passRepetition = scanner.nextLine();
                ClientUserManager.showCountries();
                System.out.println();
                System.out.print("please enter the number of your country: ");
                String num = scanner.nextLine();
                String country = ClientUserManager.getCountry(num);
                System.out.print("Birth day: ");
                String day = scanner.nextLine();
                System.out.print("Birth month: ");
                String month = scanner.nextLine();
                System.out.print("Birth year: ");
                String year = scanner.nextLine();
                String birthDate = year + "-" + month + "-" + day;
                if (ClientUserManager.checkEmailFormat(emailOrNumber)) {
                    email = emailOrNumber;
                } else if (ClientUserManager.checkPhoneNumberFormat(emailOrNumber)) {
                    if (ClientUserManager.checkPhoneNumberLength(emailOrNumber))
                        phone = emailOrNumber;
                    else {
                        throw new IllegalArgumentException("invalid format for phone number!");
                    }
                } else {
                    throw new IllegalArgumentException("invalid format for phone number or email!");
                }
                if (!ClientUserManager.checkPasswordLength(pass)) {
                    throw new IllegalArgumentException("password must be at least 8 characters!");
                }
                if (!ClientUserManager.checkPasswordFormat(pass)) {
                    throw new IllegalArgumentException("The format of the password is wrong!");
                }
                if (!pass.equals(passRepetition)) {
                    throw new IllegalArgumentException("invalid pass!");
                }
                user = new User(userName, pass, name, lastName, email, phone, country, birthDate);
                out.writeObject("1");
                Thread.sleep(500);
                out.writeObject(user);
                Thread.sleep(500);
                String temp = (String) in.readObject();
                //System.out.println(temp);
                if (temp.equals("signed up successfully!")) {
                    System.out.println(temp);
                    break;
                }
                System.out.println(temp);
            }
            catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public static void signIn() throws ParseException {
        while (true) {
            System.out.println("Please enter your information to sign in:");
            System.out.print("username: ");
            String userName = scanner.nextLine();
            System.out.print("password: ");
            String pass = scanner.nextLine();
            user = new User(userName, pass);
            try {
                out.writeObject("2");
                out.writeObject(user);
                Thread.sleep(500);
                String temp = (String) in.readObject();
                System.out.println(temp);
                if (temp.equals("You haven't signed up yet!")){
                    break;
                }
                else if (temp.equals("signed in successfully!")){
                    flag = true;
                    break;
               }
            }
            catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void shutdown(){
        done = true;
        try {
            in.close();
            out.close();
            if (!client.isClosed()){
                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            outer:
            while (true) {
                scanner = new Scanner(System.in);
                client = new Socket("192.168.62.72", 9999);
                out = new ObjectOutputStream(client.getOutputStream());
                in = new ObjectInputStream(client.getInputStream());
                clientMessageReceiver = new ClientMessageReceiver(in);
                clientReceiverThread = new Thread(clientMessageReceiver);
                clientReceiverThread.start();
                while (true) {
                    ClientUserManager.showMenu();
                    String choice = scanner.nextLine();
                    if (choice.equals("1")) {
                        signUp();
                        break;
                    }
                    else if (choice.equals("2")){
                        signIn();
                        if (flag){
                            break;
                        }
                    }
                    else if (choice.equals("3")){
                        out.writeObject(choice);
                        System.out.println((String) in.readObject());
                        client.close();
                        System.exit(0);
                    }
                }
                while (true){
                    ClientUserManager.showMainMenu();
                    break outer;
                }
            }
        }
        catch (IOException | ClassNotFoundException | ParseException | InterruptedException e ) {
            shutdown();
        }
    }
}
