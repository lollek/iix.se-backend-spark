package model

import java.io.Serializable

data class Book(
        var title: String?,
        var author: String?,
        var other: String?,
        var image: String?
) : Model(), Serializable {
    override fun save() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
