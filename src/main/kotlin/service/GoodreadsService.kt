package service

import model.Book
import org.apache.log4j.Logger
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.NodeList
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import javax.xml.parsers.DocumentBuilderFactory

class GoodreadsService {
    companion object {
        private val logger: Logger = Logger.getLogger(GoodreadsService::class.java)
        private val goodreadsUrl: String = "https://www.goodreads.com"
        private val apiKey: String = System.getProperty("goodreadsApiKey")

        fun getToReadList(userId: Number): List<Book> {
            val url: URL = URL("$goodreadsUrl/review/list?v=2&id=$userId&shelf=to-read&key=$apiKey")
            val con: HttpsURLConnection = url.openConnection() as HttpsURLConnection
            val xmlData: Document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(con.inputStream)
            xmlData.normalizeDocument()

            val reviews: NodeList = xmlData.getElementsByTagName("review")
            return 0.until(reviews.length).map { reviewIndex: Int ->
                val review: Element = reviews.item(reviewIndex) as Element
                val books: NodeList = review.getElementsByTagName("book")
                0.until(books.length).map { bookIndex: Int ->
                    val book: Element = books.item(bookIndex) as Element

                    val owned : Boolean = review.getElementsByTagName("owned").item(0).textContent == "1"
                    if (owned) {
                        null
                    } else {
                        val title: String = book.getElementsByTagName("title").item(0).textContent
                        val author: String = book.getElementsByTagName("name").item(0).textContent
                        val image: String = book.getElementsByTagName("image_url").item(0).textContent
                        Book(title, author, null, image)
                    }

                }
            }.flatten().filterNotNull()
        }
    }
}

