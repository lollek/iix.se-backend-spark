package controller

import database.Database
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

        fun <T: Model> index(clazz: Class<T>): String? {
            return JsonService.toJson(Database.index(clazz))
        }

        fun <T: Model> show(clazz: Class<T>, request: Request, response: Response): String? {
            val id: Int? = getId(request)
            if (id == null) {
                response.status(HttpStatus.BAD_REQUEST_400)
                return ""
            }
            return JsonService.toJson(Database.show(clazz, id))
        }

        fun <T: Model> save(clazz: Class<T>, request: Request, response: Response): String? {
            if (!AccessService.isLoggedIn(request)) {
                response.status(HttpStatus.UNAUTHORIZED_401)
                return ""
            }

            val obj: T? = JsonService.fromJson(request.body(), clazz)
            if (obj == null || !Database.save(clazz, obj)) {
                response.status(HttpStatus.BAD_REQUEST_400)
                return ""
            }
            return JsonService.toJson(obj)
        }

        fun <T : Model> update(clazz: Class<T>, request: Request, response: Response): String? {
            if (!AccessService.isLoggedIn(request)) {
                response.status(HttpStatus.UNAUTHORIZED_401)
                return ""
            }

            val id: Int? = getId(request)
            if (id == null) {
                response.status(HttpStatus.BAD_REQUEST_400)
                return ""
            }

            val obj: T? = JsonService.fromJson(request.body(), clazz)
            if (obj == null) {
                response.status(HttpStatus.BAD_REQUEST_400)
                return ""
            }
            obj.id = id

            if (!Database.update(clazz, obj)) {
                response.status(HttpStatus.BAD_REQUEST_400)
                return ""
            }
            return JsonService.toJson(obj)
        }

        fun <T : Model> delete(clazz: Class<T>, request: Request, response: Response): String? {
            if (!AccessService.isLoggedIn(request)) {
                response.status(HttpStatus.UNAUTHORIZED_401)
                return ""
            }

            val id: Int? = getId(request)
            if (id == null) {
                response.status(HttpStatus.BAD_REQUEST_400)
                return ""
            }

            if (!Database.delete(clazz, id)) {
                response.status(HttpStatus.BAD_REQUEST_400)
                return ""
            }

            response.status(HttpStatus.NO_CONTENT_204)
            return ""
        }
    }
}
