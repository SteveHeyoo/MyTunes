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
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
    private boolean runningDelay;
    private boolean playingSong;
    
    private Timeline timeline;
    private int index;
    private List<Song> currentList;
    
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
            
            playTheSong(song);
        }
        else
        {
            mTPlayer.getMediaPlayer().stop();
            playTheSong(song);
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
                    timeline.play();
                    mTPlayer.getMediaPlayer().play();
                    mTPlayer.setPause(false);
            
                }
                else
                {
                    //pause
                    if(mTPlayer.getMediaPlayer().getCurrentTime().toMillis() < mTPlayer.getMediaPlayer().getCycleDuration().toMillis())
                    {
                        //mTPlayer.getMediaPlayer().setAutoPlay(false);
                        //playTheSong(song);
                        timeline.pause();
                        mTPlayer.getMediaPlayer().pause();                    
                        mTPlayer.setPause(true);
                        //Pause song            
                    }
                    else
                    {
                        playTheSong(song);
                    }
                }
            }
            else
            {
                //it is a new song. play the song
                mTPlayer.getMediaPlayer().stop();
                playTheSong(song);
            }          
        }
        else
        {   
                //no song playing, and no song has been played before
                playTheSong(song);
                
        }
        

        //System.out.println(mTPlayer.getMediaPlayer().getCurrentTime());
        //System.out.println("Donezo");
        
    //Thread.sleep((long)mTPlayer.getMediaPlayer().getCycleDuration().toMillis());
        //mTPlayer.getMediaPlayer().setAutoPlay(true);
    }
    
    private void playTheSong(Song song)
    {      
        if(playingSong != false)
        {
            timeline.stop();
        }

        playingSong = true;
        mTPlayer = new MyTunesPlayer(song.getFilePath());
        mTPlayer.getMediaPlayer().setAutoPlay(true);
        lastSongId = song.getId();
        //index = currentList.indexOf(song);
        startDelay(song); 
        
    }
    
    private void startDelay(Song song)
    {
        timeline = new Timeline(new KeyFrame(Duration.millis((song.getDuration()*1000)),ae -> playNextSong(song)));timeline.play();
        runningDelay = true;
    }
    
        
    private void playNextSong(Song song)
    {
        playingSong = false;
        playTheSong(getNextSongInCurrentList(song));
        runningDelay = false;
    }
    /**
     * Returns the next song in the currently active list og songs
     * @param song
     * @return the next song
     */
    public Song getNextSongInCurrentList(Song song)
    {
        
        Song nextSong;
        if(songs.contains(song))
        {
            currentList = songs;
            //System.out.println("List: all list");
        }
        else if(songsByPlaylistId.contains(song))
        {
            currentList = songsByPlaylistId;
            ///System.out.println("List: playlist list");
        }
        else
        {
            currentList = null;
            System.out.println("ERROR no list found");
        }
        
        
        int index = currentList.indexOf(song);

        if (index != currentList.size()-1)
        {
            System.out.println("next sonng is: " + index + 1);
            nextSong = currentList.get(index + 1);
            index++;
        }
        else
        {
            System.out.println("nexxxt song is: " + currentList.get(0).getAllSongStringInfo());
            nextSong = currentList.get(0);
            index = 0;
        }
        return nextSong;
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

    public void createNewPlaylist(Playlist playlistToEdit, String playlistName) throws IOException
    {
        if (playlistToEdit == null)
        {
            Playlist playlistToAdd = mMgr.createNewPlaylist(playlistName);
            playlists.add(playlistToAdd);
            
        }
        else
        {
            playlistToEdit.setName(playlistName);
            mMgr.editPlaylistName(playlistToEdit);
            playlists.clear();
            playlists.addAll(mMgr.getAllPlayLists());          
        }

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

    public void showPlaylistSongs(int playlistId) throws UnsupportedAudioFileException
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

    public void addSongToPlaylist(Song songToAdd, Playlist playlistToAddTo) throws UnsupportedAudioFileException
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
