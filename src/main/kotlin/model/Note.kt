package model

import org.jooq.DSLContext
import org.jooq.generated.tables.Notes.NOTES
import service.DbService
import java.io.Serializable
import java.sql.Date
import javax.persistence.Column

class Note : Model(), Serializable {
    @Column(name="TITLE") var title: String? = null
    @Column(name="TEXT") var text: String? = null
    @Column(name="DATE") var date: Date? = null

    override fun save() {
        DbService.execute { context: DSLContext ->
            if (id == null) {
                context.insertInto(NOTES, NOTES.TITLE, NOTES.TEXT, NOTES.DATE)
                        .values(title, text, date)
                        .execute()
            } else {
                context.update(NOTES)
                       .set(NOTES.TITLE, title)
                       .set(NOTES.TEXT, text)
                       .set(NOTES.DATE, date)
                       .where(NOTES.ID.eq(id))
                       .execute()
            }
        }
    }

    companion object {
        fun loadAllAsRef(): List<Note> {
            return DbService.queryAll { context: DSLContext ->
                context.select(NOTES.ID, NOTES.TITLE, NOTES.DATE)
                        .from(NOTES)
                        .fetchInto(Note::class.java)
            }
        }
        fun loadById(id: Int): Note? {
            return DbService.query { context: DSLContext ->
                context.select()
                        .from(NOTES)
                        .where(NOTES.ID.eq(id))
                        .fetchAnyInto(Note::class.java)
            }
        }
        fun deleteById(id: Int) = Model.deleteById(id, NOTES, NOTES.ID)
    }

}

