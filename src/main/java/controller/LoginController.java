package controller;

import com.google.gson.reflect.TypeToken;
import service.AccessService;
import service.JsonService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Map;

public class LoginController {

    public static Route checkLogin = (Request request, Response response) -> {
        response.status(AccessService.isLoggedIn(request) ? 200 : 401);
        return "";
    };

    public static Route login = (Request request, Response response) -> {
        final Map<String, String> data = JsonService.fromJson(request.body(), new TypeToken<Map<String, String>>(){}.getType());
        final String username = data.get("username");
        final String password = data.get("password");

        if (username == null || username.isEmpty()) {
            response.status(403);
            return "";
        }

        response.status(AccessService.authenticate(request, username, password) ? 200 : 403);
        return "";
    };
}
