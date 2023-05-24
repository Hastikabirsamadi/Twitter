import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {
    private Socket client;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private boolean done;

    public Client() {
        done = false;
    }

    @Override
    public void run() {
        try {
            client = new Socket("192.168.62.72", 9999);
            out = new ObjectOutputStream(client.getOutputStream());
            in = new ObjectInputStream(client.getInputStream());
            InputHandler inputHandler = new InputHandler();
            Thread thread = new Thread(inputHandler);
            thread.start();
            String inMessage;
            while ((inMessage = (String) in.readObject()) != null){
                System.out.println(inMessage);
            }
        }
        catch (IOException | ClassNotFoundException e) {
            //shutdown();
            System.out.println("koon");
        }
    }

    public void shutdown(){
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

    class InputHandler implements Runnable{

        @Override
        public void run() {
            try {
                while (!done) {
                    Scanner scanner = new Scanner(System.in);
                    String message = scanner.nextLine();
                    if (message.equals("quit")) {
                        shutdown();
                    } else {
                        out.writeObject(message);
                    }
                }
            }
            catch (IOException e){
                // shutdown();
                e.printStackTrace();
                System.out.println("kiri");
                System.out.println();
            }
        }
    }
    public static void main(String[] args){
        Client client = new Client();
        client.run();
    }
}
