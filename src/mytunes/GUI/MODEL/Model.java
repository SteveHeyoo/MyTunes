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
import mytunes.BE.Playlist;
import mytunes.BE.Song;
import mytunes.BLL.MusicManager;
import mytunes.BLL.MyTunesPlayer;

/**
 *
 * @author Stefan-VpcEB3J1E
 */
public class Model
{

    private static Model INSTANCE;
    private MusicManager mMgr;
    private ObservableList songs, playlists;
    private MyTunesPlayer mTPlayer;

    private Model()
    {
        mMgr = new MusicManager();
        songs = FXCollections.observableArrayList();
        playlists = FXCollections.observableArrayList();
        loadSongsAndPlaylists();
        
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
            songs.add(song);
        } catch (IOException ex)
        {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex)
        {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void loadSongsAndPlaylists()
    {
        try
        {
            playlists.addAll(mMgr.getAllPlayLists());
            songs.addAll(mMgr.getAllSongs());
        } catch (IOException ex)
        {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ObservableList<Song> getAllSongs()
    {
        return songs;
    }

    public void playSong(Song song)
    {
        if (mTPlayer == null)
        {
            mTPlayer = new MyTunesPlayer(song.getFilePath());
            mTPlayer.getMediaPlayer().setAutoPlay(true);
        }
        mTPlayer.getMediaPlayer().stop();
        mTPlayer = new MyTunesPlayer(song.getFilePath());
        mTPlayer.getMediaPlayer().setAutoPlay(true);

    }

    public void deleteSong(Song song)
    {
        try
        {
            mMgr.deleteSong(song.getId());
            songs.remove(song);
        } catch (IOException ex)
        {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    public void createNewPlaylist(String playlistName) throws IOException
    {
        Playlist playlistToAdd = mMgr.createNewPlaylist(playlistName);
        playlists.add(playlistToAdd);
    }

    public ObservableList<Playlist> getAllPlaylists()
    {
        return playlists;
    }
    
    public void deletPlaylist(Playlist playlist)
    {
        try
        {
            mMgr.deletePlaylist(playlist.getId());
            playlists.remove(playlist);
        }
        catch(IOException ex)
        {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
