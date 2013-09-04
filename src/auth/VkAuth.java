package auth;

import gui.MainStage;
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

public class VkAuth {

    private Scene _scene;
    private WebView _web;
    private GridPane layout;

    private ProgressIndicator pi;
    private Label label;

    private String accessToken;
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
                    if (!startMainStage) {
                        new MainStage(accessToken).show();
                        startMainStage = true;
                        s.close();
                    }
                }
            }
        });
    }

}
