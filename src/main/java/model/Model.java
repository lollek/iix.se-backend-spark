package model;

import com.j256.ormlite.field.DatabaseField;

abstract public class Model {
    @DatabaseField(generatedId = true)
    public int id;
}
