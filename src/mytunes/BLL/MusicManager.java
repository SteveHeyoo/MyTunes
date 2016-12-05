/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.BLL;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.UnsupportedAudioFileException;
import mytunes.BE.Playlist;
import mytunes.BE.Song;
import mytunes.DAL.RelationsDAO;
import mytunes.DAL.SongsPlaylistsDAO;

/**
 *
 * @author Stefan-VpcEB3J1E
 */
public class MusicManager
{

    private static final String FILE_NAME = "Songs.dat";

    private SongsPlaylistsDAO sPlDAO;
    private RelationsDAO rDAO;

    public MusicManager()
    {
        sPlDAO = new SongsPlaylistsDAO();
        rDAO = new RelationsDAO();
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
     *
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
        List<Playlist> playlists = sPlDAO.getAllPlayLists();
        for (Playlist playlist : playlists)
        {
            playlist.setNumberOfSongsInPlaylist(getSongsByPlaylistId(playlist.getId()).size());
        }
        return playlists;
    }

    /**
     * Calls the removePlaylistById in the songDAO class
     *
     * @param id
     * @throws IOException
     */
    public void deletePlaylist(int id) throws IOException
    {
        sPlDAO.removePlayListById(id);
    }

    public List<Song> getSongsByPlaylistId(int playlistId) throws IOException
    {
        List<Song> returnList = new ArrayList<>();
        List<Song> allSongs = sPlDAO.getAllSongs();
        List<Integer> songsWithPlaylistId = rDAO.getSongIdByPlaylistId(playlistId);

        for (Integer songId : songsWithPlaylistId)
        {
            for (Song song : allSongs)
            {
                int readSongId = song.getId();

                if (readSongId == songId)
                {

                    returnList.add(song);
                }

            }

        }

        return returnList;

    }

    public void addSongToPlaylist(int songId, int playlistId) throws IOException
    {
        rDAO.addSongToPlaylist(songId, playlistId);

    }

    public List<Song> search(String query) throws FileNotFoundException, IOException
    {
        List<Song> allWords = getAllSongs();
        List<Song> searchList = new ArrayList<>();

        for (int i = 0; i < allWords.size(); i++)
        {
            if (allWords.get(i).getAllSongStringInfo().toLowerCase().contains(query.toLowerCase()))
            {
                searchList.add(allWords.get(i));
            }
        }

        return searchList;
    }

    public void editPlaylistName(Playlist playlistToEdit) throws IOException
    {
        sPlDAO.editPlaylistName(playlistToEdit);
    }


    public void saveEditedSong(Song songSong) throws IOException, UnsupportedAudioFileException
    {
        sPlDAO.editSong(songSong);
    }

}
