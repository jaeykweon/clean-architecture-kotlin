package org.idd.cleanarchitecturekotlin.account.adapter.out.persistence

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Table(name = "activity")
@Entity
class ActivityJpaEntity(
    @Id
    @GeneratedValue
    private val id: Long?,
    private val timestamp: LocalDateTime,
    private val ownerAccountId: Long,
    private val sourceAccountId: Long,
    private val targetAccountId: Long,
    private val amount: Long
) {
    fun getId() = this.id
    fun getTimestamp() = this.timestamp
    fun getOwnerAccountId() = this.ownerAccountId
    fun getSourceAccountId() = this.sourceAccountId
    fun getTargetAccountId() = this.targetAccountId
    fun getAmount() = this.amount
}
