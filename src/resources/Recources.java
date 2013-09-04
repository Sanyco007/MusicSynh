package resources;

import javafx.scene.image.Image;

public class Recources {

    public static Image vkIcon;

    public static void init() {
        vkIcon = new Image(new Recources().getClass().getResourceAsStream("icon.png"));
    }

}
