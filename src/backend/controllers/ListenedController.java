package backend.controllers;

import backend.database.Database;
import backend.model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ListenedController {
    public List<Integer> topListened(int user_id, int top){
        List<Integer> toplist = new ArrayList<>();
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select song_id from (select song_id from listened where user_id = " + user_id + " order by nolistenes desc) where rownum<=" + top)) {

            while(rs.next()){
                toplist.add(rs.getInt(1));
            }
            return toplist;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toplist;
    }

    public List<Integer> topListenedByGenre(int user_id, int top, int genre_id){
        List<Integer> toplist = new ArrayList<>();
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select song_id from (select listened.song_id from listened JOIN categorized on listened.song_id = categorized.song_id where user_id = " + user_id + " and genre_id = " + genre_id + " order by nolistenes desc) where rownum<=" + top)) {
            while(rs.next()){
                toplist.add(rs.getInt(1));
            }
            return toplist;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toplist;
    }

    public int getSongFreqForUser(int user_id, int song_id){
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select nolistenes from listened where song_id = " + song_id + " and user_id = " + user_id)) {
            if(rs.next())
                return rs.getInt(1);
            else return 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
