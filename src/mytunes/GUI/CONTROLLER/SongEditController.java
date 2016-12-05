/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.GUI.CONTROLLER;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
    @FXML
    private Label lblSongInfo;
    @FXML
    private Label lblTitle;
    @FXML
    private Label lblArtist;
    @FXML
    private Label lblCategory;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSave;
    @FXML
    private Label lblTime;
    @FXML
    private Label lblFile;
    @FXML
    private Button btnChoose;
    
    
    
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

    @FXML
    private void handleSaveSong(ActionEvent event)
    {
//        String songName = txtTitle.getText().trim();
//        String artistName = txtArtist.getText().trim();
//        String fileName = txtFile.getText().trim();
//        
//        
//        
//        Stage getStage = (Stage) btnSave.getScene().getWindow();
//        getStage.close();
    }
    
    @FXML
    private void handleCancelEdit(ActionEvent event)
    {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
    
}
