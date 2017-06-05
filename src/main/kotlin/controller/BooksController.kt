package controller

import model.Book
import spark.Request
import spark.Response
import spark.Spark

class BooksController : ModelController() {
    companion object {

        fun register(endpointUrl: String) {
            Spark.get(endpointUrl, index)
            Spark.get("$endpointUrl/:id", show)
            Spark.post(endpointUrl, save)
            Spark.put("$endpointUrl/:id", update)
            Spark.delete("$endpointUrl/:id", delete)
        }

        val index = fun(_: Request, _: Response): String = index(Book::class.java)
        val show = fun(request: Request, response: Response): String = show(Book::class.java, request, response)
        val save = fun(request: Request, response: Response): String = save(Book::class.java, request, response)
        val update = fun(request: Request, response: Response): String = update(Book::class.java, request, response)
        val delete = fun(request: Request, response: Response): String = delete(Book::class.java, request, response)
    }
}
