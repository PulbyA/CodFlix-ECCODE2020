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

        String title = request.queryParams("title") + "/ ";
        String genre = request.queryParams("genre") + "/ ";
        String type = request.queryParams("type") + "/ ";
        String date = request.queryParams("datestart") + "/ " + request.queryParams("dateend") + "/ ";
        String filters = " " + title + genre + type + date;

        medias = mediaDao.filterMedias(filters);

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
