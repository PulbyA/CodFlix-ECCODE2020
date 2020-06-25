package com.codflix.backend.features.media;

import com.codflix.backend.core.Database;
import com.codflix.backend.models.Media;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MediaDao {
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    /**
     *
     * @return a list of all media from database
     */
    public List<Media> getAllMedias() {
        List<Media> medias = new ArrayList<>();

        Connection connection = Database.get().getConnection();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM media ORDER BY release_date DESC");
            while (rs.next()) {
                medias.add(mapToMedia(rs));
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }

        return medias;
    }

    /**
     *
     * @param filter
     * @return a list of medias filtered
     */
    public List<Media> filterMedias(String filter) {
        List<Media> medias = new ArrayList<>();

        List<Integer> addedFilter = new ArrayList<>();

        Connection connection = Database.get().getConnection();
        try {
            PreparedStatement st;

            //The query will be done dynamically to enable multiple filters
            String query = "SELECT * FROM media";

            String[] filters = (filter.split("/"));

            //Creation of the query
            if(filters.length != 0){

                if(!filters[0].equals(" ") || !filters[1].equals(" ") || !filters[2].equals(" ") || !filters[3].equals(" ") || !filters[4].equals(" ")){
                    query += " WHERE ";
                }


                if(null != filters[0] && !filters[0].isEmpty() && !filters[0].equals(" ")){
                    query+= " title LIKE CONCAT('%', ? ,'%' ) AND";
                    addedFilter.add(0);
                }
                if(null != filters[1] && !filters[1].isEmpty() && !filters[1].equals(" ")){
                    query+= " genre_id = (SELECT id FROM genre WHERE name = ?) AND";
                    addedFilter.add(1);
                }
                if(null != filters[2] && !filters[2].isEmpty() && !filters[2].equals(" ")){
                    query+= " type = ? AND";
                    addedFilter.add(2);
                }
                if((null != filters[3] && !filters[3].isEmpty() && !filters[3].equals(" "))
                        || (null != filters[4] && !filters[4].isEmpty() && !filters[4].equals(" "))){
                    query+= " release_date BETWEEN ? AND ? AND";
                    addedFilter.add(3);
                    addedFilter.add(4);

                }
            }

            //Delete of the last "AND" to be able to execute the Query
            if(query.endsWith("AND")){
                query = query.substring(0, query.length() - 3);
            }
            query += " ORDER BY release_date DESC;";

            st = connection.prepareStatement(
                    query);

            //Set the different variables in the query
            for (int i = 0; i < addedFilter.size() ; i++) {
                st.setString(i+1, filters[addedFilter.get(i)].replaceAll(" ", ""));
            }

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                medias.add(mapToMedia(rs));
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }

        return medias;
    }

    /**
     *
     * @param id
     * @return a single media with its id
     */
    public Media getMediaById(int id) {
        Media media = null;

        Connection connection = Database.get().getConnection();
        try {
            PreparedStatement st = connection.prepareStatement("SELECT * FROM media WHERE id=?");

            st.setInt(1, id);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                media = mapToMedia(rs);
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }

        return media;
    }

    /**
     *
     * @param id
     * @return a single media with an episode Id
     */
    public Media getMediaWithEpisodeId(int id){
        Media media = null;

        Connection connection = Database.get().getConnection();
        try {
            PreparedStatement st = connection.prepareStatement("SELECt * FROM media WHERE media.id = (SELECT media_id FROM episode WHERE id = ?);");

            st.setInt(1, id);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                media = mapToMedia(rs);
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }

        return media;
    }

    private Media mapToMedia(ResultSet rs) throws SQLException, ParseException {
        return new Media(
                rs.getInt(1), // id
                rs.getInt(2), // genre_id
                rs.getString(3), // title
                rs.getString(4), // type
                rs.getString(5), // status
                DATE_FORMAT.parse(rs.getString(6)), // release_date
                rs.getString(7), // summary
                rs.getString(8), // trailer_url
                rs.getString(9), // Director
                rs.getInt(10) // Duration
        );
    }
}
