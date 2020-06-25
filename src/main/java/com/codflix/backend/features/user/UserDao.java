package com.codflix.backend.features.user;

import com.codflix.backend.core.Database;
import com.codflix.backend.models.User;

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
}
