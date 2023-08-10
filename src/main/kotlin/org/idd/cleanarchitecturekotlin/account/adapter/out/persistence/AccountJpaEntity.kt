package org.idd.cleanarchitecturekotlin.account.adapter.out.persistence

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Table(name = "account")
@Entity
class AccountJpaEntity(
    @Id
    @GeneratedValue
    private val id: Long
) {
    fun getId() = this.id
}
