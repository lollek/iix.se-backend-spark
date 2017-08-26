package database

import model.*
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import java.sql.Connection
import java.sql.DriverManager

class Database {
    companion object {
        fun getConnection(): Connection {
            return DriverManager.getConnection(
                    System.getProperty("dbPath"),
                    System.getProperty("dbUser"),
                    System.getProperty("dbPassword")
            )
        }

        fun execute(task: (context: DSLContext) -> Unit) {
            val connection: Connection = getConnection()
            val context: DSLContext = DSL.using(connection, SQLDialect.POSTGRES)
            task(context)
            connection.close()
        }

        fun <T: Model> query(task: (context: DSLContext) -> T?): T? {
            val connection: Connection = getConnection()
            val context: DSLContext = DSL.using(connection, SQLDialect.POSTGRES)
            val t = task(context)
            connection.close()
            return t
        }

        fun <T: Model> queryAll(task: (context: DSLContext) -> List<T>): List<T> {
            val connection: Connection = getConnection()
            val context: DSLContext = DSL.using(connection, SQLDialect.POSTGRES)
            val t = task(context)
            connection.close()
            return t
        }
    }
}
