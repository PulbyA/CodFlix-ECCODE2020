package com.codflix.backend.features.series;

import com.codflix.backend.core.Database;
import com.codflix.backend.models.Episode;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EpisodeDao {

    public List<Episode> getAllEpisode() {
        List<Episode> episode = new ArrayList<>();

        Connection connection = Database.get().getConnection();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM episode");
            while (rs.next()) {
                episode.add(mapToEpisode(rs));
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }

        return episode;
    }

    public Episode getEpisodeById(int id) {
        Episode episode = null;

        Connection connection = Database.get().getConnection();
        try {
            PreparedStatement st = connection.prepareStatement("SELECT * FROM episode WHERE id=?");

            st.setInt(1, id);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                episode = mapToEpisode(rs);
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }

        return episode;
    }

    public List<Episode> getAllEpisodeFromMedia(int id) {
        List<Episode> episode = new ArrayList<>();

        Connection connection = Database.get().getConnection();
        try {
            PreparedStatement st = connection.prepareStatement("SELECT * FROM episode WHERE media_id = ? ");
            st.setInt( 1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                episode.add(mapToEpisode(rs));
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }

        return episode;
    }

    public Episode getEpisodeBySeasonAndEpisode(int seasonId, int episodeId){
        Episode episode = null;

        Connection connection = Database.get().getConnection();
        try {
            PreparedStatement st = connection.prepareStatement("SELECT * FROM episode WHERE season=? AND episode=?");

            st.setInt(1, seasonId);
            st.setInt(2, episodeId);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                episode = mapToEpisode(rs);
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }

        return episode;
    }

    public Map<Integer, List<Episode>> getEpisodesOrderedBySeason(){
        HashMap<Integer, List<Episode>> episodesList = new HashMap<Integer, List<Episode>>();
        Connection connection = Database.get().getConnection();
        try {
            PreparedStatement st = connection.prepareStatement("SELECT * FROM episode ORDER BY episode ASC, season DESC");

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                if (!episodesList.containsKey(mapToEpisode(rs).getSeason())) {
                    episodesList.put(mapToEpisode(rs).getSeason(), new ArrayList<>());
                }
                episodesList.get(mapToEpisode(rs).getSeason()).add(mapToEpisode(rs));
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }

        return episodesList;
    }

    private Episode mapToEpisode(ResultSet rs) throws SQLException, ParseException {
        return new Episode(
                rs.getInt(1), // id
                rs.getInt(2), // mediaseriesId
                rs.getInt(3), // season
                rs.getInt(4), // episode
                rs.getString(5), // episode_url
                rs.getString(6), // title
                rs.getString(7), // summary
                rs.getDate(8), // release_date
                rs.getInt(9) // duration
        );
    }
}