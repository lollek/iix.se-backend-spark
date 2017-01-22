package controller;

import service.JsonService;
import service.SteamWishlistService;
import spark.Request;
import spark.Response;
import spark.Route;

public class GamesController {
    public static Route index = (Request request, Response response) -> JsonService.toJson(SteamWishlistService.getWishlist("loll3k"));
}
