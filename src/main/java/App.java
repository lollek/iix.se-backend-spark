import Database.Database;
import controller.NotesController;

import java.sql.SQLException;

import static spark.Spark.*;

public class App {

    public static void main(String[] args) throws SQLException {
        Database.init();

        get("/notes", NotesController.index);
        get("/notes/:id", NotesController.show);
    }
}

