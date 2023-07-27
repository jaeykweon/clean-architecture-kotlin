package org.idd.cleanarchitecturekotlin.common

import org.idd.cleanarchitecturekotlin.account.domain.Account
import org.idd.cleanarchitecturekotlin.account.domain.ActivityWindow
import org.idd.cleanarchitecturekotlin.account.domain.Money

class AccountTestData {

    class AccountBuilder {
        private lateinit var accountId: Account.AccountId
        private lateinit var baselineBalance: Money
        private lateinit var activityWindow: ActivityWindow

        fun withAccountId(accountId: Account.AccountId): AccountBuilder {
            this.accountId = accountId
            return this
        }

        fun withBaselineBalance(baselineBalance: Money): AccountBuilder {
            this.baselineBalance = baselineBalance
            return this
        }

        fun withActivityWindow(activityWindow: ActivityWindow): AccountBuilder {
            this.activityWindow = activityWindow
            return this
        }

        fun build(): Account {
            return Account.withId(accountId, baselineBalance, activityWindow)
        }
    }

    companion object {
        @JvmStatic
        fun defaultAccount(): AccountBuilder {
            return AccountBuilder()
                .withAccountId(Account.AccountId(42L))
                .withBaselineBalance(Money(999L))
                .withActivityWindow(
                    ActivityWindow(
                        ActivityTestData.defaultActivity().build(),
                        ActivityTestData.defaultActivity().build()
                    )
                )
        }
    }
}
