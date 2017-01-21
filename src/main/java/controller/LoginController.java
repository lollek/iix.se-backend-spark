package controller;

import com.google.gson.reflect.TypeToken;
import model.User;
import org.eclipse.jetty.http.HttpStatus;
import service.AccessService;
import service.JsonService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Map;

public class LoginController {

    public static Route checkLogin = (Request request, Response response) -> {
        String currentUser = AccessService.getUsername(request);
        if (currentUser != null) {
            User user = new User();
            user.username = currentUser;
            return JsonService.toJson(user);
        } else {
            response.status(HttpStatus.UNAUTHORIZED_401);
            return "";
        }
    };

    public static Route login = (Request request, Response response) -> {
        final Map<String, String> data = JsonService.fromJson(request.body(), new TypeToken<Map<String, String>>(){}.getType());
        final String username = data.get("username");
        final String password = data.get("password");

        if (username == null || username.isEmpty()) {
            response.status(403);
            return "";
        }

        response.status(AccessService.login(request, username, password) ? 200 : 403);
        return "";
    };

    public static Route logout = (Request request, Response response) -> {
        AccessService.logout(request);
        return "";
    };
}
