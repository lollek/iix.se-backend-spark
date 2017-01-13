package controller;

import spark.Request;
import spark.Response;
import spark.Route;

public class LoginController {

    public static Route login = (Request request, Response response) -> {
        if (request.session().attribute("username")) {
            response.status(200);
            return "";
        }

        response.status(401);
        return "";
    };
}
