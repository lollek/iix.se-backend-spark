package model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;

@DatabaseTable(tableName = "notes")
public class Note implements Serializable {
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    @SuppressWarnings("unused")
    @DatabaseField(generatedId = true)
    private int id;

    @SuppressWarnings("unused")
    @DatabaseField
    private String title;

    @SuppressWarnings("unused")
    @DatabaseField
    private String text;

    @SuppressWarnings("unused")
    @DatabaseField(dataType = DataType.DATE_STRING, format = DATE_FORMAT)
    private Date date;
}

