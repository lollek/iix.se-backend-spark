package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "users")
public class User {

    @SuppressWarnings("unused")
    @DatabaseField(generatedId = true)
    private int id;

    @SuppressWarnings("unused")
    @DatabaseField
    private String username;

    @SuppressWarnings("unused")
    @DatabaseField
    private String password;

    @SuppressWarnings("unused")
    @DatabaseField
    private String salt;

}
