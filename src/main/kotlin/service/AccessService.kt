package service

import com.j256.ormlite.dao.Dao
import database.Database
import model.User
import spark.Request

import java.sql.SQLException

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
            val dao: Dao<User?, Int> = Database.getDao(User::class.java)

            val user: User?
            try {
                user = dao.queryForFirst(dao.queryBuilder().where().eq("username", username).prepare())
            } catch (e: SQLException) {
                LogService.logException(e)
                return false
            }

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
