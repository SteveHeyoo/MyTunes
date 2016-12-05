/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.GUI.CONTROLLER;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.sound.sampled.UnsupportedAudioFileException;
import mytunes.BE.Playlist;
import mytunes.BE.Song;
import mytunes.GUI.MODEL.Model;

/**
 *
 * @author Bo
 */
public class FXMLDocumentController implements Initializable
{
    private Media me;

    private Model model;

    @FXML
    private Label lblSong;
    @FXML
    private Label lblSongPlaylist;

    @FXML
    private TableView<Playlist> tblPlaylist;
    @FXML
    private TableColumn<Playlist, String> columnPlaylistName;
    @FXML
    private TableColumn<Playlist, Integer> columnPlaylistNumberOfSongs;
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
    private ListView<Song> listPlaylistSong;

    @FXML
    private TextField txtFieldSearch;

    @FXML
    private Button btnPreviousSong;
    @FXML
    private Button btnPlaySong;
    @FXML
    private Button btnNextSong;

    @FXML
    private Slider volumeSlide;
    @FXML
    private Label lblVolume;

    private Song currentSong;
    private Control currentControlList;
    
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
        columnPlaylistNumberOfSongs.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getNumberOfSongsInPlaylist()));
        //I bind the table to a list of data (Empty at startup):
        tblSong.setItems(model.getAllSongs());
        tblPlaylist.setItems(model.getAllPlaylists());
        listPlaylistSong.setItems(model.getAllSongsByPlaylistId());
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
        currentSong = tblSong.getSelectionModel().getSelectedItem();
        currentControlList = tblSong; 
        
        if (event.getClickCount() == 2 && currentSong != null)
        {
            model.playSong(currentSong);
            
            model.setCurrentListControl(currentControlList);
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
        showNewEditPlaylistDialog(null);

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
        } catch (IOException ex)
        {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleShowPlaylistSongs(MouseEvent event) throws IOException
    {
        Playlist playlist = tblPlaylist.getSelectionModel().getSelectedItem();
        int index = tblPlaylist.getSelectionModel().getSelectedIndex();
        int playlistId = playlist.getId();
        if (playlist != null)
        {
            try
            {
                model.showPlaylistSongs(playlistId);
            } catch (UnsupportedAudioFileException ex)
            {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
            try
            {
                model.showPlaylistSongs(playlistId);
            } catch (UnsupportedAudioFileException ex)
            {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            

        }
        if (event.getClickCount() == 2)
        {
            showNewEditPlaylistDialog(playlist);

        }

    }

    @FXML
    private void handleAddSongToPlaylist(ActionEvent event)
    {
        

        try
        {
            Song songToAdd = tblSong.getSelectionModel().getSelectedItem();
            Playlist playlistToAddTo = tblPlaylist.getSelectionModel().getSelectedItem();
            int plIndexNum = tblPlaylist.getSelectionModel().getSelectedIndex();
            
            model.addSongToPlaylist(songToAdd, playlistToAddTo);
            tblPlaylist.getSelectionModel().clearAndSelect(plIndexNum);
        } 
        catch (UnsupportedAudioFileException ex)
        {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void handleSongsOnPlaylistPlay(MouseEvent event)
    {
        currentSong = listPlaylistSong.getSelectionModel().getSelectedItem(); 
        currentControlList = listPlaylistSong;
        
        if (event.getClickCount() == 2 && currentSong != null)
        {
            model.playSong(currentSong);
            //currentControlList = listPlaylistSong;
            model.setCurrentListControl(currentControlList);
        }
    }

    @FXML
    private void handleSearch3(KeyEvent event)
    {
        String query = txtFieldSearch.getText().trim();

        List<Song> searchResult = null;
        searchResult = model.filterSongs(query);
        model.setSongs(searchResult);
    }

    private void handleMoveSongUp(ActionEvent event)
    {
        Song songToMoveUp = listPlaylistSong.getSelectionModel().getSelectedItem();

        if (songToMoveUp != null)
        {
            listPlaylistSong.getSelectionModel().clearAndSelect(model.moveSongUp(songToMoveUp) - 1);

        }
    }

    private void handleMoveSongDown(ActionEvent event)
    {
        Song songToMoveDown = listPlaylistSong.getSelectionModel().getSelectedItem();

        if (songToMoveDown != null)
        {
            listPlaylistSong.getSelectionModel().clearAndSelect(model.moveSongDown(songToMoveDown) + 1);
        }
    }

    @FXML
    private void handlePlayButton(ActionEvent event)
    {
        //model.playSong(currentSong);
        model.setCurrentListControl(currentControlList);
        model.playSongButtonClick();
        
        
        //btnPlaySong.setText("Pause");
    }
    
    private void showNewEditPlaylistDialog(Playlist playlist) throws IOException
    {
        // TODO Display the New/Edit gui to enter a name to the new playlist
        Stage primStage = (Stage) tblSong.getScene().getWindow();
        //mvc pattern til fxml sti
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/GUI/VIEW/NewEditPlaylistView.fxml"));

        Parent root = loader.load();

        //Fethes controller from patient view
        NewEditPlaylistViewController newEditController = loader.getController();
        if (playlist != null)
        {
            newEditController.setPlaylistToEdit(playlist);

        }

        // sets new stage as modal window
        Stage stageNewEditPlaylist = new Stage();
        stageNewEditPlaylist.setScene(new Scene(root));
        stageNewEditPlaylist.initModality(Modality.WINDOW_MODAL);
        stageNewEditPlaylist.initOwner(primStage);
        stageNewEditPlaylist.setResizable(false);

        stageNewEditPlaylist.show();
    }

    private void handleEditPlaylist(ActionEvent event) throws IOException
    {
        Playlist playlist = tblPlaylist.getSelectionModel().getSelectedItem();
        showNewEditPlaylistDialog(playlist);

    }

    @FXML
    private void handleVolume(MouseEvent event)
    {
    }

    @FXML
    private void handlePlayNextSong(ActionEvent event)
    {
        //model.playSong(model.getNextSongInCurrentList(model.getSongPlaying()));
        model.pressNextButton();
        /*
        try
        {
            ListView<Song> playlist = (ListView)currentList;
            playlist.getSelectionModel().clearAndSelect(currentList.indexOf(model.getNextSongInCurrentList(currentSong)));
            
        }
        catch(ClassCastException c)
        {
            TableView<Song> playlist = (TableView)currentList;
            playlist.getSelectionModel().clearAndSelect(currentList.indexOf(nextSong));
        }   */
    }

    @FXML
    private void handlePlayPreviousSong(ActionEvent event)
    {
    }

}
