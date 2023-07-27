package org.idd.cleanarchitecturekotlin.account.domain

import java.time.LocalDateTime

/**
 * A money transfer activity between [Account]s.
 */
data class Activity(
    private val id: ActivityId?,
    /**
     * The account that owns this activity.
     */
    private val ownerAccountId: Account.AccountId,
    /**
     * The debited account.
     */
    private val sourceAccountId: Account.AccountId,
    /**
     * The credited account.
     */
    private val targetAccountId: Account.AccountId,
    /**
     * The timestamp of the activity.
     */
    private val timestamp: LocalDateTime,
    /**
     * The money that was transferred between the accounts.
     */
    private val money: Money
) {

    data class ActivityId(
        private val value: Long
    ) {
        fun getValue() = this.value
    }

    fun getSourceAccountId(): Account.AccountId {
        return this.sourceAccountId
    }

    fun getTargetAccountId(): Account.AccountId {
        return this.targetAccountId
    }

    fun getTimeStamp(): LocalDateTime {
        return this.timestamp
    }

    fun getMoney(): Money {
        return this.money
    }
}
