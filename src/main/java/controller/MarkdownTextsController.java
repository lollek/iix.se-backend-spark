package controller;

import com.j256.ormlite.dao.Dao;
import database.Database;
import model.MarkdownText;
import org.eclipse.jetty.http.HttpStatus;
import service.AccessService;
import service.JsonService;
import spark.Request;
import spark.Response;
import spark.Route;

public class MarkdownTextsController extends ModelController {

    public static Route show = (Request request, Response response) -> {
        final Dao<MarkdownText, Integer> dao = Database.getDao(MarkdownText.class);
        return JsonService.toJson(dao.queryForFirst(dao.queryBuilder().where().eq("name", request.params("name")).prepare()));
    };

    public static Route update = (Request request, Response response) -> {
        if (!AccessService.isLoggedIn(request)) {
            response.status(HttpStatus.UNAUTHORIZED_401);
            return "";
        }

        final Dao<MarkdownText, Integer> dao = Database.getDao(MarkdownText.class);
        MarkdownText dbObject = dao.queryForFirst(dao.queryBuilder().where().eq("name", request.params("name")).prepare());
        if (dbObject == null) {
            response.status(HttpStatus.BAD_REQUEST_400);
            return "";
        }

        MarkdownText jsonObject = JsonService.fromJson(request.body(), MarkdownText.class);
        if (jsonObject == null) {
            response.status(HttpStatus.BAD_REQUEST_400);
            return "";
        }

        jsonObject.id = dbObject.id;
        if (!Database.update(MarkdownText.class, jsonObject)) {
            response.status(HttpStatus.BAD_REQUEST_400);
            return "";
        }

        return JsonService.toJson(jsonObject);
    };
}
