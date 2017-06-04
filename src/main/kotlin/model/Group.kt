package model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "groups")
class Group : Model() {
    @DatabaseField var name: String? = null
}
