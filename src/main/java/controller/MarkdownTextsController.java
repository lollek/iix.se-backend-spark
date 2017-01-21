package controller;

import database.Database;
import model.MarkdownText;
import service.JsonService;
import spark.Request;
import spark.Response;
import spark.Route;

public class MarkdownTextsController extends ModelController {

    public static Route index = (Request request, Response response) ->
            JsonService.toJson(Database.index(MarkdownText.class, MarkdownText.ID_FIELD_NAME, MarkdownText.NAME_FIELD_NAME ));

    public static Route show = (Request request, Response response) -> show(MarkdownText.class, request);
}
