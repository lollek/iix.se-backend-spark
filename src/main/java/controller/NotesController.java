package controller;

import Database.Database;
import com.google.gson.Gson;

import model.Note;
import spark.*;

public class NotesController {

    public static Route index = (Request request, Response response) ->
            new Gson().toJson(Database.index(Note.class));

    public static Route show = (Request request, Response response) -> {
        int id;
        try {
            id = Integer.parseInt(request.params("id"));
        } catch (NumberFormatException exception) {
            return null;
        }
        return new Gson().toJson(Database.show(Note.class, id));
    };
}
