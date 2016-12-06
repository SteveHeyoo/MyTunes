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
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.util.Duration;
import javax.sound.sampled.UnsupportedAudioFileException;
import mytunes.BE.Playlist;
import mytunes.BE.Song;
import mytunes.BLL.MusicManager;
import mytunes.BLL.MyTunesPlayer;
import mytunes.GUI.CONTROLLER.FXMLDocumentController;

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
    private int lastSongIndex;
    private boolean runningDelay;
    private boolean playingSong;
    
    private Song songPlaying;
    private Timeline timeline;
    private List<Song> currentList;
    private Control currentListControl;
    private Playlist currentPlaylist;
    private int currentIndex;
    private List<Song> songsCleared;
    
    private Model()
    {
        mMgr = new MusicManager();
        songs = FXCollections.observableArrayList();
        playlists = FXCollections.observableArrayList();
        songsByPlaylistId = FXCollections.observableArrayList();
        loadSongsAndPlaylists();
       
   
    }

    public void setCurrentListControl(Control currentListControl)
    {
        this.currentListControl = currentListControl; 
    }
    
    public void setCurrentPlaylist(Playlist playlist)
    {
        currentPlaylist = playlist;
    }
    
    public void setIndex(int index)
    {
        currentIndex = index;
    }
    
    public static Model getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new Model();
        }
        return INSTANCE;
    }

    public void createNewSong(File file) throws IOException, UnsupportedAudioFileException
    {
        Song song;
        song = mMgr.addSong(file);
        songs.add(song);

    }

    private void loadSongsAndPlaylists()
    {
        try
        {
            playlists.clear();
            songs.clear();
            playlists.addAll(mMgr.getAllPlayLists());
            songs.addAll(mMgr.getAllSongs());
        } 
        catch (IOException ex)
        {
            ex.printStackTrace();
        } catch (UnsupportedAudioFileException ex)
        {
           ex.printStackTrace();
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
            /*
            Song songToPlay;
            try
            {
                ListView<Song> playlist = (ListView)currentListControl;
                songToPlay = playlist.getSelectionModel().getSelectedItem();
            
            }
            catch(ClassCastException c)
            {
                TableView<Song> playlist = (TableView)currentListControl;
                songToPlay = playlist.getSelectionModel().getSelectedItem();
           
            }   
            playTheSong(songToPlay);
            */
        
        }            
        
    }

    
    public void playSongButtonClick()
    {
//        if (mTPlayer == null)
//        {           
//            mTPlayer = new MyTunesPlayer(song.getFilePath());            
//        }
//        else
//        {  
//        }
        Song songToPlay;
            try
            {
                ListView<Song> playlist = (ListView)currentListControl;
                songToPlay = playlist.getSelectionModel().getSelectedItem();
            
            }
            catch(ClassCastException c)
            {
                TableView<Song> playlist = (TableView)currentListControl;
                songToPlay = playlist.getSelectionModel().getSelectedItem();
           
            }   
        
        
        int id = songToPlay.getId();
        
        
        
        if(mTPlayer != null)
        {
            
            if(currentIndex == lastSongIndex)
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
                        playTheSong(songToPlay);
                    }
                }
            }
            else
            {
                //it is a new song. play the song
                mTPlayer.getMediaPlayer().stop();
                playTheSong(songToPlay);
            }          
        }
        else
        {   
                //no song playing, and no song has been played before
                playTheSong(songToPlay);
                
        }
        

        //System.out.println(mTPlayer.getMediaPlayer().getCurrentTime());
        //System.out.println("Donezo");
        
    //Thread.sleep((long)mTPlayer.getMediaPlayer().getCycleDuration().toMillis());
        //mTPlayer.getMediaPlayer().setAutoPlay(true);
    }
    
    private void playTheSong(Song song)
    {      

        if(playingSong == true)
        {
            timeline.stop();
            
        }
        songPlaying = song;
        playingSong = true;
        mTPlayer = new MyTunesPlayer(song.getFilePath());
        mTPlayer.getMediaPlayer().setAutoPlay(true);
        
        lastSongId = song.getId();
        lastSongIndex = currentIndex;
        
        ListView playlist = null;
        
        try
        {
            playlist = (ListView)currentListControl;
            //playlist.getSelectionModel().clearAndSelect(currentList.indexOf(nextSong));
            //currentIndex = playlist.getSelectionModel().getSelectedIndex();
            //currentIndex = playlist.ge
            
        }
        catch(ClassCastException c)
        {
            System.out.println("sdas");
        }
        
        //index = currentList.indexOf(song);
        startDelay(song); 
        //currentIndex = playlist.getSelectionModel().getSelectedIndex();
        
        
    }
    
    private void startDelay(Song song)
    {
        timeline = new Timeline(new KeyFrame(Duration.millis((song.getDuration()*1000)),ae -> playNextSong(false)));timeline.play();
        runningDelay = true;
    }
    
    public Song getSongPlaying()
    {
        return songPlaying;
    }
        
    public void pressNextButton()
    {       
        //mTPlayer.setPause(false);
        if (mTPlayer == null)
        {
            //mTPlayer.getMediaPlayer().stop();
            timeline.stop();

            playNextSong(false);
        }
        else
        {
            mTPlayer.getMediaPlayer().stop();

            timeline.stop();
            
            playNextSong(false);           
        }       
    }
    public void pressPreviousButton()
    {
        if (mTPlayer == null)
        {
            //mTPlayer.getMediaPlayer().stop();
            timeline.stop();

            playNextSong(true);
        }
        else
        {
            mTPlayer.getMediaPlayer().stop();

            timeline.stop();
            
            playNextSong(true);         
        }
    }
    
    
    private void playNextSong(boolean previous) 
    {
        playingSong = false;
        Song nextSong = null;
        try
        {
            nextSong = getNextSongInCurrentList(songPlaying,previous);
        } catch (IOException ex)
        {
            ex.printStackTrace();
        } catch (UnsupportedAudioFileException ex)
        {
           ex.printStackTrace();
        }
        
        try
        {
            ListView<Song> playlist = (ListView)currentListControl;
            //playlist.getSelectionModel().clearAndSelect(currentList.indexOf(nextSong));
            playlist.getSelectionModel().clearAndSelect(currentIndex);
            
        }
        catch(ClassCastException c)
        {
            TableView<Song> playlist = (TableView)currentListControl;
            //playlist.getSelectionModel().clearAndSelect(currentList.indexOf(nextSong));
            playlist.getSelectionModel().clearAndSelect(currentIndex);
        }
        playTheSong(nextSong);
        //runningDelay = false;      
    }
    
    /**
     * Returns the next song in the currently active list og songs
     * @param song
     * @return the next song
     */
    
    
    public Song getNextSongInCurrentList(Song currentSong, boolean previous) throws IOException, UnsupportedAudioFileException
    {
        Song nextSong;       
        
        if(songs.contains(currentSong))
        {
            currentList = songs;
            System.out.println("List: all list");
        }
        else if(songsByPlaylistId.contains(currentSong))
        {
            currentList = songsByPlaylistId;
            System.out.println("List: playlist list");
        }
        else
        {
            currentList = null;
            System.out.println("ERROR no list found");
        }        
        
        //int index = currentList.indexOf(currentSong);
        System.out.println("index:" + currentIndex);
        
        if (previous == false)
        {
            if (currentIndex != currentList.size()-1)
            {
                //System.out.println("next sonng is: " + index + 1);
                nextSong = currentList.get(currentIndex + 1);
                currentIndex = currentIndex + 1;
            }
            else
            {
                //System.out.println("nexxxt song is: " + currentList.get(0).getAllSongStringInfo());
                nextSong = currentList.get(0);
                currentIndex = 0;
            }
        }
        else
        {
            if (currentIndex != 0)
            {
                //System.out.println("next sonng is: " + index + 1);
                nextSong = currentList.get(currentIndex - 1);
                currentIndex = currentIndex - 1;
            }
            else
            {
                //System.out.println("nexxxt song is: " + currentList.get(0).getAllSongStringInfo());
                nextSong = currentList.get(0);
                currentIndex = 0;
            }
        }            
        
        return nextSong;
    }
    /*
    public Song getNextSongInCurrentList(Song song) throws IOException, UnsupportedAudioFileException
    {
        Song nextSong;       
        
        if(songs.contains(song))
        {
            currentList = songs;
            System.out.println("List: all list");
        }
        else if(songsByPlaylistId.contains(song))
        {
            currentList = songsByPlaylistId;
            System.out.println("List: playlist list");
        }
        else
        {
            currentList = null;
            System.out.println("ERROR no list found");
        }        
        
        int index = currentList.indexOf(song);
        System.out.println("index:" + index);
        
        if (index != currentList.size()-1)
        {
            //System.out.println("next sonng is: " + index + 1);
            nextSong = currentList.get(index + 1);
        }
        else
        {
            //System.out.println("nexxxt song is: " + currentList.get(0).getAllSongStringInfo());
            nextSong = currentList.get(0);
        }
        
        return nextSong;
    }
    */
    
    public void deleteSong(Song song) throws IOException
    {

            mMgr.deleteSong(song.getId());
            songs.remove(song);
    }

    public void createNewPlaylist(Playlist playlistToEdit, String playlistName) throws IOException, UnsupportedAudioFileException
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

    public void deletPlaylist(Playlist playlist) throws IOException
    {
            mMgr.deletePlaylist(playlist.getId());

            playlists.remove(playlist);
            songsByPlaylistId.clear();
           

    }

    public void showPlaylistSongs(int playlistId) throws UnsupportedAudioFileException, IOException

    {
            songsByPlaylistId.clear();
            songsByPlaylistId.addAll(mMgr.getSongsByPlaylistId(playlistId));      
    }


    public void addSongToPlaylist(Song songToAdd, Playlist playlistToAddTo) throws UnsupportedAudioFileException, IOException

    {

            //  songsByPlaylistId.add(songToAdd);

            mMgr.addSongToPlaylist(songToAdd.getId(), playlistToAddTo.getId());
            playlistToAddTo.setNumberOfSongsInPlaylist(+1);
            playlists.clear();
            playlists.addAll(mMgr.getAllPlayLists());
            showPlaylistSongs(playlistToAddTo.getId());

    }

    public List<Song> filterSongs(String query) throws IOException
    {
        List<Song> songList = null;

            songList = mMgr.search(query);


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
        System.out.println(indexId);
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

    public void editSong(Song songToEdit, File fileSong) throws IOException, UnsupportedAudioFileException
    {
        Song songSong = (Song) songs.get(songs.indexOf(songToEdit));
        songSong.setArtist(songToEdit.getArtist());
        songSong.setTitle(songToEdit.getTitle());
        if (fileSong != null)
        {
            songSong.setFilePath(fileSong.getAbsolutePath());
        }
        
            mMgr.saveEditedSong(songSong);
            songs.clear();
            songs.addAll(mMgr.getAllSongs());

        
    }

    public MyTunesPlayer getmTPlayer()
    {
        return mTPlayer;
    }

    

    
}
