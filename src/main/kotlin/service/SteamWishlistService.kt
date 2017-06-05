package service

import model.Game
import org.apache.log4j.Logger
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.util.*

class SteamWishlistService {
    companion object {
        private val logger: Logger = Logger.getLogger(SteamWishlistService::class.java)

        private fun gameFromRow(row: Element): Game {
            val image: String = row
                    .select("div.gameListRowLogo").first()
                    .select("a[href]").first()
                    .select("img[src]").first()
                    .attr("src")

            val wishlistRowItem: Element = row.select("div.wishlistRowItem").first()
            val name: String = wishlistRowItem.select("h4.ellipsis").first().text()

            val gameListPriceData: Element = wishlistRowItem.select("div.gameListPriceData").first()
            val link: String = gameListPriceData
                    .select("div.storepage_btn_ctn").first()
                    .select("a[href]").first()
                    .attr("href")

            return Game(name, link, image)
        }

        fun getWishlist(username: String): List<Game> {
            try {
                return Jsoup
                        .connect("https://steamcommunity.com/id/$username/wishlist")
                        .get()
                        .select("div#wishlist_items > div.wishlistRow")
                        .map { gameFromRow(it) }
            } catch (e: Exception) {
                logger.error("Failed to parse wishlist", e)
                return ArrayList()
            }
        }
    }
}
