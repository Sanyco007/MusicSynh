package sample;

import auth.VkAuth;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        new VkAuth().show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
