package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "markdown_texts")
public class MarkdownText extends Model implements Serializable {
    public static final String NAME_FIELD_NAME = "name";
    public static final String ID_FIELD_NAME = "id";

    @SuppressWarnings("unused")
    @DatabaseField(columnName = NAME_FIELD_NAME)
    private String name;

    @SuppressWarnings("unused")
    @DatabaseField
    private String data;

}
