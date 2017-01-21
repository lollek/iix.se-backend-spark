package controller;

import model.Beer;
import spark.Request;
import spark.Response;
import spark.Route;

public class BeersController extends ModelController {

    public static Route index = (Request request, Response response) -> index(Beer.class);
    public static Route show = (Request request, Response response) -> show(Beer.class, request);
}
