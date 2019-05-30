package backend.controllers;

import backend.database.Database;
import backend.model.Song;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SongsController {
    public String getTitle(int id){
        Connection con = Database.getConnection();

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select title from songs where song_id = " + id)) {
            if(rs.next())
                return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Song getSongById(int id){
        ArtistsController ac = new ArtistsController();
        AlbumsController alc = new AlbumsController();

        Connection con = Database.getConnection();

        Song song = new Song();

        song.setId(id);
        song.setTitle(getTitle(id));

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select artist_id from sangby where song_id = " + id)) {
            if(rs.next())
                song.setArtistId(rs.getInt(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        song.setArtistName(ac.getName(song.getArtistId()));

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select album_id from contain where song_id = " + id)) {
            if(rs.next())
                song.setAlbumId(rs.getInt(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        song.setAlbumName(alc.getTitle(song.getAlbumId()));


        return song;
    }

    public List<Integer> mostListened(int page, int per_page) throws SQLException {
        List<Integer> songs = new ArrayList<>();
        Connection con = Database.getConnection();

        String command = "begin ? := MOSTLISTENED(?, ?); end;";
        CallableStatement cstmt = con.prepareCall(command);

        cstmt.setInt(2, page);
        cstmt.setInt(3, per_page);
        cstmt.registerOutParameter(1, Types.ARRAY, "VARR");

        cstmt.execute();

        Array arr = cstmt.getArray(1);
        if (arr != null) {
            BigDecimal[] data = (BigDecimal[]) arr.getArray();
            for (int i = 0; i < data.length; i++) {
                songs.add(data[i].intValueExact());
            }
        }

        return songs;
    }
}
