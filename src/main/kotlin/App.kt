import controller.*
import exceptions.*
import org.eclipse.jetty.http.HttpStatus
import service.LogService
import spark.Request
import spark.Response

import java.sql.SQLException

import spark.Spark

class App {
    companion object {

        @JvmStatic
        @Throws(SQLException::class)
        fun main(vararg args: String) {
            Spark.ipAddress("0.0.0.0")
            Spark.port(8002)

            BeverageController.register("/api/beverages")
            BooksController.register("/api/books")
            GamesController.register("/api/games")
            LoginController.register("/api/login")
            MarkdownTextsController.register("/api/markdown")
            NotesController.register("api/notes")
            WordsController.register("api/words")

            Spark.connect("*", fun(_:Request, _: Response) { throw HttpNotFound() })
            Spark.delete("*", fun(_:Request, _: Response) { throw HttpNotFound() })
            Spark.get("*", fun(_:Request, _: Response) { throw HttpNotFound() })
            Spark.head("*", fun(_:Request, _: Response) { throw HttpNotFound() })
            Spark.options("*", fun(_:Request, _: Response) { throw HttpNotFound() })
            Spark.patch("*", fun(_:Request, _: Response) { throw HttpNotFound() })
            Spark.post("*", fun(_:Request, _: Response) { throw HttpNotFound() })
            Spark.put("*", fun(_:Request, _: Response) { throw HttpNotFound() })
            Spark.trace("*", fun(_:Request, _: Response) { throw HttpNotFound() })

            Spark.exception(HttpStatusException::class.java, fun(exception: Exception, request: Request, response: Response) {
                (exception as HttpStatusException).let {
                    response.status(exception.httpStatus)
                    response.body(exception.message)
                }
                LogService.logAccess(request, response)
            })
            Spark.exception(Exception::class.java, fun(exception: Exception, request: Request, response: Response) {
                response.status(HttpStatus.INTERNAL_SERVER_ERROR_500)
                response.body("")
                LogService.logException(exception)
                LogService.logAccess(request, response)
            })
            Spark.after("*", fun(request: Request, response: Response) {
                response.type("application/json")
                response.header("Access-Control-Allow-Origin", "*")
                if (request.headers("Accept-Encoding")?.contains("gzip") == true) {
                    response.header("Content-Encoding", "gzip")
                }
            })
            Spark.after("*", fun(request: Request, response: Response) = LogService.logAccess(request, response))

        }
    }
}

