package controller;

import database.Database;
import model.Note;
import service.JsonService;
import spark.Request;
import spark.Response;
import spark.Route;

public class NotesController extends ModelController {
    public static Route index = (Request request, Response response) ->
            JsonService.toJson(Database.index(Note.class, Note.ID_FIELD_NAME, Note.TITLE_FIELD_NAME, Note.DATE_FIELD_NAME ));
    public static Route show = (Request request, Response response) -> show(Note.class, request, response);
    public static Route save = (Request request, Response response) -> save(Note.class, request, response);
    public static Route update = (Request request, Response response) -> update(Note.class, request, response);
    public static Route delete = (Request request, Response response) -> delete(Note.class, request, response);
}
