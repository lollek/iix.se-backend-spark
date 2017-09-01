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
        }

        private val checkLogin = fun(request: Request, _: Response): User {
            if (!AccessService.isLoggedIn(request)) {
                throw HttpUnauthorized()
            }
            return AccessService.getUser(request) ?: throw HttpInternalServerError()
        }

        private val login = fun(request: Request, response: Response): User {
            val data: Map<String, Any> = JsonService.jsonToMap(request.body()) ?: throw HttpBadRequest("Failed to parse json")

            val username: String = data["username"]?.toString() ?: throw HttpForbidden("Missing username")
            val password: String = data["password"]?.toString() ?: throw HttpForbidden("Missing password")

            if (!AccessService.login(response, username, password)) {
                throw HttpForbidden("Username or password is incorrect")
            }
            return User(username)
        }
    }
}
