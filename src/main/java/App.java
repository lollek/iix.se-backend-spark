import Database.Database;
import controller.BooksController;
import controller.NotesController;

import java.sql.SQLException;

import static spark.Spark.*;

public class App {

    public static void main(String[] args) throws SQLException {
        Database.init();

        get("/books", BooksController.index);
        get("/books/:id", BooksController.show);
        get("/notes", NotesController.index);
        get("/notes/:id", NotesController.show);
    }
}

