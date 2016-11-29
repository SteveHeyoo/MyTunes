/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.GUI.CONTROLLER;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
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
    private Label lblPlaylist;

    @FXML
    private Label lblSongPlaylist;

    @FXML
    private Label lblFilter;

    @FXML
    private TableColumn<Playlist, String> columnPlaylistName;
    @FXML
    private TableColumn<Playlist, Integer> columnSongNumber;
    @FXML
    private TableColumn<Playlist, Integer> columnPlaylistTime;

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
    private Label label;
    @FXML
    private TableView<?> tblPlaylist;
    @FXML
    private Button btnPreviousSong;
    @FXML
    private Button btnPlaySong;
    @FXML
    private Button btnNextSong;
    @FXML
    private ListView<?> listPlaylistSong;
    @FXML
    private Button addSongToPlaylist;

    @FXML
    private Button btnClose;
    @FXML
    private Button btnNewPlaylist;
    @FXML
    private Button btnEditPlaylist;
    @FXML
    private Button btnDeletePlaylist;
    @FXML
    private Button btnDeleteSongFromPlaylist;
    @FXML
    private Button btnMoveSongUp;
    @FXML
    private Button btnMoveSongDown;
    @FXML
    private Button btnDeleteSong;
    @FXML
    private Button btnEditSong;
    @FXML
    private Button btnAddNewSong;
    @FXML
    private TextField txtFieldSearch;
    @FXML
    private Button btnSearch;

    private Model model;

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

        //I bind the table to a list of data (Empty at startup):
        tblSong.setItems(model.getAllSongs());
    }

    @FXML
    private void handleNewSong(ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);

        if (file != null)
        {
            // to do
            model.createNewSong(file);
        }
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

}
