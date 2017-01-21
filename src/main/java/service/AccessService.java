package service;

import com.j256.ormlite.dao.Dao;
import database.Database;
import model.User;
import spark.Request;

import java.sql.SQLException;

public class AccessService {

    public static boolean isLoggedIn(Request request) {
        return request.session().attribute("username") != null;
    }

    public static boolean authenticate(Request request, String username, String password) {
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
}