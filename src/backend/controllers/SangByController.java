package backend.controllers;

import backend.database.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SangByController {
    public List<Integer> getSongsByArtist(int artist_id){
        List<Integer> songs = new ArrayList<>();
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select song_id from sangby where artist_id = " + artist_id)) {
            while(rs.next()){
                songs.add(rs.getInt(1));
            }
            return songs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return songs;
    }

    public int getSongArtist(int song_id){
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select artist_id from sangby where song_id = " + song_id)) {
            if(rs.next())
                return rs.getInt(1);
            else return -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
