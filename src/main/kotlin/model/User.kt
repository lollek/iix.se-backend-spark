package model

import org.jooq.DSLContext
import org.jooq.generated.tables.Users.USERS
import org.mindrot.jbcrypt.BCrypt
import service.DbService
import javax.persistence.Column

class User() : Model() {
    @Column(name="USERNAME") var username: String? = null
    @Column(name="PASSWORD") var password: String? = null
    @Column(name="SALT") var salt: String? = null

    constructor(username: String) : this() {
        this.username = username
    }

    override fun save() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun auth(password: String): Boolean {
        return this.password != null && BCrypt.checkpw(password, this.password)
    }

    fun sanitizedCopy(): User {
        val user: User = User()
        user.id = this.id
        user.username = this.username
        return user
    }

    companion object {
        fun loadByUsername(username: String): User? {
            return DbService.query { context: DSLContext ->
                context.select()
                       .from(USERS)
                       .where(USERS.USERNAME.eq(username))
                       .fetchAnyInto(User::class.java)
            }
        }
    }
}
