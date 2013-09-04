package gui;

import javafx.stage.Stage;
import network.IVkResponse;
import network.VkConnection;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainStage extends Stage implements IVkResponse {

    private VkConnection vk;

    public MainStage(String token) {
        vk = new VkConnection(token);
        Map parameters = new HashMap<String, String>();
        parameters.put("q", "beatles");
        parameters.put("count", "3");
        vk.setOnVkResponse(this);
        vk.sendApiRequest("audio.search", parameters);
    }

    @Override
    public void OnVkResponse(JSONObject json) {
        System.out.println(json);
    }
}
