package model

import javax.persistence.Column

class Group : Model() {
    @Column(name="NAME") var name: String? = null

    override fun save() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
