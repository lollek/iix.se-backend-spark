package model

import org.jooq.*
import javax.persistence.Column

abstract class Model {
    @Column(name="ID") var id: Int? = null

    abstract fun save()

    companion object {
        @JvmStatic
        protected fun deleteById(id: Int, table: Table<*>, tableField: TableField<*, Int>) {
            database.Database.execute { context: DSLContext ->
                context.deleteFrom(table)
                       .where(tableField.eq(id))
                       .execute()
            }
        }
    }
}
