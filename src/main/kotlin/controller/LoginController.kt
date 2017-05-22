package controller

import exceptions.UnauthorizedException
import model.User
import service.AccessService
import service.JsonService
import spark.Request
import spark.Response
import spark.Spark


class LoginController {
    companion object {
        fun register(endpointUrl: String) {
            Spark.get(endpointUrl, checkLogin)
            Spark.post(endpointUrl, login)
            Spark.delete(endpointUrl, logout)
        }

        @Suppress("UNUSED_PARAMETER")
        private val checkLogin = fun(request: Request, response: Response): String? {
            if (!AccessService.isLoggedIn(request)) {
                throw UnauthorizedException()
            }
            return JsonService.toJson(AccessService.getUser(request))
        }

        private val login = fun(request: Request, response: Response): String? {
            val data: Map<String, Any> = JsonService.jsonToMap(request.body())
            val username: String? = data["username"]?.toString()
            val password: String? = data["password"]?.toString()

            if (username == null || username.isEmpty() || !AccessService.login(request, username, password)) {
                response.status(403)
                return  "Username or password is incorrect"
            }

            return JsonService.toJson(User(username))
        }

        @Suppress("UNUSED_PARAMETER")
        private val logout = fun(request: Request, response: Response): String? {
            AccessService.logout(request)
            return null
        }
    }
}


