package controller

import database.Database
import exceptions.HttpBadRequest
import exceptions.HttpInternalServerError
import exceptions.HttpUnauthorized
import model.Model
import org.eclipse.jetty.http.HttpStatus
import service.AccessService
import service.JsonService
import spark.Request
import spark.Response

abstract class ModelController {
    companion object {
        private fun getId(request: Request): Int? {
            try {
                return Integer.parseInt(request.params("id"))
            } catch (_: NumberFormatException) {
                return null
            }
        }

        fun <T: Model> index(clazz: Class<T>): String {
            return JsonService.toJson(Database.index(clazz))
        }

        @Suppress("UNUSED_PARAMETER")
        fun <T: Model> show(clazz: Class<T>, request: Request, response: Response): String {
            val id: Int = getId(request) ?: throw HttpBadRequest("")
            return JsonService.toJson(Database.show(clazz, id))
        }

        @Suppress("UNUSED_PARAMETER")
        fun <T: Model> save(clazz: Class<T>, request: Request, response: Response): String {
            if (!AccessService.isLoggedIn(request)) {
                throw HttpUnauthorized()
            }

            val obj: T? = JsonService.fromJson(request.body(), clazz)
            if (obj == null || !Database.save(clazz, obj)) {
                throw HttpBadRequest("")
            }
            return JsonService.toJson(obj)
        }

        @Suppress("UNUSED_PARAMETER")
        fun <T : Model> update(clazz: Class<T>, request: Request, response: Response): String {
            if (!AccessService.isLoggedIn(request)) {
                throw HttpUnauthorized()
            }

            val id: Int = getId(request) ?: throw HttpBadRequest("")
            val obj: T = JsonService.fromJson(request.body(), clazz) ?: throw HttpBadRequest("")
            obj.id = id

            if (!Database.update(clazz, obj)) {
                throw HttpInternalServerError()
            }
            return JsonService.toJson(obj)
        }

        fun <T : Model> delete(clazz: Class<T>, request: Request, response: Response): String {
            if (!AccessService.isLoggedIn(request)) {
                throw HttpUnauthorized()
            }

            val id: Int = getId(request) ?: throw HttpBadRequest("")

            if (!Database.delete(clazz, id)) {
                throw HttpInternalServerError()
            }

            response.status(HttpStatus.NO_CONTENT_204)
            return ""
        }
    }
}
