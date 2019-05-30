package backend.controllers;

import backend.database.Database;
import backend.model.User;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class CredentialsController {

    public int login(String email, String password) throws SQLException {
        Connection con = Database.getConnection();

        String f_name;
        String l_name;

        String command = "{? = call USER_EXISTS(?,?)}";
        CallableStatement cstmt = con.prepareCall(command);

        cstmt.setString(2, email);
        cstmt.setString(3, password);
        cstmt.registerOutParameter(1, Types.NUMERIC);


        cstmt.executeUpdate();

        int result = cstmt.getInt(1);
        return result;
    }

    public boolean register(String firstName, String lastName, String email, String password)
            throws SQLException
    {
        Connection con = Database.getConnection();

        CallableStatement cstmt = con.prepareCall("{call REGISTER(?, ?, ?, ?, ?)}");
        cstmt.setString(1, firstName);
        cstmt.setString(2, lastName);
        cstmt.setString(3, email);
        cstmt.setString(4, password);
        cstmt.registerOutParameter(5, Types.NUMERIC);

        cstmt.executeUpdate();
        Database.commit();

        return cstmt.getInt(5) == 1 ? true : false;
    }
}
