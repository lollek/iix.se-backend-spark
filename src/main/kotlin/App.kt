import controller.*
import database.Database
import exceptions.UnauthorizedException
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
            Database.init()
            Spark.ipAddress(System.getProperty("ip"))
            Spark.port(8002)

            BeersController.register("/api/beers")
            BooksController.register("/api/books")
            GamesController.register("/api/games")
            LoginController.register("/api/login")
            MarkdownTextsController.register("/api/markdown")
            NotesController.register("api/notes")

            Spark.get("*", fun(_: Request, response: Response): String? {
                response.status(404)
                return ""
            })

            Spark.notFound("")
            Spark.internalServerError("")

            Spark.after("*", fun(_: Request, response: Response) {
                response.type("application/json")
            })
            Spark.after("*", fun(request: Request, response: Response) = LogService.Companion.logAccess(request, response))

            Spark.exception(UnauthorizedException::class.java, fun(_: Exception, _: Request, response: Response) = response.status(HttpStatus.UNAUTHORIZED_401))
            Spark.exception(Exception::class.java, fun(exception: Exception, request: Request, response: Response) {
                LogService.logAccess(request, response)
                LogService.logException(exception)
            })
        }
    }
}

