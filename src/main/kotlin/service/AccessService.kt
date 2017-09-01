package service

import model.User
import spark.Request
import java.security.Key
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import spark.Response
import javax.crypto.spec.SecretKeySpec


class AccessService {
    companion object {
        private val key: Key = SecretKeySpec(System.getProperty("jwt").toByteArray(), SignatureAlgorithm.HS256.jcaName)
        private val AUTHORIZATION_HEADER = "Authorization"
        private val AUTHORIZATION_PREFIX = "Bearer "


        fun isLoggedIn(request: Request): Boolean {
            return validateJWT(getJWTFromRequest(request) ?: return false)
        }

        fun getUser(request: Request): User? {
            return User.loadByUsername(getUsernameFromJWT(getJWTFromRequest(request) ?: return null) ?: return null)
        }

        fun login(response: Response, username: String, password: String): User? {
            val user: User = User.loadByUsername(username) ?: return null
            if (!user.auth(password)) {
                return null
            }

            response.header(AUTHORIZATION_HEADER, "$AUTHORIZATION_PREFIX${generateJWT(user)}")
            return user
        }

        private fun getJWTFromRequest(request: Request): String? {
            val authHeader = request.headers(AUTHORIZATION_HEADER) ?: return null
            return authHeader.removePrefix(AUTHORIZATION_PREFIX)
        }

        private fun generateJWT(user: User): String {
            return Jwts.builder()
                    .setSubject(user.username!!)
                    .signWith(SignatureAlgorithm.HS512, key)
                    .compact()
        }

        private fun validateJWT(token: String): Boolean {
            try {
                Jwts.parser().setSigningKey(key).parseClaimsJws(token)
                return true
            } catch (e: Exception) {
                return false
            }
        }

        private fun getUsernameFromJWT(token: String): String? {
            try {
                return Jwts.parser().setSigningKey(key).parseClaimsJws(token).body.subject
            } catch (e: Exception) {
                return null
            }
        }
    }
}
