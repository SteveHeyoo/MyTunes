/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.GUI.MODEL;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.sound.sampled.UnsupportedAudioFileException;
import mytunes.BE.Song;
import mytunes.BLL.MusicManager;

/**
 *
 * @author Stefan-VpcEB3J1E
 */
public class Model
{

    private static Model INSTANCE;
    private MusicManager mMgr;
    private ObservableList songs;

    private Model()
    {
        mMgr = new MusicManager();
        songs = FXCollections.observableArrayList();
        //mMgr.addSong(new Song(0, artist, title, filePath, 0));

    }

    public static Model getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new Model();
        }
        return INSTANCE;
    }

    public void createNewSong(File file)
    {
        Song song;
        try
        {
            song = mMgr.addSong(file);
            System.out.println(song.getArtist());
            System.out.println(song.getTitle());
            System.out.println(song.getDuration());
            System.out.println(song.getId());
            songs.add(song);
        } catch (IOException ex)
        {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex)
        {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
    }

}
