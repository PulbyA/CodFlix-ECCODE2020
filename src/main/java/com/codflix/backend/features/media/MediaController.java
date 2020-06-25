package com.codflix.backend.features.media;

import com.codflix.backend.core.Template;
import com.codflix.backend.features.genre.GenreDao;
import com.codflix.backend.models.Genre;
import com.codflix.backend.models.Media;
import spark.Request;
import spark.Response;
import spark.Session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MediaController {
    private final MediaDao mediaDao = new MediaDao();
    private final GenreDao genreDao = new GenreDao();

    public String list(Request request, Response response) {
        List<Media> medias;

        String title = request.queryParams("title");
        String genre = request.queryParams("genre");
        String type = request.queryParams("type");
        String date = request.queryParams("datestart") + " " + request.queryParams("dateend");

        if (title != null && !title.isEmpty()) {
            medias = mediaDao.filterMedias(title, "title");
        }
        else if (genre != null && !genre.isEmpty()){
            medias = mediaDao.filterMedias(genre, "genre");
        }
        else if (type != null && !type.isEmpty()){
            medias = mediaDao.filterMedias(type, "type");
        }
        else if (date != null && !date.isEmpty() && !date.equals(" ")){
            medias = mediaDao.filterMedias(date, "date");
        }
        else {
            medias = mediaDao.getAllMedias();
        }

        List<Genre> genres = genreDao.getAllGenres();

        Map<String, Object> model = new HashMap<>();
        model.put("medias", medias);
        model.put("genres", genres);
        return Template.render("media_list.html", model);
    }

    public String detail(Request request, Response res) {
        int id = Integer.parseInt(request.params(":mediaId"));
        Media media = mediaDao.getMediaById(id);

        Map<String, Object> model = new HashMap<>();
        model.put("media", media);
        return Template.render("media_detail.html", model);
    }
}
