package gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class StageStarter {

    public static void startStage(URL url, String title) {
        Parent root = null;
        try {
            root = FXMLLoader.load(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (root != null) {
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        }
    }

}
