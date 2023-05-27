package Server;

import Model.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {

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

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }
}

