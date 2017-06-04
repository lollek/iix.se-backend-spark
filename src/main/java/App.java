import controller.*;
import database.Database;
import exceptions.UnauthorizedException;
import org.eclipse.jetty.http.HttpStatus;
import service.LogService;

import java.sql.SQLException;

import static spark.Spark.*;

public class App {

    public static void main(String[] args) throws SQLException {
        Database.init();
        ipAddress(System.getProperty("ip"));
        port(8002);

        BeersController.Companion.register("/api/beers");
        BooksController.Companion.register("/api/books");
        GamesController.Companion.register("/api/games");
        LoginController.Companion.register("/api/login");
        MarkdownTextsController.Companion.register("/api/markdown");
        NotesController.Companion.register("api/notes");

        get("*", ((request, response) -> {
            response.status(404);
            return "";
        }));

        notFound("");
        internalServerError("");

        after((request, response) -> response.type("application/json"));
        after(LogService::logAccess);

        exception(UnauthorizedException.class, ((exception, request, response) -> response.status(HttpStatus.UNAUTHORIZED_401)));
        exception(Exception.class, (exception, request, response) -> {
            LogService.logAccess(request, response);
            LogService.logException(exception);
        });

    }
}

