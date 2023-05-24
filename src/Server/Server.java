package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable{

    private ServerSocket serverSocket;
    private ArrayList<ClientHandler> clientHandlers;
    private boolean done;
    private ExecutorService threadPool;

    public Server() {
        this.clientHandlers = new ArrayList<>();
        done = false;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(9999);
            threadPool = Executors.newCachedThreadPool();
            while (!done) {
                Socket client = serverSocket.accept();
                ClientHandler handler = new ClientHandler(client);
                clientHandlers.add(handler);
                threadPool.execute(handler);
            }

        } catch (IOException e) {
            //TODO: shutdown
        }
    }

    class ClientHandler implements Runnable {

        private Socket client;
        private ObjectInputStream in;
        private ObjectOutputStream out;

        public ClientHandler(Socket client) {
            this.client = client;
        }
        @Override
        public void run() {
            try {
                in = new ObjectInputStream(client.getInputStream());
                out = new ObjectOutputStream(client.getOutputStream());
                out.writeObject(UserManager.showMenu());
                String choice;
                while (true) {
                    choice = (String)in.readObject();
                    switch (choice) {
                        case "1" -> {

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
}
