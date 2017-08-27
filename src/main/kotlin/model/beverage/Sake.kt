package model.beverage

import org.jooq.DSLContext
import org.jooq.generated.tables.Beverages.BEVERAGES
import service.DbService
import java.io.Serializable

class Sake : Beverage(), Serializable {

    override fun save() {
        DbService.execute { context: DSLContext ->
            if (id == null) {
                context.insertInto(
                        BEVERAGES, BEVERAGES.NAME, BEVERAGES.BREWERY, BEVERAGES.PERCENTAGE, BEVERAGES.COUNTRY,
                        BEVERAGES.STYLE, BEVERAGES.COMMENT, BEVERAGES.SSCORE, BEVERAGES.OSCORE,
                        BEVERAGES.CATEGORY)
                        .values(name, brewery, percentage, country, style, comment, sscore, oscore, CATEGORY_SAKE)
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
        fun loadById(id: Int): Sake? {
            return DbService.query { context: DSLContext ->
                context.select()
                       .from(BEVERAGES)
                       .where(BEVERAGES.ID.eq(id))
                       .and(BEVERAGES.CATEGORY.eq(CATEGORY_SAKE))
                       .fetchAnyInto(Sake::class.java)
            }
        }

        fun loadAll(): List<Sake> {
            return DbService.queryAll { context: DSLContext ->
                context.select()
                       .from(BEVERAGES)
                       .where(BEVERAGES.CATEGORY.eq(CATEGORY_SAKE))
                       .fetchInto(Sake::class.java)
            }
        }

        fun deleteById(id: Int) = deleteById(id, BEVERAGES, BEVERAGES.ID)
    }
}
