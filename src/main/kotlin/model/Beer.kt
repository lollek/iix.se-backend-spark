package model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

import java.io.Serializable

@DatabaseTable(tableName = "beers")
class Beer : Model(), Serializable {
    @DatabaseField var name: String? = null
    @DatabaseField var brewery: String? = null
    @DatabaseField var percentage: Double? = null
    @DatabaseField var country: String? = null
    @DatabaseField var style: String? = null
    @DatabaseField var sscore: Double? = null
    @DatabaseField var oscore: Double? = null
}
