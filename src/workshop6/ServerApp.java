package workshop6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static workshop6.Cookie.getRandomCookie;
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
        ExecutorService executor = Executors.newFixedThreadPool(5);

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

class ClientHandler implements Runnable {
    private final Socket client;

    public ClientHandler(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try (InputStream is = client.getInputStream();
             DataInputStream dis = new DataInputStream(is);
             OutputStream os = client.getOutputStream();
             DataOutputStream dos = new DataOutputStream(os)) {

            while (true) {
                String commandMessage = dis.readUTF();
                System.out.println("ClientHandler: message=" + commandMessage);
                if (commandMessage.equals("exit")) {
                    System.exit(1);
                } else if (commandMessage.equals("get-cookie")) {
                    String randomCookie = "";
                    try {
                        randomCookie = getRandomCookie();
                    } catch (NoCookieFoundException e) {
                        System.err.println("ClientHandler: NoCookieFoundException: " + e.getMessage());
                    }
                    dos.writeUTF("cookie-text_" + randomCookie);
                } else {
                    dos.writeUTF("Invalid command");
                }
            }
        } catch (IOException e) {
            System.err.println("ClientHandler: IOException: " + e.getMessage());
        }
    }
}