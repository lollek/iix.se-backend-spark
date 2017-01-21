package controller;

import database.Database;
import model.Model;
import org.eclipse.jetty.http.HttpStatus;
import service.AccessService;
import service.JsonService;
import spark.Request;
import spark.Response;

import java.sql.SQLException;

abstract class ModelController {
    static String index(Class<?> clazz) throws SQLException {
        return JsonService.toJson(Database.index(clazz));
    }
    static String show(Class<?> clazz, Request request) throws SQLException {
        int id;
        try {
            id = Integer.parseInt(request.params("id"));
        } catch (NumberFormatException exception) {
            return null;
        }
        return JsonService.toJson(Database.show(clazz, id));
    }

    static <T> String save(Class<T> clazz, Request request, Response response) {
        if (!AccessService.isLoggedIn(request)) {
            response.status(HttpStatus.UNAUTHORIZED_401);
            return "";
        }

        T object = JsonService.fromJson(request.body(), clazz);
        if (object == null || !Database.save(clazz, object)) {
            response.status(HttpStatus.BAD_REQUEST_400);
            return "";
        }
        return JsonService.toJson(object);
    }

    static <T extends Model> String update(Class<T> clazz, Request request, Response response) {
        if (!AccessService.isLoggedIn(request)) {
            response.status(HttpStatus.UNAUTHORIZED_401);
            return "";
        }

        int id;
        try {
            id = Integer.parseInt(request.params("id"));
        } catch (NumberFormatException exception) {
            return null;
        }

        T object = JsonService.fromJson(request.body(), clazz);
        if (object == null) {
            response.status(HttpStatus.BAD_REQUEST_400);
            return "";
        }
        object.id = id;

        if (!Database.update(clazz, object)) {
            response.status(HttpStatus.BAD_REQUEST_400);
            return "";
        }
        return JsonService.toJson(object);
    }
}
