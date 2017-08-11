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
        }

        fun <T: Model> getDao(clazz: Class<T>): Dao<T?, Int>? = DaoManager.createDao(connectionSource, clazz)

        inline fun <reified T: Model> index(clazz: Class<T>, vararg columns: String): List<T>? {
            val dao: Dao<T?, Int> = getDao(clazz)!!
            return when {
                columns.isNotEmpty() -> dao.query(dao.queryBuilder().selectColumns(*columns).prepare())
                else -> dao.queryForAll()
            }?.filterIsInstance<T>()
        }

        fun <T: Model> show(clazz: Class<T>, id: Int): T? {
            val dao: Dao<T?, Int> = getDao(clazz)!!
            return dao.queryForFirst(dao.queryBuilder().where().idEq(id).prepare())
        }

        fun <T: Model> save(clazz: Class<T>, obj: T): Boolean {
            return getDao(clazz)!!.create(obj) > 0
        }

        fun <T: Model> update(clazz: Class<T>, obj: T): Boolean {
            return getDao(clazz)!!.update(obj) > 0
        }

        fun <T: Model> delete(clazz: Class <T>, id: Int): Boolean {
            val deleteBuilder: DeleteBuilder<T?, Int> = getDao(clazz)!!.deleteBuilder()
            deleteBuilder.where().idEq(id)
            return deleteBuilder.delete() > 0
        }
    }
}
