package org.idd.cleanarchitecturekotlin.account.domain

import java.time.LocalDateTime

/**
 * A window of account activities.
 */
class ActivityWindow(
    /**
     * The list of account activities within this window.
     */
    private val activities: MutableList<Activity>
) {

    constructor(vararg _activities: Activity) : this(_activities.map { it }.toMutableList())

    /**
     * The timestamp of the first activity within this window.
     */
    fun getStartTimestamp(): LocalDateTime {
        return activities.minOf { it.getTimeStamp() }
    }
    /**
     * The timestamp of the last activity within this window.
     * @return
     */
    fun getEndTimestamp(): LocalDateTime {
        return activities.maxOf { it.getTimeStamp() }
    }

    /**
     * Calculates the balance by summing up the values of all activities within this window.
     */
    fun calculateBalance(accountId: Account.AccountId): Money {
        val depositBalance: Money =
            activities
                .filter { it.getTargetAccountId() == accountId }
                .map { it.getMoney() }
                .fold(Money.ZERO) { total, next -> total + next }

        val withdrawalBalance: Money =
            activities
                .filter { it.getSourceAccountId() == accountId }
                .map { it.getMoney() }
                .fold(Money.ZERO) { total, next -> total + next }

        return Money.add(depositBalance, withdrawalBalance.negate())
    }

    fun getActivities(): List<Activity> {
        return activities.toList()
    }

    fun addActivity(activity: Activity) {
        activities.add(activity)
    }
}
