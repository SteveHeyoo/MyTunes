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
    private static final int DURATION_SIZE = Double.BYTES;
    private static final int NAME_SIZE = 50;
    private static final int FILEPATH_SIZE = 200;
    private static final int WRITE_SIZE = ID_SIZE + DURATION_SIZE + (NAME_SIZE*2) + FILEPATH_SIZE;
            
    
 
    private final String fileName;

    public SongDAO(String fileName)
    {
        this.fileName = fileName;
    }
    
    public void addSong(Song s) throws IOException
    {
        try (RandomAccessFile raf = new RandomAccessFile(new File(fileName), "rw"))
        {
            int nextId = raf.readInt();
            raf.seek(raf.length()-raf.length());
            raf.writeInt(nextId+1);
            raf.seek(raf.length());  // place the file pointer at the end of the file.
            raf.writeInt(nextId);
            raf.writeDouble(s.getDuration());    
            raf.writeBytes(String.format("%-" + NAME_SIZE + "s", s.getArtist()).substring(0, NAME_SIZE));
            raf.writeBytes(String.format("%-" + NAME_SIZE + "s", s.getTitle()).substring(0, NAME_SIZE));
            raf.writeBytes(String.format("%-" + FILEPATH_SIZE + "s", s.getFilePath()).substring(0, FILEPATH_SIZE));
        }
    }
}
