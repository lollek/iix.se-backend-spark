package controller

import model.Game
import service.JsonService
import service.SteamWishlistService
import spark.Request
import spark.Response
import spark.Spark

import java.time.LocalDateTime

class GamesController {
    companion object {

        fun register(endpointUrl: String) {
            Spark.get(endpointUrl, index, JsonService.gson::toJson)
        }

        private var gameListUpdate: LocalDateTime? = null
        private var gameList: List<Game> = listOf()

        val index = fun(_: Request, _: Response): List<Game> {
            synchronized(this) {
                if (gameListUpdate?.isBefore(LocalDateTime.now().minusHours(1)) ?: true) {
                    gameList = SteamWishlistService.getWishlist("loll3k")
                    gameListUpdate = LocalDateTime.now()
                }
            }
            return gameList
        }
    }
}
