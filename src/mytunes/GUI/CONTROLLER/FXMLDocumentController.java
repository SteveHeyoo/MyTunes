/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.GUI.CONTROLLER;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 *
 * @author Bo
 */
public class FXMLDocumentController implements Initializable
{
    
    @FXML
    private Label lblSong;
    
    @FXML
    private Label lblPlaylist;
    
    @FXML
    private Label lblSongPlaylist;
            
    @FXML
    private Label lblFilter;
    
    @FXML
    private TableView<Playlist> tblPlayList;
    @FXML
    private TableColumn<Playlist, String> columnPlaylistName;
    @FXML
    private TableColumn<Playlist, int> columnSongNumber;
    @FXML
    private TableColumn<Playlist, int> columnPlaylistTime;
    
    @FXML
    private TableView<Song> tblSong;
    @FXML
    private TableColumn<Song, String> columnTitle;
    @FXML
    private TableColumn<Song, String> columnArtist;
    @FXML
    private TableColumn<Song, String> coulmnCategory; 
    @FXML
    private TableColumn<Song, int> columnSongTime;
    
    
    @FXML
    private void handleButtonAction(ActionEvent event)
    {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    
    
}
