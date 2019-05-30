package backend.controllers;

import backend.database.Database;
import backend.model.Artist;
import backend.model.Song;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ArtistsController {
    public String getName(int id){
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select name from artists where artist_id = " + id)) {
            if(rs.next())
                return rs.getString(1);
            else return  null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Artist getArtistById(int id){
        ArtistsController ac = new ArtistsController();
        AlbumsController alc = new AlbumsController();

        Connection con = Database.getConnection();

        Artist artist = new Artist();

        artist.setId(id);
        artist.setName(getName(id));

        List<Integer> albumIds = new ArrayList<>();
        List<String> albumNames = new ArrayList<>();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "select distinct c.album_id from sangby s natural join contain c where s.artist_id = " + id)) {
            while(rs.next()){
                albumIds.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for(int i:albumIds)
        {
            albumNames.add(alc.getTitle(i));
        }
        artist.setAlbumIds(albumIds);
        artist.setAlbumNames(albumNames);

        return artist;
    }
}
