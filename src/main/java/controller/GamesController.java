package controller;

import model.Game;
import service.JsonService;
import service.SteamWishlistService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GamesController {
    private static List<Game> gameList = new ArrayList<>();
    private static LocalDateTime gameListUpdate;

    private static synchronized List<Game> getGameList() {
        if (gameListUpdate == null || gameListUpdate.isBefore(LocalDateTime.now().minusHours(1))) {
            gameList = SteamWishlistService.getWishlist("loll3k");
            gameListUpdate = LocalDateTime.now();
        }
        return gameList;
    }

    public static Route index = (Request request, Response response) -> JsonService.toJson(getGameList());
}
