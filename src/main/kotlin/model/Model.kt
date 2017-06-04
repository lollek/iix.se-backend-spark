package model

import com.j256.ormlite.field.DatabaseField

abstract class Model {
    @DatabaseField(generatedId = true) var id: Int? = null
}
