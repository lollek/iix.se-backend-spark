package controller

import model.Game
import service.JsonService
import service.SteamWishlistService
import spark.Request
import spark.Response
import spark.Spark

import java.time.LocalDateTime
import java.util.*

class GamesController {
    companion object {
        fun register(endpointUrl: String) {
            Spark.get(endpointUrl, index)
        }

        private var gameListUpdate: LocalDateTime? = null
        private var gameList: List<Game> = ArrayList()
            get() {
                if (gameListUpdate == null || gameListUpdate?.isBefore(LocalDateTime.now().minusHours(1)) ?: true) {
                    field = SteamWishlistService.getWishlist("loll3k")
                    gameListUpdate = LocalDateTime.now()
                }
                return field
            }

        val index = fun(_: Request, _: Response): String {
             return JsonService.toJson(gameList)
        }

    }


}
