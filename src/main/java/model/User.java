package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.mindrot.jbcrypt.BCrypt;

@DatabaseTable(tableName = "users")
public class User extends Model {

    public User() {}

    public User(String username) {
        this.username = username;
    }

    @SuppressWarnings("unused")
    @DatabaseField
    public String username;

    @SuppressWarnings("unused")
    @DatabaseField
    private String password;

    @SuppressWarnings("unused")
    @DatabaseField
    private String salt;

    public boolean auth(String password) {
        return this.password != null && BCrypt.checkpw(password, this.password);
    }
}
