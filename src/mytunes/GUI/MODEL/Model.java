/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.GUI.MODEL;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Duration;
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
    private ObservableList songs, playlists, songsByPlaylistId;
    private MyTunesPlayer mTPlayer;
    private int lastSongId;
    

    private Model()
    {
        mMgr = new MusicManager();
        songs = FXCollections.observableArrayList();
        playlists = FXCollections.observableArrayList();
        songsByPlaylistId = FXCollections.observableArrayList();
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
        else
        {
            mTPlayer.getMediaPlayer().stop();
            mTPlayer = new MyTunesPlayer(song.getFilePath());
            mTPlayer.getMediaPlayer().setAutoPlay(true);            
        }        
        
    }
    
    public void playSongButtonClick(Song song)
    {
//        if (mTPlayer == null)
//        {           
//            mTPlayer = new MyTunesPlayer(song.getFilePath());            
//        }
//        else
//        {  
//        }
        int id = song.getId();
        
        if(mTPlayer != null)
        {
            
            if(lastSongId == id)
            {
                //it is the same song as the last. the song should pause/play
                if(mTPlayer.isPaused())
                {
                    //resume
                    mTPlayer.getMediaPlayer().play();
                    mTPlayer.setPause(false);
            
                }
                else
                {
                    //pause
                    if(mTPlayer.getMediaPlayer().getCurrentTime().toMillis() < mTPlayer.getMediaPlayer().getCycleDuration().toMillis())
                    {
                        //mTPlayer.getMediaPlayer().setAutoPlay(false);
                        mTPlayer.getMediaPlayer().pause();
                        mTPlayer.setPause(true);
                        //Pause song            
                    }
                    else
                    {
                        mTPlayer = new MyTunesPlayer(song.getFilePath());
                        mTPlayer.getMediaPlayer().setAutoPlay(true); 
                    }
                }
            }
            else
            {
                //it is a new song. play the song
                mTPlayer.getMediaPlayer().stop();
                mTPlayer = new MyTunesPlayer(song.getFilePath());
                mTPlayer.getMediaPlayer().setAutoPlay(true); 
            }          
        }
        else
        {   
                mTPlayer = new MyTunesPlayer(song.getFilePath());
                mTPlayer.getMediaPlayer().setAutoPlay(true);         
                //no song playing
        }
        
        lastSongId = song.getId();
        //System.out.println(mTPlayer.getMediaPlayer().getCurrentTime());
        //System.out.println("Donezo");
        
    //Thread.sleep((long)mTPlayer.getMediaPlayer().getCycleDuration().toMillis());
        //mTPlayer.getMediaPlayer().setAutoPlay(true);
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

    public ObservableList<Song> getAllSongsByPlaylistId()
    {
        return songsByPlaylistId;
    }

    public void deletPlaylist(Playlist playlist)
    {
        try
        {
            mMgr.deletePlaylist(playlist.getId());

            playlists.remove(playlist);

        } catch (IOException ex)
        {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showPlaylistSongs(int playlistId)
    {
        try
        {
            songsByPlaylistId.clear();
            songsByPlaylistId.addAll(mMgr.getSongsByPlaylistId(playlistId));
        } catch (IOException ex)
        {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addSongToPlaylist(Song songToAdd, Playlist playlistToAddTo)
    {
        try
        {
            //  songsByPlaylistId.add(songToAdd);

            mMgr.addSongToPlaylist(songToAdd.getId(), playlistToAddTo.getId());
            showPlaylistSongs(playlistToAddTo.getId());
        } catch (IOException ex)
        {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public List<Song> filterSongs(String query)
    {
        List<Song> songList = null;
        try
        {
            songList = mMgr.search(query);

        } catch (IOException ex)
        {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return songList;
    }

    public void setSongs(List<Song> songList)
    {
        songs.clear();
        songs.addAll(songList);
    }

    public int moveSongUp(Song songToMoveUp)
    {
        int indexId = songsByPlaylistId.indexOf(songToMoveUp);
        if (indexId != 0)
        {
            Collections.swap(songsByPlaylistId, indexId - 1, indexId);

        }
        else
        {
            indexId += 1;
        }
        return indexId;

    }

    public int moveSongDown(Song songToMoveDown)
    {
        int indexId = songsByPlaylistId.indexOf(songToMoveDown);
        if (indexId  != songsByPlaylistId.size()-1)
        {
            Collections.swap(songsByPlaylistId, indexId + 1, indexId);

        }
        else
        {
            indexId -= 1;
        }
        return indexId;
    }

}
