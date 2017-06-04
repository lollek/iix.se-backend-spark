package model

import java.io.Serializable

data class Game(
        var name: String,
        var link: String,
        var image: String
) : Model(), Serializable
