package model

import org.jooq.DSLContext
import org.jooq.generated.tables.Beers.BEERS
import java.io.Serializable
import javax.persistence.Column

class Beer : Model(), Serializable {
    @Column(name="NAME") var name: String? = null
    @Column(name="BREWERY") var brewery: String? = null
    @Column(name="PERCENTAGE") var percentage: Double? = null
    @Column(name="COUNTRY") var country: String? = null
    @Column(name="STYLE") var style: String? = null
    @Column(name="COMMENT") var comment: String? = null
    @Column(name="SSCORE") var sscore: Double? = null
    @Column(name="OSCORE") var oscore: Double? = null

    override fun save() {
        database.Database.execute { context: DSLContext ->
            if (id == null) {
                context.insertInto(
                        BEERS, BEERS.NAME, BEERS.BREWERY, BEERS.PERCENTAGE, BEERS.COUNTRY, BEERS.STYLE, BEERS.COMMENT,
                        BEERS.SSCORE, BEERS.OSCORE)
                        .values(name, brewery, percentage, country, style, comment, sscore, oscore)
                        .execute()
            } else {
                context.update(BEERS)
                        .set(BEERS.NAME, name)
                        .set(BEERS.BREWERY, brewery)
                        .set(BEERS.PERCENTAGE, percentage)
                        .set(BEERS.COUNTRY, country)
                        .set(BEERS.STYLE, style)
                        .set(BEERS.COMMENT, comment)
                        .set(BEERS.SSCORE, sscore)
                        .set(BEERS.OSCORE, oscore)
                        .where(BEERS.ID.eq(id))
                        .execute()
            }
        }
    }
    companion object {
        fun loadById(id: Int): Beer? {
            return database.Database.query { context: DSLContext ->
                context.select()
                       .from(BEERS)
                       .where(BEERS.ID.eq(id))
                       .fetchAnyInto(Beer::class.java)
            }
        }

        fun loadAll(): List<Beer> {
            return database.Database.queryAll { context: DSLContext ->
                context.select()
                       .from(BEERS)
                       .fetchInto(Beer::class.java)
            }
        }

        fun deleteById(id: Int) = Model.deleteById(id, BEERS, BEERS.ID)
    }
}
