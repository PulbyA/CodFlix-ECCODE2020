package com.codflix.backend.models;

import com.codflix.backend.features.genre.GenreDao;
import com.codflix.backend.features.series.EpisodeDao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Media {
    private int id;
    private int genreId;
    private String title;
    private String type;
    private String status;
    private Date releaseDate;
    private String summary;
    private String trailerUrl;
    private String director;
    private int duration;

    private final GenreDao genreDao = new GenreDao();

    public Media(int id, int genreId, String title, String type, String status, Date releaseDate, String summary, String trailerUrl, String director, int duration) {
        this.id = id;
        this.genreId = genreId;
        this.title = title;
        this.type = type;
        this.status = status;
        this.releaseDate = releaseDate;
        this.summary = summary;
        this.trailerUrl = trailerUrl;
        this.director = director;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Media{" +
                "id=" + id +
                ", genreId=" + genreId +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", releaseDate=" + releaseDate +
                ", summary='" + summary + '\'' +
                ", trailerUrl='" + trailerUrl + '\'' +
                ", director='" + director + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    public String getDirector(){return director;}

    public void setDirector(String director){this.director = director;}

    public int getDuration(){return duration;}

    public void setDuration(int duration){this.duration = duration;}

    public int getDurationInMinutes(){
        return duration/60;
    }

    public String findGenreById(){
        Genre genre = genreDao.getGenreById(genreId);
        return genre.getName();
    }

    public List<Episode> getAllEpisodeFromMedia() {
        EpisodeDao episodeDao = new EpisodeDao();
        List<Episode> episodes;

        episodes = episodeDao.getAllEpisodeFromMedia(this.id);

        return episodes;
    }

    public int getFullDurationFromEpisodes (){
        duration = 0;
        EpisodeDao episodeDao = new EpisodeDao();
        List<Episode> episodes;

        episodes = episodeDao.getAllEpisodeFromMedia(this.id);
        for (Episode episode:episodes) {
            duration+=episode.getDuration();
        }

        duration = getDurationInMinutes();
        return duration;
    }

    public String formatDate(){
        String newDate = null;
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        newDate = simpleDateFormat.format(releaseDate);

        return newDate;
    }
}

