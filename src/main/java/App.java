import controller.*;
import database.Database;
import exceptions.UnauthorizedException;
import org.eclipse.jetty.http.HttpStatus;
import service.LogService;

import java.sql.SQLException;

import static spark.Spark.*;

public class App {

    private static boolean isDevelop() {
        String develop = System.getProperty("develop");
        return develop != null && develop.toLowerCase().equals("true");
    }

    public static void main(String[] args) throws SQLException {
        boolean develop = App.isDevelop();

        // Init
        Database.init();
        ipAddress("127.0.0.1");
        port(8001);
        if (develop) {
            staticFiles.externalLocation(System.getProperty("staticFolder"));
        } else {
            staticFiles.location("/public");
        }

        LoginController.Companion.register("/api/login");
        BeersController.register("/api/beers");


        get("/api/books", BooksController.index);
        get("/api/books/:id", BooksController.show);
        post("/api/books", BooksController.save);
        put("/api/books/:id", BooksController.update);
        delete("/api/books/:id", BooksController.delete);

        get("/api/games", GamesController.index);

        get("/api/markdown/:name", MarkdownTextsController.show);
        put("/api/markdown/:name", MarkdownTextsController.update);

        get("/api/notes", NotesController.index);
        get("/api/notes/:id", NotesController.show);
        post("/api/notes", NotesController.save);
        put("/api/notes/:id", NotesController.update);
        delete("/api/notes/:id", NotesController.delete);

        get("*", ((request, response) -> {
            response.redirect("/#!/404");
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

