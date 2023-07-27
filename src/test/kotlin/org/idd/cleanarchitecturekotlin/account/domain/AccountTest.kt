package org.idd.cleanarchitecturekotlin.account.domain

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.idd.cleanarchitecturekotlin.common.AccountTestData
import org.idd.cleanarchitecturekotlin.common.ActivityTestData

/**
 * @see Account
 */
class AccountTest : FunSpec({

    test("calculates Balance") {
        val accountId = Account.AccountId(1L)
        val account: Account = AccountTestData.defaultAccount()
            .withAccountId(accountId)
            .withBaselineBalance(Money(555L))
            .withActivityWindow(
                ActivityWindow(
                    ActivityTestData.defaultActivity()
                        .withTargetAccount(accountId)
                        .withMoney(Money(999L)).build(),
                    ActivityTestData.defaultActivity()
                        .withTargetAccount(accountId)
                        .withMoney(Money(1L)).build()
                )
            )
            .build()

        val balance = account.calculateBalance()

        balance shouldBe Money(1555L)
    }

    test("withdrawal Succeeds") {
        val accountId = Account.AccountId(1L)
        val account: Account = AccountTestData.defaultAccount()
            .withAccountId(accountId)
            .withBaselineBalance(Money(555L))
            .withActivityWindow(
                ActivityWindow(
                    ActivityTestData.defaultActivity()
                        .withTargetAccount(accountId)
                        .withMoney(Money(999L)).build(),
                    ActivityTestData.defaultActivity()
                        .withTargetAccount(accountId)
                        .withMoney(Money(1L)).build()
                )
            )
            .build()

        val success = account.withdraw(Money(555L), Account.AccountId(99L))

        success shouldBe true
        account.getActivityWindow().getActivities().size shouldBe 3
        account.calculateBalance() shouldBe Money(1000L)
    }

    test("withdrawal Failure") {
        val accountId = Account.AccountId(1L)
        val account = AccountTestData.defaultAccount()
            .withAccountId(accountId)
            .withBaselineBalance(Money(555L))
            .withActivityWindow(
                ActivityWindow(
                    ActivityTestData.defaultActivity()
                        .withTargetAccount(accountId)
                        .withMoney(Money(999L)).build(),
                    ActivityTestData.defaultActivity()
                        .withTargetAccount(accountId)
                        .withMoney(Money(1L)).build()
                )
            )
            .build()

        val success = account.withdraw(Money(1556L), Account.AccountId(99L))

        success shouldBe false
        account.getActivityWindow().getActivities().size shouldBe 2
        account.calculateBalance() shouldBe Money(1555L)
    }

    test("deposit Success") {
        val accountId = Account.AccountId(1L)
        val account = AccountTestData.defaultAccount()
            .withAccountId(accountId)
            .withBaselineBalance(Money(555L))
            .withActivityWindow(
                ActivityWindow(
                    ActivityTestData.defaultActivity()
                        .withTargetAccount(accountId)
                        .withMoney(Money(999L)).build(),
                    ActivityTestData.defaultActivity()
                        .withTargetAccount(accountId)
                        .withMoney(Money(1L)).build()
                )
            )
            .build()

        val success = account.deposit(Money(445L), Account.AccountId(99L))

        success shouldBe true
        account.getActivityWindow().getActivities().size shouldBe 3
        account.calculateBalance() shouldBe Money(2000L)
    }
})
