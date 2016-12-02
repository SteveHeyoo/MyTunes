/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.GUI.CONTROLLER;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mytunes.BE.Playlist;
import mytunes.GUI.MODEL.Model;

/**
 * FXML Controller class
 *
 * @author Stefan-VpcEB3J1E
 */
public class NewEditPlaylistViewController implements Initializable
{

    @FXML
    private TextField lblNameNewEditPlaylist;

    Model model;
    private boolean PlayListExist;
    private Playlist playlistToEdit;

    public NewEditPlaylistViewController()
    {
        model = Model.getInstance();

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }

    @FXML
    private void handleSaveNewPlaylist(ActionEvent event) throws IOException
    {
        String playlistName = lblNameNewEditPlaylist.getText().trim();
        model.createNewPlaylist(playlistToEdit, playlistName);

        Stage getStage = (Stage) lblNameNewEditPlaylist.getScene().getWindow();
        getStage.close();

    }

    @FXML
    private void handleCancelNewPlaylist(ActionEvent event)
    {
        Stage getStage = (Stage) lblNameNewEditPlaylist.getScene().getWindow();
        getStage.close();
    }

    public void setPlaylistToEdit(Playlist playlist)
    {
        playlistToEdit = playlist;
        lblNameNewEditPlaylist.setText(playlist.getName());
    }
}
