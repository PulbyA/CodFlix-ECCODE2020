package com.codflix.backend.features.series;

import com.codflix.backend.core.Template;
import com.codflix.backend.features.media.MediaDao;
import com.codflix.backend.models.Episode;
import com.codflix.backend.models.Media;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EpisodeController {

    public EpisodeDao episodeDao = new EpisodeDao();
    public MediaDao mediaDao = new MediaDao();

    /**
     * Display a liste of all episodes, ordered by season
     * @param request
     * @param response
     * @return
     */
    public String list(Request request, Response response) {
        Map<Integer, List<Episode>> episodeList;
        List<Episode> episodes;

        String season = request.queryParams("season");
        episodeList = episodeDao.getEpisodesOrderedBySeason();
        Media media = mediaDao.getMediaById(episodeList.get(1).get(0).getMediaId());

        if(season != null && !season.isEmpty()){
            episodes = episodeDao.getEpisodeBySeason(Integer.parseInt(season));
        }
        else {
            episodes = episodeDao.getEpisodeBySeason(1);
        }


        Map<String, Object> model = new HashMap<>();
        model.put("episodes", episodes );
        model.put("media", media);
        model.put("episodeList", episodeList);
        return Template.render("episode_list.html", model);
    }

    /**
     * Diplay the detail of an episode
     * @param request
     * @param res
     * @return
     */
    public String detail(Request request, Response res) {
        int seasonId = Integer.parseInt(request.params(":seasonId"));
        int episodeId = Integer.parseInt(request.params(":episodeId"));
        Episode episode = episodeDao.getEpisodeBySeasonAndEpisode(seasonId, episodeId);

        Media media = mediaDao.getMediaById(episode.getMediaId());
        Map<String, Object> model = new HashMap<>();
        model.put("episode", episode);
        model.put("media", media);
        return Template.render("episode_detail.html", model);
    }
}
