/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.GUI.CONTROLLER;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import mytunes.BE.Song;

/**
 *
 * @author Bo
 */
public class SongEditController implements Initializable
{
    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtArtist;
    @FXML
    private ComboBox cbCategory;
    @FXML
    private TextField txtTime;
    @FXML
    private TextField txtFile;
    
    private Song currentSong;
    
    public void initialize(URL url, ResourceBundle rb)
    {
    
    }
    
    public void setSong(Song song)
    {
        currentSong = song;
        fillTextField();
    }
    
    private void fillTextField()
    {
        txtTitle.setText(currentSong.getTitle());
        txtArtist.setText(currentSong.getArtist());
        txtTime.setText(currentSong.getDuration()+"");
        txtFile.setText(currentSong.getFilePath());
        
    }
    
}
