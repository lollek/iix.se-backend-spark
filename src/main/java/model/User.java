package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.sun.istack.internal.Nullable;
import org.mindrot.jbcrypt.BCrypt;

@DatabaseTable(tableName = "users")
public class User extends Model {

    @SuppressWarnings("unused")
    @DatabaseField
    private String username;

    @SuppressWarnings("unused")
    @DatabaseField
    private String password;

    @SuppressWarnings("unused")
    @DatabaseField
    private String salt;

    public boolean auth(@Nullable String password) {
        return this.password != null && BCrypt.checkpw(password, this.password);
    }
}
