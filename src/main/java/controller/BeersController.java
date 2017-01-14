package controller;

import com.google.gson.Gson;
import database.Database;
import model.Beer;
import spark.Request;
import spark.Response;
import spark.Route;

public class BeersController {

    public static Route index = (Request request, Response response) ->
            new Gson().toJson(Database.index(Beer.class));

    public static Route show = (Request request, Response response) -> {
        int id;
        try {
            id = Integer.parseInt(request.params("id"));
        } catch (NumberFormatException exception) {
            return null;
        }
        return new Gson().toJson(Database.show(Beer.class, id));
    };
}
