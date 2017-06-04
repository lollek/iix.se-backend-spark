package model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

import java.io.Serializable
import java.util.Date

@DatabaseTable(tableName = "notes")
class Note : Model(), Serializable {
    companion object {
        const val DATE_FORMAT: String = "yyyy-MM-dd"
    }

    @DatabaseField var title: String? = null
    @DatabaseField var text: String? = null
    @DatabaseField(format = DATE_FORMAT) var date: Date? = null
}

