package sample;

import auth.AuthProfile;
import auth.VkAuth;
import gui.MainController;
import gui.StageStarter;
import javafx.application.Application;
import javafx.stage.Stage;
import resources.Recources;

import java.io.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        boolean hasToken = false;
        try {
            File authFile = new File("auth.dat");
            //Если настройки есть - читаем их
            if (authFile.exists()) {
                BufferedReader rd = new BufferedReader(new FileReader(authFile));
                String accessToken = rd.readLine();
                String userId = rd.readLine();
                String expires = rd.readLine();
                long expiresOver = Long.parseLong(expires);
                //Токен еще действителен
                if (System.currentTimeMillis() / 1000 < expiresOver) {
                    hasToken = true;
                    AuthProfile.initAuth(accessToken, userId, expiresOver);
                    StageStarter.startStage(MainController.class.getResource("mainstage.fxml"), "VkMusic");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (!hasToken) {
                new VkAuth().show();
            }
        }
    }

    public static void main(String[] args) {
        Recources.init();
        launch(args);
    }
}
