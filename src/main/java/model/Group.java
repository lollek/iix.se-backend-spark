package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "groups")
public class Group {

    @SuppressWarnings("unused")
    @DatabaseField(generatedId = true)
    private int id;

    @SuppressWarnings("unused")
    @DatabaseField
    private String name;
}
