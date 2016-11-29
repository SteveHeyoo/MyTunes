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
import javafx.scene.control.cell.PropertyValueFactory;
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

    private void loadSongDataView(Song song) throws IOException
    {
        // Fetches primary stage and gets loader and loads FXML file to Parent
        Stage primStage = (Stage) tblSong.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("SongEdit.fxml"));
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
    private void mousePressedOnTableView(MouseEvent event) throws IOException
    {
        // Check double-click left mouse button
        if (event.isSecondaryButtonDown() && event.getClickCount() == 1)
        {
            Song selectedSong = tblSong.getSelectionModel().getSelectedItem();
            loadSongDataView(selectedSong);
        }
    }

    @FXML
    private void handleTblViewMouseClick(MouseEvent event)
    {
        Song song = tblSong.getSelectionModel().getSelectedItem();

        if (event.getClickCount() == 2 && song != null && event.isPrimaryButtonDown())
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
        NewEditPlaylistViewController patientViewController = loader.getController();

        // sets new stage as modal window
        Stage stagePatientView = new Stage();
        stagePatientView.setScene(new Scene(root));
        stagePatientView.initModality(Modality.WINDOW_MODAL);
        stagePatientView.initOwner(primStage);

        stagePatientView.show();
    }

}
