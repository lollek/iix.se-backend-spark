package model;

import com.j256.ormlite.field.DatabaseField;

abstract public class Model {
    public static final String ID_FIELD_NAME = "id";

    @DatabaseField(generatedId = true)
    public int id;
}
