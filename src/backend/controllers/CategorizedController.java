package backend.controllers;

import backend.database.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CategorizedController {
    public int getSongGenre(int song_id){
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select genre_id from categorized where song_id = " + song_id)) {
            if(rs.next())
                return rs.getInt(1);
            else return -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

}
