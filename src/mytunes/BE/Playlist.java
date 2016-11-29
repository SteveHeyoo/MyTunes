/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.BE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Stefan-VpcEB3J1E
 */
public class Playlist
{
   private final int id;
   private String name;
   private String playlistDuration;
   private List<Integer> songsRelations;

    public Playlist(int id, String name)
    {
        this.id = id;
        this.name = name;
        songsRelations = new ArrayList<>();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPlaylistDuration()
    {
        return playlistDuration;
    }

    public void setPlaylistDuration(String playlistDuration)
    {
        this.playlistDuration = playlistDuration;
    }

    public List<Integer> getSongsRelations()
    {
        return songsRelations;
    }

    public void setSongsRelations(List<Integer> songsRelations)
    {
        this.songsRelations = songsRelations;
    }
   
    
}
