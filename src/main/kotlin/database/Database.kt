package database

import com.j256.ormlite.dao.Dao
import com.j256.ormlite.dao.DaoManager
import com.j256.ormlite.jdbc.JdbcConnectionSource
import com.j256.ormlite.stmt.DeleteBuilder
import com.j256.ormlite.support.ConnectionSource
import model.*

import java.sql.SQLException

class Database {
    companion object {
        private var connectionSource: ConnectionSource? = null

        @Throws(SQLException::class)
        fun init() {
            connectionSource = JdbcConnectionSource(
                    System.getProperty("dbPath"),
                    System.getProperty("dbUser"),
                    System.getProperty("dbPassword")
            )

            DaoManager.createDao(connectionSource, Beer::class.java)
            DaoManager.createDao(connectionSource, Book::class.java)
            DaoManager.createDao(connectionSource, Group::class.java)
            DaoManager.createDao(connectionSource, MarkdownText::class.java)
            DaoManager.createDao(connectionSource, Note::class.java)
            DaoManager.createDao(connectionSource, User::class.java)
        }

        fun <T: Model> getDao(clazz: Class<T>): Dao<T?, Int> = DaoManager.lookupDao(connectionSource, clazz)

        @Throws(SQLException::class)
        fun <T: Model> index(clazz: Class<T>): MutableList<T?>? = getDao(clazz).queryForAll()

        @Throws(SQLException::class)
        fun <T: Model> index(clazz: Class<T>, vararg columns: String): MutableList<T?>? {
            val dao: Dao<T?, Int> = getDao(clazz)
            return dao.query(dao.queryBuilder().selectColumns(*columns).prepare())
        }

        @Throws(SQLException::class)
        fun <T: Model> show(clazz: Class<T>, id: Int): T? {
            val dao: Dao<T?, Int> = getDao(clazz)
            return dao.queryForFirst(dao.queryBuilder().where().idEq(id).prepare())
        }

        fun <T: Model> save(clazz: Class<T>, obj: T): Boolean {
            try {
                getDao(clazz).create(obj)
                return true
            } catch (_: SQLException) {
                return false
            }
        }

        fun <T: Model> update(clazz: Class<T>, obj: T): Boolean {
            try {
                getDao(clazz).update(obj)
                return true
            } catch (_: SQLException) {
                return false
            }
        }

        fun <T: Model> delete(clazz: Class <T>, id: Int): Boolean {
            try {
                val deleteBuilder: DeleteBuilder<T?, Int> = getDao(clazz).deleteBuilder()
                deleteBuilder.where().idEq(id)
                deleteBuilder.delete()
                return true
            } catch (_: SQLException) {
                return false
            }
        }
    }
}
