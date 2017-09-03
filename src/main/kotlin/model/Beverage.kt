package model

import org.jooq.DSLContext
import service.DbService
import java.io.Serializable
import javax.persistence.Column
import org.jooq.generated.tables.Beverages.BEVERAGES

class Beverage : Model(), Serializable {
    @Column(name = "NAME") var name: String? = null
    @Column(name = "BREWERY") var brewery: String? = null
    @Column(name = "PERCENTAGE") var percentage: Double? = null
    @Column(name = "COUNTRY") var country: String? = null
    @Column(name = "STYLE") var style: String? = null
    @Column(name = "COMMENT") var comment: String? = null
    @Column(name = "SSCORE") var sscore: Double? = null
    @Column(name = "OSCORE") var oscore: Double? = null
    @Column(name = "CATEGORY") var category: Int? = null


    fun validateBeforeSave() {
        if (category == null) {
            category = Category.BEER.ordinal
        }
    }

    override fun save() {
        validateBeforeSave()

        DbService.execute { context: DSLContext ->
            if (id == null) {
                context.insertInto(
                        BEVERAGES, BEVERAGES.NAME, BEVERAGES.BREWERY, BEVERAGES.PERCENTAGE, BEVERAGES.COUNTRY,
                        BEVERAGES.STYLE, BEVERAGES.COMMENT, BEVERAGES.SSCORE, BEVERAGES.OSCORE,
                        BEVERAGES.CATEGORY)
                        .values(name, brewery, percentage, country, style, comment, sscore, oscore, category)
                        .execute()
            } else {
                context.update(BEVERAGES)
                        .set(BEVERAGES.NAME, name)
                        .set(BEVERAGES.BREWERY, brewery)
                        .set(BEVERAGES.PERCENTAGE, percentage)
                        .set(BEVERAGES.COUNTRY, country)
                        .set(BEVERAGES.STYLE, style)
                        .set(BEVERAGES.COMMENT, comment)
                        .set(BEVERAGES.SSCORE, sscore)
                        .set(BEVERAGES.OSCORE, oscore)
                        .set(BEVERAGES.CATEGORY, category)
                        .where(BEVERAGES.ID.eq(id))
                        .execute()
            }
        }
    }

    companion object {
        enum class Category(val id: Int) {
            BEER(0),
            WINE(1),
            SAKE(2),
            WHISKEY(3);
        }

        fun loadById(id: Int): Beverage? {
            return DbService.query { context: DSLContext ->
                context.select()
                        .from(BEVERAGES)
                        .where(BEVERAGES.ID.eq(id))
                        .fetchAnyInto(Beverage::class.java)
            }
        }

        fun loadAll(category: Category): List<Beverage> {
            return DbService.queryAll { context: DSLContext ->
                context.select()
                        .from(BEVERAGES)
                        .where(BEVERAGES.CATEGORY.eq(category.ordinal))
                        .fetchInto(Beverage::class.java)
            }
        }

        fun deleteById(id: Int) = deleteById(id, BEVERAGES, BEVERAGES.ID)
    }
}

