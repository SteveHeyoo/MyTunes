/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.BE;

/**
 *
 * @author Stefan-VpcEB3J1E
 */
public class Song
{

    private final int id;
    private String artist;
    private String title;
    private final String filePath;
    private final double duration;
   
    public Song(int id, String artist, String title, String filePath, double duration)
    {
        this.id = id;
        this.artist = artist;
        this.title = title;
        this.filePath = filePath;
        this.duration = duration;
    }

    public String getArtist()
    {
        return artist;
    }

    public void setArtist(String artist)
    {
        this.artist = artist;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public int getId()
    {
        return id;
    }

    public String getFilePath()
    {
        return filePath;
    }

  
    public double getDuration()
    {
        return duration;
    }

}
