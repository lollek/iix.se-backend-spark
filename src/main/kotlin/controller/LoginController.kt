package controller

import exceptions.HttpBadRequest
import exceptions.HttpForbidden
import exceptions.HttpInternalServerError
import exceptions.HttpUnauthorized
import model.User
import service.AccessService
import service.JsonService
import spark.Request
import spark.Response
import spark.Spark


class LoginController {
    companion object {
        fun register(endpointUrl: String) {
            Spark.get(endpointUrl, checkLogin, JsonService.gson::toJson)
            Spark.post(endpointUrl, login, JsonService.gson::toJson)
            Spark.delete(endpointUrl, logout, JsonService.gson::toJson)
        }

        private val checkLogin = fun(request: Request, _: Response): User {
            if (!AccessService.isLoggedIn(request)) {
                throw HttpUnauthorized()
            }
            return AccessService.getUser(request) ?: throw HttpInternalServerError()
        }

        private val login = fun(request: Request, _: Response): User {
            val data: Map<String, Any>? = JsonService.jsonToMap(request.body())
            val username: String? = data?.get("username")?.toString()
            val password: String? = data?.get("password")?.toString()

            if (username == null || username.isEmpty() || password == null) {
                throw HttpBadRequest("")
            }

            if (!AccessService.login(request, username, password)) {
                throw HttpForbidden("Username or password is incorrect")
            }

            return User(username)
        }

        private val logout = fun(request: Request, _: Response): String {
            AccessService.logout(request)
            return ""
        }
    }
}


