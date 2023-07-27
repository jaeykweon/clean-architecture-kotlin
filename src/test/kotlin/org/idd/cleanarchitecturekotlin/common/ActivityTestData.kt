package org.idd.cleanarchitecturekotlin.common

import org.idd.cleanarchitecturekotlin.account.domain.Account
import org.idd.cleanarchitecturekotlin.account.domain.Activity
import org.idd.cleanarchitecturekotlin.account.domain.Money
import java.time.LocalDateTime

class ActivityTestData {

    class ActivityBuilder {
        private var id: Activity.ActivityId? = null
        private lateinit var ownerAccountId: Account.AccountId
        private lateinit var sourceAccountId: Account.AccountId
        private lateinit var targetAccountId: Account.AccountId
        private lateinit var timestamp: LocalDateTime
        private lateinit var money: Money

        fun withId(id: Activity.ActivityId): ActivityBuilder {
            this.id = id
            return this
        }

        fun withOwnerAccount(accountId: Account.AccountId): ActivityBuilder {
            this.ownerAccountId = accountId
            return this
        }

        fun withSourceAccount(accountId: Account.AccountId): ActivityBuilder {
            this.sourceAccountId = accountId
            return this
        }

        fun withTargetAccount(accountId: Account.AccountId): ActivityBuilder {
            this.targetAccountId = accountId
            return this
        }

        fun withTimestamp(timestamp: LocalDateTime): ActivityBuilder {
            this.timestamp = timestamp
            return this
        }

        fun withMoney(money: Money): ActivityBuilder {
            this.money = money
            return this
        }

        fun build(): Activity {
            return Activity(
                id,
                ownerAccountId,
                sourceAccountId,
                targetAccountId,
                timestamp,
                money
            )
        }
    }

    companion object {
        @JvmStatic
        fun defaultActivity(): ActivityBuilder {
            return ActivityBuilder()
                .withOwnerAccount(Account.AccountId(42L))
                .withSourceAccount(Account.AccountId(42L))
                .withTargetAccount(Account.AccountId(41L))
                .withTimestamp(LocalDateTime.now())
                .withMoney(Money(999L))
        }
    }
}
