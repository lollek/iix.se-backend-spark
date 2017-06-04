package controller

import com.j256.ormlite.dao.Dao
import database.Database
import model.MarkdownText
import org.eclipse.jetty.http.HttpStatus
import service.AccessService
import service.JsonService
import spark.Request
import spark.Response
import spark.Spark

class MarkdownTextsController : ModelController() {

    companion object {
        fun register(endpointUrl: String) {
            Spark.get("$endpointUrl/:name", show)
            Spark.put("$endpointUrl/:name", update)
        }

        val show = fun(request: Request, _: Response): String? {
            val dao: Dao<MarkdownText?, Int> = Database.getDao(MarkdownText::class.java)
            val query = dao.queryBuilder().where().eq("name", request.params("name")).prepare()
            return JsonService.toJson(dao.queryForFirst(query))
        }

        val update = fun(request: Request, response: Response): String? {
            if (!AccessService.isLoggedIn(request)) {
                response.status(HttpStatus.UNAUTHORIZED_401)
                return ""
            }

            val dao: Dao<MarkdownText?, Int> = Database.getDao(MarkdownText::class.java)
            val dbObject: MarkdownText? = dao.queryForFirst(dao.queryBuilder().where().eq("name", request.params("name")).prepare())
            if (dbObject == null) {
                response.status(HttpStatus.BAD_REQUEST_400)
                return ""
            }

            val jsonObject: MarkdownText? = JsonService.fromJson(request.body(), MarkdownText::class.java)
            if (jsonObject == null) {
                response.status(HttpStatus.BAD_REQUEST_400)
                return ""
            }

            jsonObject.id = dbObject.id
            if (!Database.update(MarkdownText::class.java, jsonObject)) {
                response.status(HttpStatus.BAD_REQUEST_400)
                return ""
            }

            return JsonService.toJson(jsonObject)
        }
    }

}
