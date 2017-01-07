package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "books")
public class Book implements Serializable{

    @SuppressWarnings("unused")
    @DatabaseField(generatedId = true)
    private int id;

    @SuppressWarnings("unused")
    @DatabaseField
    private String title;

    @SuppressWarnings("unused")
    @DatabaseField
    private String author;

    @SuppressWarnings("unused")
    @DatabaseField
    private String other;
}
