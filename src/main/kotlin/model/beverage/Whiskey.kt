package model.beverage

import org.jooq.DSLContext
import org.jooq.generated.tables.Beverages.BEVERAGES
import java.io.Serializable

class Whiskey : Beverage(), Serializable {

    override fun save() {
        database.Database.execute { context: DSLContext ->
            if (id == null) {
                context.insertInto(
                        BEVERAGES, BEVERAGES.NAME, BEVERAGES.BREWERY, BEVERAGES.PERCENTAGE, BEVERAGES.COUNTRY,
                        BEVERAGES.STYLE, BEVERAGES.COMMENT, BEVERAGES.SSCORE, BEVERAGES.OSCORE,
                        BEVERAGES.CATEGORY)
                        .values(name, brewery, percentage, country, style, comment, sscore, oscore, CATEGORY_WHISKEY)
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
                        .where(BEVERAGES.ID.eq(id))
                        .execute()
            }
        }
    }
    companion object {
        fun loadById(id: Int): Whiskey? {
            return database.Database.query { context: DSLContext ->
                context.select()
                       .from(BEVERAGES)
                       .where(BEVERAGES.ID.eq(id))
                       .and(BEVERAGES.CATEGORY.eq(CATEGORY_WHISKEY))
                       .fetchAnyInto(Whiskey::class.java)
            }
        }

        fun loadAll(): List<Whiskey> {
            return database.Database.queryAll { context: DSLContext ->
                context.select()
                       .from(BEVERAGES)
                       .where(BEVERAGES.CATEGORY.eq(CATEGORY_WHISKEY))
                       .fetchInto(Whiskey::class.java)
            }
        }

        fun deleteById(id: Int) = deleteById(id, BEVERAGES, BEVERAGES.ID)
    }
}
