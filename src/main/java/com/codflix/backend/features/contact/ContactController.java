package com.codflix.backend.features.contact;

import com.codflix.backend.core.Conf;
import com.codflix.backend.core.Template;
import com.codflix.backend.features.user.AuthController;
import com.codflix.backend.features.user.UserDao;
import com.codflix.backend.models.User;
import com.codflix.backend.utils.URLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.Spark;

import java.util.HashMap;
import java.util.Map;

public class ContactController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final ContactDao contactDao = new ContactDao();

    public String send(Request request, Response response) {

        //Get Parameters
        Map<String, String> query = URLUtils.decodeQuery(request.body());
        String sender = query.get("sender");
        String content = query.get("content");
        if(null != sender && null!= content) {
            contactDao.addContact(sender, content);
        }

        Map<String, Object> model = new HashMap<>();
        return Template.render("contact.html", model);
    }

}
