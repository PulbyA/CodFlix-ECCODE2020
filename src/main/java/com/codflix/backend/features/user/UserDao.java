package com.codflix.backend.features.user;

import com.codflix.backend.core.Database;
import com.codflix.backend.models.User;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    public User getUserByCredentials(String email, String password) {
        User user = null;

        Connection connection = Database.get().getConnection();
        try {
            PreparedStatement st = connection.prepareStatement("SELECT * FROM user WHERE email=? AND password=?");

            st.setString(1, email);
            st.setString(2, password);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                user = mapToUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    private User mapToUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt(1), // id
                rs.getString(2), // email
                rs.getString(3) // password
        );
    }

    public User getUserById(int userId) {
        User user = null;

        Connection connection = Database.get().getConnection();
        try {
            PreparedStatement st = connection.prepareStatement("SELECT * FROM user WHERE id=?");

            st.setInt(1, userId);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                user = mapToUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public void addUser(String email, String password){
        password = sha256(password);
        Connection connection = Database.get().getConnection();
        try {
            PreparedStatement st = connection.prepareStatement("" +
                    "INSERT INTO user ( email, password, isVerified) VALUES " +
                    "( ?, ?, false);");

            st.setString(1, email);
            st.setString(2, password);

            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String sha256(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
