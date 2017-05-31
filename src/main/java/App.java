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

        LoginController.Companion.register("/api/login");
        BeersController.register("/api/beers");
        GamesController.Companion.register("/api/games");


        get("/api/books", BooksController.index);
        get("/api/books/:id", BooksController.show);
        post("/api/books", BooksController.save);
        put("/api/books/:id", BooksController.update);
        delete("/api/books/:id", BooksController.delete);


        get("/api/markdown/:name", MarkdownTextsController.show);
        put("/api/markdown/:name", MarkdownTextsController.update);

        get("/api/notes", NotesController.index);
        get("/api/notes/:id", NotesController.show);
        post("/api/notes", NotesController.save);
        put("/api/notes/:id", NotesController.update);
        delete("/api/notes/:id", NotesController.delete);

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

