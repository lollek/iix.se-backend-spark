package model

import java.io.Serializable

data class Game(
        var name: String,
        var link: String,
        var image: String
) : Model(), Serializable {
    override fun save() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
