package controller

import com.j256.ormlite.dao.Dao
import database.Database
import exceptions.HttpBadRequest
import exceptions.HttpInternalServerError
import exceptions.HttpUnauthorized
import model.MarkdownText
import org.apache.log4j.Logger
import service.AccessService
import service.JsonService
import spark.Request
import spark.Response
import spark.Spark
import java.sql.SQLException

class MarkdownTextsController : ModelController() {

    companion object {
        val logger: Logger = Logger.getLogger(MarkdownTextsController::class.java)

        fun register(endpointUrl: String) {
            Spark.get("$endpointUrl/:name", show)
            Spark.put("$endpointUrl/:name", update)
        }

        val show = fun(request: Request, _: Response): String {
            Database.getDao(MarkdownText::class.java)?.let { dao: Dao<MarkdownText?, Int> ->
                try {
                    val query = dao.queryBuilder().where().eq("name", request.params("name")).prepare()
                    return JsonService.toJson(dao.queryForFirst(query))
                } catch (e: SQLException) {
                    logger.error("Database query failed!", e)
                }
            }
            throw HttpInternalServerError()
        }

        val update = fun(request: Request, _: Response): String {
            if (!AccessService.isLoggedIn(request)) {
                throw HttpUnauthorized()
            }

            val dao: Dao<MarkdownText?, Int>? = Database.getDao(MarkdownText::class.java)
            if (dao == null) {
                logger.error("Failed to get Dao!")
                throw HttpInternalServerError()
            }

            val dbObject: MarkdownText
            try {
                val query = dao.queryBuilder().where().eq("name", request.params("name")).prepare()
                dbObject = dao.queryForFirst(query) ?: throw HttpBadRequest("")
            } catch (e: SQLException) {
                logger.error("Database query failed!", e)
                throw HttpInternalServerError()
            }

            val jsonObject: MarkdownText = JsonService.fromJson(request.body(), MarkdownText::class.java) ?: throw HttpBadRequest("")
            jsonObject.id = dbObject.id
            if (!Database.update(MarkdownText::class.java, jsonObject)) {
                throw HttpInternalServerError()
            }

            return JsonService.toJson(jsonObject)
        }
    }

}
