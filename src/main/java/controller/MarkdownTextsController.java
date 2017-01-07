package controller;

import Database.Database;
import com.google.gson.Gson;
import model.MarkdownText;
import spark.Request;
import spark.Response;
import spark.Route;

public class MarkdownTextsController {

    public static Route index = (Request request, Response response) ->
            new Gson().toJson(Database.index(MarkdownText.class, MarkdownText.ID_FIELD_NAME, MarkdownText.NAME_FIELD_NAME ));

    public static Route show = (Request request, Response response) -> {
        int id;
        try {
            id = Integer.parseInt(request.params("id"));
        } catch (NumberFormatException exception) {
            return null;
        }
        return new Gson().toJson(Database.show(MarkdownText.class, id));
    };
}