package model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;

@DatabaseTable(tableName = "notes")
public class Note extends Model implements Serializable {
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    @SuppressWarnings("unused")
    @DatabaseField
    public String title;

    @SuppressWarnings("unused")
    @DatabaseField
    public String text;

    @SuppressWarnings("unused")
    @DatabaseField(dataType = DataType.DATE_STRING, format = DATE_FORMAT)
    public Date date;
}

