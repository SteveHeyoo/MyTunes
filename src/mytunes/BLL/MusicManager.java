/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.BLL;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.sound.sampled.UnsupportedAudioFileException;
import mytunes.BE.Playlist;
import mytunes.BE.Song;
import mytunes.DAL.SongsPlaylistsDAO;

/**
 *
 * @author Stefan-VpcEB3J1E
 */
public class MusicManager
{

    private static final String FILE_NAME = "Songs.dat";

    private SongsPlaylistsDAO sPlDAO;

    public MusicManager()
    {
        sPlDAO = new SongsPlaylistsDAO();
    }

    public Song addSong(File file) throws IOException, UnsupportedAudioFileException
    {
        return sPlDAO.addSong(file);
    }

    public List<Song> getAllSongs() throws IOException
    {
        return sPlDAO.getAllSongs();
    }
    
    /**
     * Calls the removeSongById in the songDAO class
     * @param id
     * @throws IOException 
     */
    public void deleteSong(int id) throws IOException
    {
        sPlDAO.removeSongById(id);
    }

    public Playlist createNewPlaylist(String playlistName) throws IOException
    {
        return sPlDAO.createNewPlaylist(playlistName);
    }
    
    
    public List<Playlist> getAllPlayLists() throws IOException
    {
        return sPlDAO.getAllPlayLists();
    }
    /**
     * Calls the removePlaylistById in the songDAO class
     * @param id
     * @throws IOException 
     */
    public void deletePlaylist(int id) throws IOException
    {
        sPlDAO.removePlayListById(id);
    }
}
