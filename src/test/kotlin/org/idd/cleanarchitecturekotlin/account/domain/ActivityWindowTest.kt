package org.idd.cleanarchitecturekotlin.account.domain

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.idd.cleanarchitecturekotlin.common.ActivityTestData
import java.time.LocalDateTime

/**
 * @see ActivityWindow
 */
class ActivityWindowTest : FunSpec({

    fun startDate(): LocalDateTime {
        return LocalDateTime.of(2019, 8, 3, 0, 0)
    }

    fun inBetweenDate(): LocalDateTime {
        return LocalDateTime.of(2019, 8, 4, 0, 0)
    }

    fun endDate(): LocalDateTime {
        return LocalDateTime.of(2019, 8, 5, 0, 0)
    }

    test("calculates StartTimestamp") {
        val window = ActivityWindow(
            ActivityTestData.defaultActivity().withTimestamp(startDate()).build(),
            ActivityTestData.defaultActivity().withTimestamp(inBetweenDate()).build(),
            ActivityTestData.defaultActivity().withTimestamp(endDate()).build()
        )

        window.getStartTimestamp() shouldBe startDate()
    }

    test("calculates EndTimestamp") {
        val window = ActivityWindow(
            ActivityTestData.defaultActivity().withTimestamp(startDate()).build(),
            ActivityTestData.defaultActivity().withTimestamp(inBetweenDate()).build(),
            ActivityTestData.defaultActivity().withTimestamp(endDate()).build()
        )

        window.getEndTimestamp() shouldBe endDate()
    }

    test("calculates Balance") {
        val account1 = Account.AccountId(1L)
        val account2 = Account.AccountId(2L)

        val window = ActivityWindow(
            ActivityTestData.defaultActivity()
                .withSourceAccount(account1)
                .withTargetAccount(account2)
                .withMoney(Money(999)).build(),
            ActivityTestData.defaultActivity()
                .withSourceAccount(account1)
                .withTargetAccount(account2)
                .withMoney(Money(1)).build(),
            ActivityTestData.defaultActivity()
                .withSourceAccount(account2)
                .withTargetAccount(account1)
                .withMoney(Money(500)).build()
        )

        window.calculateBalance(account1) shouldBe Money(-500)
        window.calculateBalance(account2) shouldBe Money(500)
    }
})
