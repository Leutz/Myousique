package backend.controllers;

import backend.database.Database;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SearchController {
    public List<Integer> searchSongs(String query) throws SQLException {
        Connection con = Database.getConnection();

        List<Integer> songs = new ArrayList<>();

        String command = "begin ? := searchSongs(?); end;";
        CallableStatement cstmt = con.prepareCall(command);

        cstmt.setString(2, query);
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

    public List<Integer> searchArtist(String query) throws SQLException {
        Connection con = Database.getConnection();

        List<Integer> artists = new ArrayList<>();

        String command = "begin ? := searchArtists(?); end;";
        CallableStatement cstmt = con.prepareCall(command);

        cstmt.setString(2, query);
        cstmt.registerOutParameter(1, Types.ARRAY, "VARR");

        cstmt.execute();

        Array arr = cstmt.getArray(1);
        if (arr != null) {
            BigDecimal[] data = (BigDecimal[]) arr.getArray();
            for (int i = 0; i < data.length; i++) {
                artists.add(data[i].intValueExact());
            }
        }

        return artists;
    }

    public List<Integer> searchAlbums(String query) throws SQLException {
        Connection con = Database.getConnection();

        List<Integer> albums = new ArrayList<>();

        String command = "begin ? := searchAlbums(?); end;";
        CallableStatement cstmt = con.prepareCall(command);

        cstmt.setString(2, query);
        cstmt.registerOutParameter(1, Types.ARRAY, "VARR");

        cstmt.execute();

        Array arr = cstmt.getArray(1);
        if (arr != null) {
            BigDecimal[] data = (BigDecimal[]) arr.getArray();
            for (int i = 0; i < data.length; i++) {
                albums.add(data[i].intValueExact());
            }
        }

        return albums;
    }

    public List<Integer> searchUsers(String query) throws SQLException {
        Connection con = Database.getConnection();

        List<Integer> users = new ArrayList<>();

        String command = "begin ? := searchUsers(?); end;";
        CallableStatement cstmt = con.prepareCall(command);

        cstmt.setString(2, query);
        cstmt.registerOutParameter(1, Types.ARRAY, "VARR");

        cstmt.execute();

        Array arr = cstmt.getArray(1);
        if (arr != null) {
            BigDecimal[] data = (BigDecimal[]) arr.getArray();
            for (int i = 0; i < data.length; i++) {
                users.add(data[i].intValueExact());
            }
        }

        return users;
    }




}
