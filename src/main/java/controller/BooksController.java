package controller;

import model.Book;
import spark.Request;
import spark.Response;
import spark.Route;

public class BooksController extends ModelController {

    public static Route index = (Request request, Response response) -> index(Book.class);
    public static Route show = (Request request, Response response) -> show(Book.class, request);
}
