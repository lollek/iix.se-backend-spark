package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "books")
public class Book implements Serializable{

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String title;

    @DatabaseField
    private String author;

    @DatabaseField
    private String other;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
