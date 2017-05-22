package service;

import com.j256.ormlite.dao.Dao;
import database.Database;
import model.User;
import spark.Request;

import java.sql.SQLException;

public class AccessService {

    public static boolean isLoggedIn(Request request) {
        return getUsername(request) != null;
    }

    public static String getUsername(Request request) {
        return request.session().attribute("username");
    }

    public static User getUser(Request request) {
        final String username = getUsername(request);
        return username != null ? new User(username) : null;
    }

    public static boolean login(Request request, String username, String password) {
        Dao<User, Integer> dao = Database.getDao(User.class);
        User user = null;
        try {
            user = dao.queryForFirst(dao.queryBuilder().where().eq("username", username).prepare());
        } catch (SQLException e) {
            LogService.logException(e);
        }

        if (user == null || !user.auth(password)) {
            return false;
        }

        request.session().attribute("username", username);
        return true;
    }

    public static void logout(Request request) {
        request.session().attribute("username", null);
    }
}
