package network;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class RequestThread extends Thread {

    private String uri;
    private VkConnection vkConn;

    public RequestThread(VkConnection vkConn, String uri) {
        this.uri = uri;
        this.vkConn = vkConn;
    }

    @Override
    public void run() {
        String response = null;
        try {
            URL url = new URL(uri);
            HttpsURLConnection client = (HttpsURLConnection)url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            response = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        vkConn.finishRequest(response);
    }

}
