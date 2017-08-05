package controller

import model.Book
import service.GoodreadsService
import service.JsonService
import spark.Request
import spark.Response
import spark.Spark
import java.time.LocalDateTime

class BooksController : ModelController() {
    companion object {

        fun register(endpointUrl: String) {
            Spark.get(endpointUrl, index, JsonService.gson::toJson)
        }

        private var bookListUpdate: LocalDateTime? = null
        private var bookList: List<Book> = listOf()

        val index = fun(_: Request, _: Response): List<Book> {
            synchronized(this) {
                if (bookListUpdate?.isBefore(LocalDateTime.now().minusHours(1)) ?: true) {
                    bookList = GoodreadsService.getToReadList(37418936)
                    bookListUpdate = LocalDateTime.now()
                }
            }
            return bookList
        }
    }
}
