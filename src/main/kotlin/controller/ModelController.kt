package controller

import database.Database
import exceptions.HttpBadRequest
import exceptions.HttpInternalServerError
import exceptions.HttpNotFound
import exceptions.HttpUnauthorized
import model.Model
import org.eclipse.jetty.http.HttpStatus
import service.AccessService
import service.JsonService
import spark.Request
import spark.Response

abstract class ModelController {
    companion object {
        private fun getId(request: Request): Int {
            try {
                return Integer.parseInt(request.params("id"))
            } catch (_: NumberFormatException) {
                throw HttpBadRequest("")
            }
        }

        inline fun <reified T: Model> index(clazz: Class<T>, vararg columns: String): List<T> {
            return Database.index(clazz, *columns) ?: listOf<T>()
        }

        @Suppress("UNUSED_PARAMETER")
        fun <T: Model> show(clazz: Class<T>, request: Request, response: Response): T {
            return Database.show(clazz, getId(request)) ?: throw HttpNotFound()
        }

        @Suppress("UNUSED_PARAMETER")
        fun <T: Model> save(clazz: Class<T>, request: Request, response: Response): T {
            if (!AccessService.isLoggedIn(request)) {
                throw HttpUnauthorized()
            }

            val obj: T? = JsonService.fromJson(request.body(), clazz)
            if (obj == null || !Database.save(clazz, obj)) {
                throw HttpBadRequest("")
            }
            return obj
        }

        @Suppress("UNUSED_PARAMETER")
        fun <T : Model> update(clazz: Class<T>, request: Request, response: Response): T {
            if (!AccessService.isLoggedIn(request)) {
                throw HttpUnauthorized()
            }

            val obj: T = JsonService.fromJson(request.body(), clazz) ?: throw HttpBadRequest("")
            obj.id = getId(request)

            if (!Database.update(clazz, obj)) {
                throw HttpInternalServerError()
            }
            return obj
        }

        fun <T : Model> delete(clazz: Class<T>, request: Request, response: Response): String {
            if (!AccessService.isLoggedIn(request)) {
                throw HttpUnauthorized()
            }

            if (!Database.delete(clazz, getId(request))) {
                throw HttpInternalServerError()
            }

            response.status(HttpStatus.NO_CONTENT_204)
            return ""
        }
    }
}
