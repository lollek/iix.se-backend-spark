package model

import org.jooq.DSLContext
import org.jooq.generated.tables.MarkdownTexts.MARKDOWN_TEXTS

import java.io.Serializable
import javax.persistence.Column

class MarkdownText : Model(), Serializable {
    @Column(name="NAME") var name: String? = null
    @Column(name="DATA") var data: String? = null

    override fun save() {
        database.Database.execute { context: DSLContext ->
            if (id == null) {
                context.insertInto(MARKDOWN_TEXTS, MARKDOWN_TEXTS.NAME, MARKDOWN_TEXTS.DATA)
                        .values(name, data)
                        .execute()
            } else {
                context.update(MARKDOWN_TEXTS)
                        .set(MARKDOWN_TEXTS.NAME, name)
                        .set(MARKDOWN_TEXTS.DATA, data)
                        .where(MARKDOWN_TEXTS.ID.eq(id))
                        .execute()
            }
        }
    }
    companion object {
        fun loadByName(name: String): MarkdownText? {
            return database.Database.query { context: DSLContext ->
                context.select()
                       .from(MARKDOWN_TEXTS)
                       .where(MARKDOWN_TEXTS.NAME.eq(name))
                       .fetchAnyInto(MarkdownText::class.java)
            }
        }
    }
}
