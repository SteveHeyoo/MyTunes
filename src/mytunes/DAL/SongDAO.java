/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.DAL;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import mytunes.BE.Song;

/**
 *
 * @author Stefan-VpcEB3J1E
 */
public class SongDAO 
{
    private static final int ID_SIZE = Integer.BYTES;
    private static final int NAME_SIZE = 50;
 
    private final String fileName;

    public SongDAO(String fileName)
    {
        this.fileName = fileName;
    }
    
    public void addSong(Song s) throws IOException
    {
        try (RandomAccessFile raf = new RandomAccessFile(new File(fileName), "rw"))
        {
            raf.seek(raf.length());  // place the file pointer at the end of the file.
            raf.writeInt(s.getId());
        //    raf.writeBytes(String.format("%-" + NAME_SIZE + "s", s.getName()).substring(0, NAME_SIZE));
        }
    }
}
