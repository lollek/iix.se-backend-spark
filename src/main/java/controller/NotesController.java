package controller;

import model.Note;
import spark.Request;
import spark.Response;
import spark.Route;

public class NotesController extends BaseApiController {
    public static Route index = (Request request, Response response) -> index(Note.class);
    public static Route show = (Request request, Response response) -> show(Note.class, request);
    public static Route save = (Request request, Response response) -> save(Note.class, request, response);
    public static Route update = (Request request, Response response) -> update(Note.class, request, response);
}
