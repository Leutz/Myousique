package backend.controllers;

import backend.database.Database;
import backend.model.Album;
import backend.model.Artist;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AlbumsController {
    public String getTitle(int id){
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select title from albums where album_id = " + id)) {
            if(rs.next())
                return rs.getString(1);
            else return  null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Album getAlbumById(int id){
        ArtistsController ac = new ArtistsController();
        SongsController sc = new SongsController();
        Connection con = Database.getConnection();

        Album album = new Album();
        album.setId(id);
        album.setName(getTitle(id));

        List<Integer> songIds = new ArrayList<>();
        List<String> songNames = new ArrayList<>();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "select song_id from contain where album_id = " + id)) {
            while(rs.next()){
                songIds.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for(int i:songIds)
        {
            songNames.add(sc.getTitle(i));
        }
        album.setSongIds(songIds);
        album.setSongNames(songNames);

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "select artist_id from sangby where song_id = " + songIds.get(1))) {
            while(rs.next()){
                album.setArtistId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        album.setArtistName(ac.getName(album.getArtistId()));


        return album;
    }
}
