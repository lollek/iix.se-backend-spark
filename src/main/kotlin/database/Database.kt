package database

import com.j256.ormlite.dao.Dao
import com.j256.ormlite.dao.DaoManager
import com.j256.ormlite.jdbc.JdbcConnectionSource
import com.j256.ormlite.stmt.DeleteBuilder
import com.j256.ormlite.support.ConnectionSource
import model.*
import org.apache.log4j.Logger

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

        fun <T: Model> getDao(clazz: Class<T>): Dao<T?, Int>? = DaoManager.lookupDao(connectionSource, clazz)

        inline fun <reified T: Model> index(clazz: Class<T>, vararg columns: String): List<T>? {
            getDao(clazz)?.let { dao: Dao<T?, Int> ->
                try {
                    return when {
                        columns.isNotEmpty() -> dao.query(dao.queryBuilder().selectColumns(*columns).prepare())
                        else -> getDao(clazz)?.queryForAll()
                    }?.filterIsInstance<T>()
                } catch (e: SQLException) {
                    Logger.getLogger(Database::class.java).error("Database query failed!", e)
                }
            }
            return null
        }

        fun <T: Model> show(clazz: Class<T>, id: Int): T? {
            getDao(clazz)?.let { dao: Dao<T?, Int> ->
                try {
                    return dao.queryForFirst(dao.queryBuilder().where().idEq(id).prepare())
                } catch (e: SQLException) {
                    Logger.getLogger(Database::class.java).error("Database query failed!", e)
                }
            }
            return null
        }

        fun <T: Model> save(clazz: Class<T>, obj: T): Boolean {
            getDao(clazz)?.let { dao: Dao<T?, Int> ->
                try {
                    return dao.create(obj) > 0
                } catch (e: SQLException) {
                    Logger.getLogger(Database::class.java).error("Database update failed!", e)
                }
            }
            return false
        }

        fun <T: Model> update(clazz: Class<T>, obj: T): Boolean {
            getDao(clazz)?.let { dao: Dao<T?, Int> ->
                try {
                    return dao.update(obj) > 0
                } catch (e: SQLException) {
                    Logger.getLogger(Database::class.java).error("Database update failed!", e)
                }
            }
            return false
        }

        fun <T: Model> delete(clazz: Class <T>, id: Int): Boolean {
            getDao(clazz)?.deleteBuilder()?.let { deleteBuilder: DeleteBuilder<T?, Int> ->
                try {
                    deleteBuilder.where().idEq(id)
                    deleteBuilder.delete()
                    return true
                } catch (e: SQLException) {
                    Logger.getLogger(Database::class.java).error("Database delete failed!", e)
                }
            }
            return false
        }
    }
}
