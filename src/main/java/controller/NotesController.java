package controller;

import database.Database;
import model.Note;
import service.JsonService;
import spark.Request;
import spark.Response;
import spark.Route;

public class NotesController {

    public static Route index = (Request request, Response response) ->
            JsonService.toJson(Database.index(Note.class));

    public static Route show = (Request request, Response response) -> {
        int id;
        try {
            id = Integer.parseInt(request.params("id"));
        } catch (NumberFormatException exception) {
            return null;
        }
        return JsonService.toJson(Database.show(Note.class, id));
    };
}
