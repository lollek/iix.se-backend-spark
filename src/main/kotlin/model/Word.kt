package model

import org.jooq.DSLContext
import service.DbService
import java.io.Serializable
import javax.persistence.Column
import org.jooq.generated.tables.Words.WORDS

class Word : Model(), Serializable {
    @Column(name = "LANGUAGE") var language: String? = null
    @Column(name = "CATEGORY") var category: String? = null
    @Column(name = "WORD") var word: String? = null
    @Column(name = "PRONOUNCIATION") var pronounciation: String? = null
    @Column(name = "TRANSLATION") var translation: String? = null

    override fun save() {
        DbService.execute { context: DSLContext ->
            if (id == null) {
                id = context.insertInto(WORDS,
                        WORDS.LANGUAGE, WORDS.CATEGORY, WORDS.WORD, WORDS.PRONOUNCIATION, WORDS.TRANSLATION)
                        .values(language, category, word, pronounciation, translation)
                        .returning(WORDS.ID)
                        .fetchOne()
                        .id
            } else {
                context.update(WORDS)
                        .set(WORDS.LANGUAGE, language)
                        .set(WORDS.CATEGORY, category)
                        .set(WORDS.WORD, word)
                        .set(WORDS.PRONOUNCIATION, pronounciation)
                        .set(WORDS.TRANSLATION, translation)
                        .where(WORDS.ID.eq(id))
                        .execute()
            }
        }
    }

    companion object {
        fun loadById(id: Int): Word? {
            return DbService.query { context: DSLContext ->
                context.select()
                        .from(WORDS)
                        .where(WORDS.ID.eq(id))
                        .fetchAnyInto(Word::class.java)
            }
        }

        fun loadAll(language: String?, category: String?): List<Word> {
            return DbService.queryAll { context: DSLContext ->
                val query = context.selectQuery()
                query.addFrom(WORDS)
                if (language != null) {
                    query.addConditions(WORDS.LANGUAGE.eq(language))
                }
                if (category != null) {
                    query.addConditions(WORDS.CATEGORY.eq(category))
                }
                query.fetchInto(Word::class.java)
            }
        }

        fun deleteById(id: Int) = deleteById(id, WORDS, WORDS.ID)
    }
}

