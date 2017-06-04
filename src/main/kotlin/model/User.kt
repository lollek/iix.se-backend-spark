package model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import org.mindrot.jbcrypt.BCrypt

@DatabaseTable(tableName = "users")
class User() : Model() {
    @DatabaseField var username: String? = null
    @DatabaseField var password: String? = null
    @DatabaseField var salt: String? = null

    constructor(username: String) : this() {
        this.username = username
    }

    fun auth(password: String): Boolean {
        return this.password != null && BCrypt.checkpw(password, this.password)
    }
}
