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
    private static Integer getId(Request request) {
        try {
            return Integer.parseInt(request.params("id"));
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    static String index(Class<?> clazz) throws SQLException {
        return JsonService.toJson(Database.index(clazz));
    }

    static String show(Class<?> clazz, Request request, Response response) throws SQLException {
        Integer id = getId(request);
        if (id == null) {
            response.status(HttpStatus.BAD_REQUEST_400);
            return "";
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

        Integer id = getId(request);
        if (id == null) {
            response.status(HttpStatus.BAD_REQUEST_400);
            return "";
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

    static <T> String delete(Class<T> clazz, Request request, Response response) {
        if (!AccessService.isLoggedIn(request)) {
            response.status(HttpStatus.UNAUTHORIZED_401);
            return "";
        }

        Integer id = getId(request);
        if (id == null) {
            response.status(HttpStatus.BAD_REQUEST_400);
            return "";
        }

        if (!Database.delete(clazz, id)) {
            response.status(HttpStatus.BAD_REQUEST_400);
            return "";
        }

        response.status(HttpStatus.NO_CONTENT_204);
        return "";
    }
}
