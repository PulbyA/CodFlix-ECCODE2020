package com.codflix.backend.features.contact;

import com.codflix.backend.core.Database;
import com.codflix.backend.models.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactDao {

    public void addContact(String sender, String content){

        Connection connection = Database.get().getConnection();
        try {
            PreparedStatement st = connection.prepareStatement("" +
                    "INSERT INTO email ( sender, receiver, content, type) VALUES " +
                    "( ?, 'contact@codflix.com', ?, 'contact');");

            st.setString(1, sender);
            st.setString(2, content);

            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
