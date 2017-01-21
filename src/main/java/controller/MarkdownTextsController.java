package controller;

import database.Database;
import model.MarkdownText;
import service.JsonService;
import spark.Request;
import spark.Response;
import spark.Route;

public class MarkdownTextsController extends ModelController {

    public static Route show = (Request request, Response response) -> show(MarkdownText.class, request, response);
    public static Route update = (Request request, Response response) -> update(MarkdownText.class, request, response);
}
