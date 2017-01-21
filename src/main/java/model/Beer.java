package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "beers")
public class Beer extends Model implements Serializable {

    @SuppressWarnings("unused")
    @DatabaseField
    private String name;

    @SuppressWarnings("unused")
    @DatabaseField
    private String brewery;

    @SuppressWarnings("unused")
    @DatabaseField
    private Double percentage;

    @SuppressWarnings("unused")
    @DatabaseField
    private String country;

    @SuppressWarnings("unused")
    @DatabaseField
    private String style;

    @SuppressWarnings("unused")
    @DatabaseField
    private Double sscore;

    @SuppressWarnings("unused")
    @DatabaseField
    private Double oscore;
}
