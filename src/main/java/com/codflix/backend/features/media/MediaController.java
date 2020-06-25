package com.codflix.backend.features.media;

import com.codflix.backend.core.Template;
import com.codflix.backend.features.genre.GenreDao;
import com.codflix.backend.features.history.HistoryDao;
import com.codflix.backend.features.user.UserDao;
import com.codflix.backend.models.Genre;
import com.codflix.backend.models.Media;
import com.codflix.backend.models.User;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.Spark;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MediaController {
    private final MediaDao mediaDao = new MediaDao();
    private final GenreDao genreDao = new GenreDao();
    private final HistoryDao historyDao = new HistoryDao();
    private final UserDao userDao = new UserDao();

    public String list(Request request, Response response) {
        List<Media> medias;

        //Get all parameters from the request (URL)
        if(!request.queryParams().isEmpty()){
            String title = request.queryParams("title") + "/ ";
            String genre = request.queryParams("genre") + "/ ";
            String type = request.queryParams("type") + "/ ";
            String date = request.queryParams("datestart") + "/ " + request.queryParams("dateend") + "/ ";
            String filters = " " + title + genre + type + date;

            medias = mediaDao.filterMedias(filters);
        }
        //If there's no parameters, get all medias
        else{
            medias = mediaDao.getAllMedias();
        }

        //Get all genre to filter medias
        List<Genre> genres = genreDao.getAllGenres();

        Map<String, Object> model = new HashMap<>();
        model.put("medias", medias);
        model.put("genres", genres);
        return Template.render("media_list.html", model);
    }

    public String detail(Request request, Response res) {
        int id = Integer.parseInt(request.params(":mediaId"));
        Media media = mediaDao.getMediaById(id);

        //Get user from session
        Session session = request.session(true);
        Integer userIdStr = session.attribute("user_id");

        //If we have a user, we had a new history in the database
        if (userIdStr != null && userIdStr != 0) {
            historyDao.addHistory(userIdStr, media.getId(), null);
            System.out.println("History addes in database");
        }

        //rooting toward media detail page
        Map<String, Object> model = new HashMap<>();
        model.put("media", media);
        return Template.render("media_detail.html", model);
    }
}
