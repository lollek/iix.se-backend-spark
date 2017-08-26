package service

import model.User
import spark.Request

class AccessService {
    companion object {

        fun isLoggedIn(request: Request): Boolean {
            return getUsername(request) != null
        }

        fun getUsername(request: Request): String? {
            return request.session().attribute("username")
        }

        fun getUser(request: Request): User? {
            val username: String? = getUsername(request)
            return if (username != null) User(username) else null
        }

        fun login(request: Request, username: String, password: String): Boolean {
            val user: User? = User.loadByUsername(username)
            if (user == null || !user.auth(password)) {
                return false
            }

            request.session().attribute("username", username)
            return true
        }

        fun logout(request: Request) {
            request.session().attribute("username", null)
        }
    }
}
