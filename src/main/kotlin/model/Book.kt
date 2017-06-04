package model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

import java.io.Serializable

@DatabaseTable(tableName = "books")
class Book : Model(), Serializable{
    @DatabaseField var title: String? = null
    @DatabaseField var author: String? = null
    @DatabaseField var other: String? = null
    @DatabaseField var image: String? = null
}
