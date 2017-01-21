package database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.sun.istack.internal.NotNull;
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

    public static <T> Dao<T, Integer> getDao(@NotNull Class<T> clazz) {
        return DaoManager.lookupDao(connectionSource, clazz);
    }

    public static <T> List<T> index(@NotNull Class<T> clazz) throws SQLException {
        return getDao(clazz).queryForAll();
    }

    public static <T> List<T> index(@NotNull Class<T> clazz, @NotNull String... columns)throws SQLException {
        final Dao<T, Integer> dao = getDao(clazz);
        return dao.query(dao.queryBuilder().selectColumns(columns).prepare());
    }

    public static <T> T show(@NotNull Class<T> clazz, int id) throws SQLException {
        final Dao<T, Integer> dao = getDao(clazz);
        return dao.queryForFirst(dao.queryBuilder().where().idEq(id).prepare());
    }

    public static <T> boolean save(@NotNull Class<T> clazz,@NotNull T object) {
        try {
            getDao(clazz).create(object);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static <T> boolean update(@NotNull Class<T> clazz,@NotNull  T object) {
        try {
            getDao(clazz).update(object);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static <T> boolean delete(@NotNull Class <T> clazz, int id) {
        try {
            DeleteBuilder<T, Integer> deleteBuilder = getDao(clazz).deleteBuilder();
            deleteBuilder.where().idEq(id);
            deleteBuilder.delete();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
