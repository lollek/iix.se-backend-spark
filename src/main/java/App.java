import controller.*;
import database.Database;
import service.LogService;

import java.sql.SQLException;

import static spark.Spark.*;

public class App {

    public static void main(String[] args) throws SQLException {

        // Init
        Database.init();
        port(4567);
        staticFiles.externalLocation(System.getProperty("staticFolder"));

        // Special services
        get("/api/login", LoginController.login);

        // Resources
        get("/api/beers", BeersController.index);
        get("/api/beers/:id", BeersController.show);

        get("/api/books", BooksController.index);
        get("/api/books/:id", BooksController.show);

        get("/api/markdown_text", MarkdownTextsController.index);
        get("/api/markdown_text/:id", MarkdownTextsController.show);

        get("/api/notes", NotesController.index);
        get("/api/notes/:id", NotesController.show);

        // Config
        after(LogService::logAccess);
        exception(Exception.class, (exception, request, response) -> {
            LogService.logAccess(request, response);
            LogService.logException(exception);
        });

    }
}

