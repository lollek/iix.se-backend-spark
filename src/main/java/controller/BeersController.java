package controller;

import model.Beer;
import spark.Request;
import spark.Response;
import spark.Route;

public class BeersController extends ModelController {

    public static Route index = (Request request, Response response) -> index(Beer.class);
    public static Route show = (Request request, Response response) -> show(Beer.class, request, response);
    public static Route save = (Request request, Response response) -> save(Beer.class, request, response);
    public static Route update = (Request request, Response response) -> update(Beer.class, request, response);
    public static Route delete = (Request request, Response response) -> delete(Beer.class, request, response);
}
