package backend.controllers;

import backend.database.Database;
import backend.model.User;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class FriendsController {

    public List<User> getFriends(int id) throws SQLException {
        UserController uc = new UserController();
        List<User> users = new ArrayList<>();
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select user_id1 from friends where user_id2 = " + id)) {
            while(rs.next()){
                users.add(uc.findById(rs.getInt(1)));
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select user_id2 from friends where user_id1 = " + id)) {
            while(rs.next()){
                users.add(uc.findById(rs.getInt(1)));
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
