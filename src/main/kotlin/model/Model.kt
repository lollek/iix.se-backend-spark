package model

import org.jooq.*
import service.DbService
import javax.persistence.Column

abstract class Model {
    @Column(name="ID") var id: Int? = null

    abstract fun save()

    companion object {
        @JvmStatic
        protected fun deleteById(id: Int, table: Table<*>, tableField: TableField<*, Int>) {
            DbService.execute { context: DSLContext ->
                context.deleteFrom(table)
                       .where(tableField.eq(id))
                       .execute()
            }
        }
    }
}
