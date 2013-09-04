package gui;

import javafx.stage.Stage;
import network.VkConnection;

public class MainStage extends Stage {

    private VkConnection vk;

    public MainStage(String token) {
        vk = new VkConnection(token);
    }

}
