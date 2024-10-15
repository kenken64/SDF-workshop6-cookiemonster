package workshop6.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import workshop6.NoCookieFoundException;

import static workshop6.Cookie.getRandomCookie;

public class ClientHandler implements Runnable {
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
