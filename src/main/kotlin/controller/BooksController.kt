package controller

import model.Book
import service.JsonService
import spark.Request
import spark.Response
import spark.Spark

class BooksController : ModelController() {
    companion object {

        fun register(endpointUrl: String) {
            Spark.get(endpointUrl, index, JsonService.gson::toJson)
            Spark.get("$endpointUrl/:id", show, JsonService.gson::toJson)
            Spark.post(endpointUrl, save, JsonService.gson::toJson)
            Spark.put("$endpointUrl/:id", update, JsonService.gson::toJson)
            Spark.delete("$endpointUrl/:id", delete, JsonService.gson::toJson)
        }

        val index = fun(_: Request, _: Response): List<Book> = index(Book::class.java)
        val show = fun(request: Request, response: Response): Book = show(Book::class.java, request, response)
        val save = fun(request: Request, response: Response): Book = save(Book::class.java, request, response)
        val update = fun(request: Request, response: Response): Book = update(Book::class.java, request, response)
        val delete = fun(request: Request, response: Response): String = delete(Book::class.java, request, response)
    }
}
