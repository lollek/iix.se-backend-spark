package controller;

import database.Database;
import com.google.gson.Gson;
import model.Book;
import spark.Request;
import spark.Response;
import spark.Route;

public class BooksController {

    public static Route index = (Request request, Response response) ->
            new Gson().toJson(Database.index(Book.class));

    public static Route show = (Request request, Response response) -> {
        int id;
        try {
            id = Integer.parseInt(request.params("id"));
        } catch (NumberFormatException exception) {
            return null;
        }
        return new Gson().toJson(Database.show(Book.class, id));
    };
}
