package controller;

import model.Beer;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

public class BeersController extends ModelController {

    public static void register(
            @SuppressWarnings("SameParameterValue")
            final String endpointUrl
    ) {
        Spark.get(endpointUrl, BeersController.index);
        Spark.get(endpointUrl + "/:id", BeersController.show);
        Spark.post(endpointUrl, BeersController.save);
        Spark.put(endpointUrl + "/:id", BeersController.update);
        Spark.delete(endpointUrl + "/:id", BeersController.delete);
    }

    private static Route index = (Request request, Response response) -> index(Beer.class);
    private static Route show = (Request request, Response response) -> show(Beer.class, request, response);
    private static Route save = (Request request, Response response) -> save(Beer.class, request, response);
    private static Route update = (Request request, Response response) -> update(Beer.class, request, response);
    private static Route delete = (Request request, Response response) -> delete(Beer.class, request, response);
}
