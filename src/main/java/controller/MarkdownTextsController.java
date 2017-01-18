package controller;

import database.Database;
import model.MarkdownText;
import service.JsonService;
import spark.Request;
import spark.Response;
import spark.Route;

public class MarkdownTextsController {

    public static Route index = (Request request, Response response) ->
            JsonService.toJson(Database.index(MarkdownText.class, MarkdownText.ID_FIELD_NAME, MarkdownText.NAME_FIELD_NAME ));

    public static Route show = (Request request, Response response) -> {
        int id;
        try {
            id = Integer.parseInt(request.params("id"));
        } catch (NumberFormatException exception) {
            return null;
        }
        return JsonService.toJson(Database.show(MarkdownText.class, id));
    };
}
