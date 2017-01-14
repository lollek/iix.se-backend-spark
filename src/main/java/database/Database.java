package database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import model.*;

import java.sql.SQLException;
import java.util.List;

public class Database {
    private static ConnectionSource connectionSource;

    public static void init() throws SQLException {
        connectionSource = new JdbcConnectionSource("jdbc:sqlite:db/production.sqlite3");
        DaoManager.createDao(connectionSource, Beer.class);
        DaoManager.createDao(connectionSource, Book.class);
        DaoManager.createDao(connectionSource, Group.class);
        DaoManager.createDao(connectionSource, MarkdownText.class);
        DaoManager.createDao(connectionSource, Note.class);
        DaoManager.createDao(connectionSource, User.class);
    }

    public static Dao getDao(Class<?> clazz) {
        return DaoManager.lookupDao(connectionSource, clazz);
    }

    public static List index(Class<?> clazz) throws SQLException {
        return getDao(clazz).queryForAll();
    }

    public static List index(Class<?> clazz, String... columns)throws SQLException {
        final Dao dao = getDao(clazz);
        return dao.query(dao.queryBuilder().selectColumns(columns).prepare());
    }

    public static Object show(Class<?> clazz, int id) throws SQLException {
        final Dao dao = getDao(clazz);
        return dao.queryForFirst(dao.queryBuilder().where().eq("id", id).prepare());
    }
}
