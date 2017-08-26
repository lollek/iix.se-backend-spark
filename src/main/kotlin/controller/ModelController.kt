package controller

import exceptions.HttpBadRequest
import exceptions.HttpUnauthorized
import model.Model
import org.eclipse.jetty.http.HttpStatus
import service.AccessService
import service.JsonService
import spark.Request
import spark.Response

abstract class ModelController {
    companion object {

        @JvmStatic
        protected fun getId(request: Request): Int {
            try {
                return Integer.parseInt(request.params("id"))
            } catch (_: NumberFormatException) {
                throw HttpBadRequest("")
            }
        }

        @Suppress("UNUSED_PARAMETER")
        fun <T: Model> save(clazz: Class<T>, request: Request, response: Response): T {
            if (!AccessService.isLoggedIn(request)) {
                throw HttpUnauthorized()
            }

            val obj: T = JsonService.fromJson(request.body(), clazz) ?: throw HttpBadRequest("")
            if (obj.id != null) {
                throw HttpBadRequest("Id found when not expected")
            }
            obj.save()
            return obj
        }

        @Suppress("UNUSED_PARAMETER")
        fun <T : Model> update(clazz: Class<T>, request: Request, response: Response): T {
            if (!AccessService.isLoggedIn(request)) {
                throw HttpUnauthorized()
            }

            val obj: T = JsonService.fromJson(request.body(), clazz) ?: throw HttpBadRequest("")
            obj.id = getId(request)
            obj.save()
            return obj
        }

        fun delete(request: Request, response: Response, deleteFn: () -> Unit): String {
            if (!AccessService.isLoggedIn(request)) {
                throw HttpUnauthorized()
            }

            deleteFn()

            response.status(HttpStatus.NO_CONTENT_204)
            return ""
        }
    }
}
