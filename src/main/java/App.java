import database.Database;
import controller.BooksController;
import controller.LoginController;
import controller.MarkdownTextsController;
import controller.NotesController;
import service.LogService;

import java.sql.SQLException;

import static spark.Spark.*;

public class App {

    public static void main(String[] args) throws SQLException {

        Database.init();

        port(4567);
        staticFileLocation("/public");

        get("/api/markdown_text", MarkdownTextsController.index);
        get("/api/markdown_text/:id", MarkdownTextsController.show);

        get("/api/books", BooksController.index);
        get("/api/books/:id", BooksController.show);

        get("/api/notes", NotesController.index);
        get("/api/notes/:id", NotesController.show);

        get("/api/login", LoginController.login);

        after(LogService::logAccess);

        exception(Exception.class, (exception, request, response) -> {
            LogService.logAccess(request, response);
            LogService.logException(exception);
        });

    }
}

