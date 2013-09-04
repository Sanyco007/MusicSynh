package network;

import javafx.application.Platform;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Map;
import java.util.Set;

public class VkConnection {

    private String accessToken;
    private String request = "https://api.vk.com/method/%s?%s&access_token=%s";
    private IVkResponse callback;

    public VkConnection(String token) {
        accessToken = token;
        callback = null;
    }

    public void setOnVkResponse(IVkResponse response) {
        callback = response;
    }

    public void sendApiRequest(String method, Map parameters) {
        Set<Map.Entry> set = parameters.entrySet();
        StringBuilder parametersBuilder = new StringBuilder();
        boolean first = true;
        for (Map.Entry item : set) {
            if (!first) {
                parametersBuilder.append("&");
            }
            if (first) {
                first = false;
            }
            parametersBuilder.append((String)item.getKey())
                    .append("=")
                    .append((String) item.getValue());
        }
        String params = parametersBuilder.toString();
        String uri = String.format(request, method, params, accessToken);
        new RequestThread(this, uri).start();
    }

    void finishRequest(String response) {
        JSONParser parser = new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse(response);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final JSONObject json = (JSONObject) obj;
        if (callback != null) {
            //run in ui thread
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    callback.OnVkResponse(json);
                }
            });
        }
    }

}
