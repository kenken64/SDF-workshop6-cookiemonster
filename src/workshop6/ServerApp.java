package workshop6;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import workshop6.util.ClientHandler;

import static workshop6.Cookie.init;

public class ServerApp {
    public static final String ARG_MESSAGE = "usage: java workshop6.ServerApp <port> <cookie file>";

    public static void main(String[] args) throws NumberFormatException, IOException {
        System.out.println("ServerApp");
        if (args.length < 2) {
            System.out.println(ARG_MESSAGE);
            return;
        }

        String serverPort = args[0];
        String cookieFile = args[1];
        System.out.println("ServerApp: serverPort=" + serverPort + ", cookieFile=" + cookieFile);
        ExecutorService executor = Executors.newFixedThreadPool(6);

        try (ServerSocket server = new ServerSocket(Integer.parseInt(serverPort))) {
            init(cookieFile);
            while (true) {
                Socket client = server.accept();
                ClientHandler clientHandler = new ClientHandler(client);
                executor.submit(clientHandler);
            }
        }finally{
            executor.shutdown();
        }
    }
}

