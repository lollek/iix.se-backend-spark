package model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

import java.io.Serializable

@DatabaseTable(tableName = "markdown_texts")
class MarkdownText : Model(), Serializable {
    @DatabaseField var name: String? = null
    @DatabaseField var data: String? = null
}
