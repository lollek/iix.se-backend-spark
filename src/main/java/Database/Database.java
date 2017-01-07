package Database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import model.Note;

import java.sql.SQLException;
import java.util.List;

public class Database {
    private static ConnectionSource connectionSource;

    public static void init() throws SQLException {
        connectionSource = new JdbcConnectionSource("jdbc:sqlite:production.sqlite3");
        DaoManager.createDao(connectionSource, Note.class);
    }

    private static Dao getDao(Class<?> clazz) {
        return DaoManager.lookupDao(connectionSource, clazz);
    }

    public static List index(Class<?> clazz) throws SQLException {
        return getDao(clazz).queryForAll();
    }

    public static Object show(Class<?> clazz, int id) throws SQLException {
        Dao dao = DaoManager.lookupDao(connectionSource, clazz);
        return dao.queryForFirst(dao.queryBuilder().where().eq("id", id).prepare());
    }
}
