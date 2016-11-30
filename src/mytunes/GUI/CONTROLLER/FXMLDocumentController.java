/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.GUI.CONTROLLER;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mytunes.BE.Playlist;
import mytunes.BE.Song;
import mytunes.GUI.MODEL.Model;

/**
 *
 * @author Bo
 */
public class FXMLDocumentController implements Initializable
{

    @FXML
    private Label lblSong;
    @FXML
    private Label lblSongPlaylist;

    @FXML
    private TableView<Playlist> tblPlaylist;
    @FXML
    private TableColumn<Playlist, String> columnPlaylistName;
    @FXML
    private TableColumn<Playlist, String> columnPlaylistNumberOfSongs;
    @FXML
    private TableColumn<Playlist, String> columnPlaylistTotalDuration;

    @FXML
    private TableView<Song> tblSong;
    @FXML
    private TableColumn<Song, String> columnTitle;
    @FXML
    private TableColumn<Song, String> columnArtist;
    @FXML
    private TableColumn<Song, String> columnTime;
    @FXML
    private TableColumn<?, ?> columnCategory;

    @FXML
    private ListView<?> listPlaylistSong;

    @FXML
    private TextField txtFieldSearch;

    private Model model;
    @FXML
    private Button btnPreviousSong;
    @FXML
    private Button btnPlaySong;
    @FXML
    private Button btnNextSong;
    @FXML
    private Button addSongToPlaylist;

    public FXMLDocumentController()
    {
        model = Model.getInstance();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        dataBind();
    }

    private void dataBind()
    {
        //I define the mapping of the table's columns to the objects that are added to it.
        columnTitle.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getTitle()));
        columnArtist.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getArtist()));
        columnTime.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getDurationInMinutes()));
        columnPlaylistName.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getName()));

        //I bind the table to a list of data (Empty at startup):
        tblSong.setItems(model.getAllSongs());
        tblPlaylist.setItems(model.getAllPlaylists());
    }

    @FXML
    private void handleNewSong(ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter mp3Filter = new FileChooser.ExtensionFilter("MP3 Files(*.mp3)", "*.mp3");
        fileChooser.getExtensionFilters().add(mp3Filter);
        File file = fileChooser.showOpenDialog(null);

        if (file != null)
        {
            // to do
            model.createNewSong(file);
        }
    }

    private void loadSongDataView(Song song) throws IOException
    {
        // Fetches primary stage and gets loader and loads FXML file to Parent
        Stage primStage = (Stage) tblSong.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/mytunes/GUI/VIEW/SongEdit.fxml"));
        Parent root = loader.load();

        // Fetches controller from patient view
        SongEditController songEditController
                = loader.getController();

        songEditController.setSong(song);

        // Sets new stage as modal window
        Stage stageSongEdit = new Stage();
        stageSongEdit.setScene(new Scene(root));

        stageSongEdit.initModality(Modality.WINDOW_MODAL);
        stageSongEdit.initOwner(primStage);

        stageSongEdit.show();
    }



    @FXML
    private void handleTblViewMouseClick(MouseEvent event)
    {
        Song song = tblSong.getSelectionModel().getSelectedItem();

        if (event.getClickCount() == 2 && song != null)
        {
            model.playSong(song);
        }
    }

    @FXML
    private void handleTblViewSongsDelete(ActionEvent event)
    {
        Song song = tblSong.getSelectionModel().getSelectedItem();
        model.deleteSong(song);
    }

    @FXML
    private void handleNewPlaylist(ActionEvent event) throws IOException
    {
        // TODO Display the New/Edit gui to enter a name to the new playlist
        Stage primStage = (Stage) tblSong.getScene().getWindow();
        //mvc pattern til fxml sti
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/GUI/VIEW/NewEditPlaylistView.fxml"));

        Parent root = loader.load();

        //Fethes controller from patient view
        NewEditPlaylistViewController newEditController = loader.getController();
        
        

        // sets new stage as modal window
        Stage stageNewEditPlaylist = new Stage();
        stageNewEditPlaylist.setScene(new Scene(root));
        stageNewEditPlaylist.initModality(Modality.WINDOW_MODAL);
        stageNewEditPlaylist.initOwner(primStage);
        stageNewEditPlaylist.setResizable(false);

        stageNewEditPlaylist.show();
        
        
    }

    @FXML
    private void handleDeletePlayList(ActionEvent event)
    {
        Playlist playlist = tblPlaylist.getSelectionModel().getSelectedItem();
        model.deletPlaylist(playlist);
    }

    @FXML
    private void handleSongEdit(ActionEvent event)
    {
        Song song = tblSong.getSelectionModel().getSelectedItem();
        try
        {
            loadSongDataView(song);
        }
        catch (IOException ex)
        {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
            
    
}
