package workshop6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cookie {

    private static List<String> cookies;

    public static void init(String cookieFile) throws IOException{
        cookies = getDataFromTextFile(cookieFile);
    }

    public static String getRandomCookie() throws NoCookieFoundException{
        String randomCookie= "";
        if(cookies.size() > 0){
            Random rand = new Random();
            int randomIndex = rand.nextInt(cookies.size());
            randomCookie = cookies.get(randomIndex);

        }else{
            System.out.println("Cookie: No cookie found");
            throw new NoCookieFoundException("Cookie: No cookie found");
        }
        return randomCookie;
    }

    public static List<String> getDataFromTextFile(String _cookieFile) 
            throws IOException {
        List<String> cookies = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(_cookieFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                cookies.add(line);
            }
        }
        System.out.println("Cookie: cookies=" + cookies);
        return cookies;

    }
    
}
