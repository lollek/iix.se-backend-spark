package service;

import model.Game;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SteamWishlistService {

    private static Game wishlistRow2Game(Element wishlistRow) {
        final Element wishlistRowItem = wishlistRow.select("div.wishlistRowItem").first();
        final Element gameListPriceData = wishlistRowItem.select("div.gameListPriceData").first();

        final String name = wishlistRowItem.select("h4.ellipsis").first().text();
        final String image = wishlistRow
                .select("div.gameListRowLogo").first()
                .select("a[href]").first()
                .select("img[src]").first()
                .attr("src");
        final String link = gameListPriceData
                .select("div.storepage_btn_ctn").first()
                .select("a[href]").first()
                .attr("href");

        return new Game(name, link, image);
    }

    public static List<Game> getWishlist(String username) {
        try {
            return Jsoup
                    .connect("https://steamcommunity.com/id/" + username + "/wishlist")
                    .get()
                    .select("div#wishlist_items > div.wishlistRow")
                    .stream().map(SteamWishlistService::wishlistRow2Game).collect(Collectors.toList());
        } catch (Exception e) {
            LogService.logException(e);
            return new ArrayList<>();
        }
    }
}
