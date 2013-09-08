package auth;

import gui.MainController;
import gui.StageStarter;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import resources.Recources;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class VkAuth {

    private Scene _scene;
    private WebView _web;
    private GridPane layout;

    private ProgressIndicator pi;
    private Label label;

    private String accessToken;
    private String userId;
    private long expiresOver;

    private boolean startMainStage = false;
    private boolean loaded = false;

    public VkAuth() {
        _web = new WebView();

        pi = new ProgressIndicator();
        pi.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);

        label = new Label("Initialization...");

        layout = new GridPane();
        layout.add(pi, 0, 0);
        layout.add(label, 1, 0);
        layout.setHgap(10d);
        layout.setPadding(new Insets(165, 0, 0, 220));
        _scene = new Scene(layout, 575, 380);
    }

    public void show() {
        final Stage s = new Stage();
        s.setResizable(false);
        s.setTitle("Authorization");
        s.getIcons().add(Recources.vkIcon);
        s.setScene(_scene);
        s.show();

        _web.getEngine().load(AuthProfile.getAuthLink());
        _web.getEngine().setOnStatusChanged(new EventHandler<WebEvent<String>>() {
            @Override
            public void handle(WebEvent<String> stringWebEvent) {
                if (!loaded) {
                    layout.setHgap(0d);
                    layout.setPadding(new Insets(0));
                    layout.add(_web, 0, 0);
                    layout.getChildren().remove(label);
                    layout.getChildren().remove(pi);
                    loaded = true;
                }
                String location = ((WebEngine)stringWebEvent.getSource()).getLocation();
                if (location.contains("access_token")) {
                    int pos = location.indexOf("access_token=") +  "access_token=".length();
                    int len = location.indexOf("&", pos);
                    accessToken = location.substring(pos, len);

                    pos = location.indexOf("user_id=") + "user_id=".length();
                    userId = location.substring(pos);

                    pos = location.indexOf("expires_in=") + "expires_in=".length();
                    len = location.indexOf("&", pos);
                    String expiresIn = location.substring(pos, len);
                    int seconds = Integer.parseInt(expiresIn);

                    long currentDate = System.currentTimeMillis() / 1000;
                    expiresOver = currentDate + seconds;

                    AuthProfile.initAuth(accessToken, userId, expiresOver);

                    try {
                        Writer sw = new FileWriter("auth.dat");
                        sw.append(accessToken + "\r\n");
                        sw.append(userId + "\r\n");
                        sw.append(expiresOver + "");
                        sw.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (!startMainStage) {
                        StageStarter.startStage(MainController.class.getResource("mainstage.fxml"), "VkMusic");
                        startMainStage = true;
                        s.close();
                    }
                }
            }
        });
    }

}
