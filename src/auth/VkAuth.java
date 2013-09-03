package auth;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class VkAuth {

    private Scene _scene;
    private WebView _web;
    private String accessToken;
    private Label label;

    private String request = "https://api.vk.com/method/%s?%s&access_token=%s";

    public VkAuth() {
        _web = new WebView();
        GridPane layout = new GridPane();
        layout.add(_web, 0, 0);
        label = new Label("Initialization...");
        _scene = new Scene(layout, 570, 370);
    }

    public void show() {
        final Stage s = new Stage();
        s.setTitle("Authorization");
        s.setScene(_scene);
        s.show();

        _web.getEngine().load(AuthProfile.getAuthLink());
        _web.getEngine().setOnStatusChanged(new EventHandler<WebEvent<String>>() {
            @Override
            public void handle(WebEvent<String> stringWebEvent) {
                String location = ((WebEngine)stringWebEvent.getSource()).getLocation();
                if (location.contains("access_token")) {
                    int pos = location.indexOf("access_token=") +  "access_token=".length();
                    int len = location.indexOf("&", pos);
                    accessToken = location.substring(pos, len);
                    s.close();
                    testAPI();
                }
            }
        });
    }

    private void testAPI() {
        String get = String.format(request, "users.get", "user_ids=1", accessToken);

    }

}
