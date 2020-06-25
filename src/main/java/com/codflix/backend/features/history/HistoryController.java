package com.codflix.backend.features.history;

import com.codflix.backend.core.Template;
import com.codflix.backend.features.user.UserDao;
import com.codflix.backend.models.History;
import com.codflix.backend.models.User;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.Spark;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryController {
    private final HistoryDao historyDao = new HistoryDao();
    private final UserDao userDao = new UserDao();


    public String list(Request request, Response res) {
        List<History> histories;

        Session session = request.session(true);
        String userIdStr = session.attribute("user_id");
        if (userIdStr == null || userIdStr.isEmpty()) {
            Spark.halt(401, "No user id provded to see history");
        }
        int userId = Integer.parseInt(userIdStr);

        User user = userDao.getUserById(userId);
        histories = historyDao.getStreamsHistoryForUser(userId);

        Map<String, Object> model = new HashMap<>();
        model.put("user", user);
        model.put("histories", histories);
        return Template.render("history_list.html", model);
    }
}
