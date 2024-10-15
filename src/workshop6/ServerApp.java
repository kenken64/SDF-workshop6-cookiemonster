package workshop6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static workshop6.Cookie.getRandomCookie;
import static workshop6.Cookie.init;

public class ServerApp {
    public static final String ARG_MESSAGE="usage: java workshop6.ServerApp <port> <cookie file>";
    public static void main(String[] args) throws NumberFormatException, IOException {
        System.out.println("ServerApp");
        if(args.length < 2) {
            System.out.println(ARG_MESSAGE);
            return;
        }

        String serverPort = args[0];
        String cookieFile = args[1];
        System.out.println("ServerApp: serverPort=" 
                    + serverPort + ", cookieFile=" + cookieFile);
        
        try (ServerSocket server = 
                new ServerSocket(Integer.parseInt(serverPort))){
            init(cookieFile);
            while(true){
                try(Socket client = server.accept()){
                    System.out.println("ServerApp: client connected");
                    InputStream is = client.getInputStream();
                    DataInputStream dis = new DataInputStream(is);
                    OutputStream os = client.getOutputStream();
                    DataOutputStream dos = new DataOutputStream(os);
                    while(true){
                        String commandMessage = dis.readUTF();
                        System.out.println("ServerApp: message=" + commandMessage);
                        if(commandMessage.equals("exit")){
                            break;
                        }else if(commandMessage.equals("get-cookie")){
                            // call the cookie object and randomize
                            String randomCookie = getRandomCookie();
                            dos.writeUTF("cookie-text_"+ randomCookie);
                        }else{
                            dos.writeUTF("Invalid command");
                        }    
                    }
                }
            }
            
        }
    }
}
