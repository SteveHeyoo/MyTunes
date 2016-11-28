/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.GUI;

import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Bo
 */
public class MyTunes extends Application
{

    @Override
    public void start(Stage stage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("VIEW/FXMLDocument.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
        play();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }

    public void play()
    {

        FileChooser fc = new FileChooser();
        File file = fc.showOpenDialog(null);
        if (file != null)
        {
            String path = file.getAbsolutePath();

            path = path.replace("\\", "/");
            Media media = new Media(new File(path).toURI().toString());
            MediaPlayer mediaPlayer;

            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.stop();

            MediaView mediaView = new MediaView(mediaPlayer);
            mediaView.setMediaPlayer(mediaPlayer);
            mediaPlayer.setAutoPlay(true);
        }

    }

}
