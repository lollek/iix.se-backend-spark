package database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.support.ConnectionSource;
import model.*;

import java.sql.SQLException;
import java.util.List;

public class Database {
    private static ConnectionSource connectionSource;

    public static void init() throws SQLException {
        connectionSource = new JdbcConnectionSource("jdbc:postgresql://localhost:5432/iix-notes", "www-data", "www-data");
        DaoManager.createDao(connectionSource, Beer.class);
        DaoManager.createDao(connectionSource, Book.class);
        DaoManager.createDao(connectionSource, Group.class);
        DaoManager.createDao(connectionSource, MarkdownText.class);
        DaoManager.createDao(connectionSource, Note.class);
        DaoManager.createDao(connectionSource, User.class);
    }

    public static <T> Dao<T, Integer> getDao(Class<T> clazz) {
        return DaoManager.lookupDao(connectionSource, clazz);
    }

    public static <T> List<T> index(Class<T> clazz) throws SQLException {
        return getDao(clazz).queryForAll();
    }

    public static <T> List<T> index(Class<T> clazz, String... columns)throws SQLException {
        final Dao<T, Integer> dao = getDao(clazz);
        return dao.query(dao.queryBuilder().selectColumns(columns).prepare());
    }

    public static <T> T show(Class<T> clazz, int id) throws SQLException {
        final Dao<T, Integer> dao = getDao(clazz);
        return dao.queryForFirst(dao.queryBuilder().where().idEq(id).prepare());
    }

    public static <T> boolean save(Class<T> clazz, T object) {
        try {
            getDao(clazz).create(object);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static <T> boolean update( Class<T> clazz,  T object) {
        try {
            getDao(clazz).update(object);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static <T> boolean delete( Class <T> clazz, int id) {
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
