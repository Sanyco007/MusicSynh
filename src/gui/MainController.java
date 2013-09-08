package gui;

import audio.AudioMethods;
import audio.AudioObject;
import audio.IAudioResult;
import auth.AuthProfile;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import network.VkConnection;


public class MainController implements IAudioResult {

    @FXML private ListView lwSongs;
    @FXML private ListView lwPlaylists;
    @FXML private ImageView ivAvatar;
    @FXML private ProgressBar pbDuration;

    public MainController() {
        VkConnection vk = new VkConnection(AuthProfile.getAccessToken());
        AudioMethods audioMethods = new AudioMethods(vk);
        audioMethods.setOnAudioResult(this);
        audioMethods.search("metallica", 10);
    }

    private MediaPlayer player;

    @Override
    public void OnAudioResult(AudioObject[] audios) {
        for (int i = 0; i < audios.length; i++) {
            lwSongs.getItems().add(audios[i]);
        }
        lwSongs.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                AudioObject audio = (AudioObject) lwSongs.getSelectionModel().getSelectedItem();
                Media audioFile = new Media(audio.getUrl());
                try {
                    if (player != null) {
                        player.stop();
                    }
                    player = new MediaPlayer(audioFile);
                    player.play();
                } catch (Exception e) {
                    System.exit(0);
                }
            }
        });
    }
}
