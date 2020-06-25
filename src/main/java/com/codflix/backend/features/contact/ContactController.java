package com.codflix.backend.features.contact;

import com.codflix.backend.core.Template;
import com.codflix.backend.features.user.AuthController;
import com.codflix.backend.utils.URLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Session;

import java.util.HashMap;
import java.util.Map;

public class ContactController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final ContactDao contactDao = new ContactDao();

    /**
     * Display contact page and send data to database
     * @param request
     * @param response
     * @return a Template.render to contact.html
     */
    public String send(Request request, Response response) {

        boolean isLogged;

        //Checking if a session is active, that way we can display the contact page in different environnement
        Session session = request.session(false);
        isLogged = session == null;

        //Get Parameters from URL
        Map<String, String> query = URLUtils.decodeQuery(request.body());
        String sender = query.get("sender");
        String content = query.get("content");
        if(null != sender && null!= content) {
            //Add the mail to the database
            contactDao.addContact(sender, content);
        }

        Map<String, Object> model = new HashMap<>();
        model.put("isLogged", isLogged);
        return Template.render("contact.html", model);
    }

}
