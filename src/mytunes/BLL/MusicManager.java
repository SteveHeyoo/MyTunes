/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.BLL;

import java.io.File;
import java.io.IOException;
import mytunes.BE.Song;
import mytunes.DAL.SongDAO;

/**
 *
 * @author Stefan-VpcEB3J1E
 */
public class MusicManager 
{
    private static final String FILE_NAME = "Departments.dat";

    private SongDAO songDAO;

    public MusicManager()
    {
        songDAO = new SongDAO(FILE_NAME);
    }
    
    public Song addSong(File file) throws IOException
    {
        return songDAO.addSong(file);
    }
}
