package com.codflix.backend.models;

import com.codflix.backend.features.media.MediaDao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Episode {
    private int id;
    private int mediaId;
    private int season;
    private int episode;
    private String episodeUrl;
    private String title;
    private String summary;
    private Date releaseDate;
    private int duration;

    private final MediaDao mediaDao = new MediaDao();

    public Episode(int id, int mediaId, int season, int episode, String episodeUrl, String title, String summary, Date releaseDate, int duration) {
        this.id = id;
        this.mediaId = mediaId;
        this.season = season;
        this.episode = episode;
        this.episodeUrl = episodeUrl;
        this.title = title;
        this.summary = summary;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMediaId() {
        return mediaId;
    }

    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getEpisode() {
        return episode;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public String getEpisodeUrl() {
        return episodeUrl;
    }

    public void setEpisodeUrl(String episodeUrl) {
        this.episodeUrl = episodeUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDurationInMinutes(){return duration/60;}

    public String formatDate(){
        String newDate = null;
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        newDate = simpleDateFormat.format(releaseDate);

        return newDate;
    }

    public Media getMedia(){ return mediaDao.getMediaWithEpisodeId(id); }
}
