import controller.*;
import database.Database;
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
        if (develop) {
            port(4567);
            staticFiles.externalLocation(System.getProperty("staticFolder"));
        } else {
            port(80);
            staticFiles.location("/public");
        }

        // Special services
        get("/api/login", LoginController.checkLogin);
        post("/api/login", LoginController.login);
        delete("/api/login", LoginController.logout);

        // Resources
        get("/api/beers", BeersController.index);
        get("/api/beers/:id", BeersController.show);
        post("/api/beers", BeersController.save);
        put("/api/beers/:id", BeersController.update);
        delete("/api/beers/:id", BeersController.delete);

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

        notFound("");
        internalServerError("");
        after((request, response) -> response.type("application/json"));
        after(LogService::logAccess);
        exception(Exception.class, (exception, request, response) -> {
            LogService.logAccess(request, response);
            LogService.logException(exception);
        });

    }
}

