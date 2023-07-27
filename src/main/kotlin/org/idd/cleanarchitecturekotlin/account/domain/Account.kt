package org.idd.cleanarchitecturekotlin.account.domain

import java.time.LocalDateTime

/**
 * An account that holds a certain amount of money. An {@link Account} object only
 * contains a window of the latest account activities. The total balance of the account is
 * the sum of a baseline balance that was valid before the first activity in the
 * window and the sum of the activity values.
 */
class Account(
    /**
     * The unique ID of the account.
     */
    private val id: AccountId?,

    /**
     * The baseline balance of the account. This was the balance of the account before the first
     * activity in the activityWindow.
     */
    private val baselineBalance: Money,

    /**
     * The window of latest activities on this account.
     */
    private val activityWindow: ActivityWindow
) {

    fun getId(): AccountId {
        return this.id ?: throw IllegalStateException()
    }

    fun getActivityWindow(): ActivityWindow {
        return this.activityWindow
    }

    /**
     * Calculates the total balance of the account by adding the activity values to the baseline balance.
     */
    fun calculateBalance(): Money {
        require(this.id != null) { "id must not be null" }
        return Money.add(
            baselineBalance,
            activityWindow.calculateBalance(id)
        )
    }

    /**
     * Tries to withdraw a certain amount of money from this account.
     * If successful, creates a new activity with a negative value.
     * @return true if the withdrawal was successful, false if not.
     */
    fun withdraw(money: Money, targetAccountId: AccountId): Boolean {
        require(this.id != null) { "id must not be null" }
        if (!mayWithdraw(money)) {
            return false
        }
        val withdrawal = Activity(
            id = null,
            ownerAccountId = this.id,
            sourceAccountId = this.id,
            targetAccountId = targetAccountId,
            timestamp = LocalDateTime.now(),
            money = money
        )
        activityWindow.addActivity(withdrawal)
        return true
    }

    private fun mayWithdraw(money: Money): Boolean {
        return Money.add(
            calculateBalance(),
            money.negate()
        ).isPositiveOrZero()
    }

    /**
     * Tries to deposit a certain amount of money to this account.
     * If sucessful, creates a new activity with a positive value.
     * @return true if the deposit was successful, false if not.
     */
    fun deposit(money: Money, sourceAccountId: AccountId): Boolean {
        require(this.id != null) { "id must not be null" }
        val deposit = Activity(
            id = null,
            ownerAccountId = this.id,
            sourceAccountId = sourceAccountId,
            targetAccountId = this.id,
            timestamp = LocalDateTime.now(),
            money = money
        )
        this.activityWindow.addActivity(deposit)
        return true
    }

    data class AccountId(
        private val value: Long
    ) {
        fun getValue() = this.value
    }

    companion object {
        /**
         * Creates an [Account] entity without an ID. Use to create a new entity that is not yet
         * persisted.
         */
        fun withoutId(
            baselineBalance: Money,
            activityWindow: ActivityWindow
        ): Account {
            return Account(null, baselineBalance, activityWindow)
        }

        /**
         * Creates an [Account] entity with an ID. Use to reconstitute a persisted entity.
         */
        fun withId(
            accountId: AccountId,
            baselineBalance: Money,
            activityWindow: ActivityWindow
        ): Account {
            return Account(accountId, baselineBalance, activityWindow)
        }
    }
}
