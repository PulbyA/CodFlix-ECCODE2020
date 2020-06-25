package com.codflix.backend.features.genre;

import com.codflix.backend.core.Database;
import com.codflix.backend.models.Genre;
import com.codflix.backend.models.Media;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class GenreDao {

    public List<Genre> getAllGenres() {
        List<Genre> genres = new ArrayList<>();
        Connection connection = Database.get().getConnection();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM genre");
            while (rs.next()) {
                Genre genre = new Genre(
                        rs.getInt(1),
                        rs.getString(2)
                );

                genres.add(genre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return genres;
    }

    public Genre getGenreById(int id) {
        Genre genre = null;

        Connection connection = Database.get().getConnection();
        try {
            PreparedStatement st = connection.prepareStatement("SELECT * FROM genre WHERE id=?");

            st.setInt(1, id);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                genre = mapToGenre(rs);
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }

        return genre;
    }

    private Genre mapToGenre(ResultSet rs) throws SQLException, ParseException {
        return new Genre(
                rs.getInt(1), // id
                rs.getString(2) // genre
        );
    }
}
