package model.beverage

import model.Model
import java.io.Serializable
import javax.persistence.Column

abstract class Beverage : Model(), Serializable {
    @Column(name="NAME") var name: String? = null
    @Column(name="BREWERY") var brewery: String? = null
    @Column(name="PERCENTAGE") var percentage: Double? = null
    @Column(name="COUNTRY") var country: String? = null
    @Column(name="STYLE") var style: String? = null
    @Column(name="COMMENT") var comment: String? = null
    @Column(name="SSCORE") var sscore: Double? = null
    @Column(name="OSCORE") var oscore: Double? = null

    companion object {
        const val CATEGORY_BEER: Int = 0
        const val CATEGORY_WINE: Int = 1
        const val CATEGORY_SAKE: Int = 2
        const val CATEGORY_WHISKEY: Int = 3
    }
}

