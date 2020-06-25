package com.codflix.backend.features.series;

import com.codflix.backend.core.Template;
import com.codflix.backend.models.Episode;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EpisodeController {

    public EpisodeDao episodeDao = new EpisodeDao();

    public String list(Request request, Response response) {
        Map<Integer, List<Episode>> episodeList;

        episodeList = episodeDao.getEpisodesOrderedBySeason();

        Map<String, Object> model = new HashMap<>();
        model.put("episodeList", episodeList);
        return Template.render("episode_list.html", model);
    }

    public String detail(Request request, Response res) {
        int seasonId = Integer.parseInt(request.params(":seasonId"));
        int episodeId = Integer.parseInt(request.params(":episodeId"));
        Episode episode = episodeDao.getEpisodeBySeasonAndEpisode(seasonId, episodeId);

        Map<String, Object> model = new HashMap<>();
        model.put("episode", episode);
        return Template.render("episode_detail.html", model);
    }
}
