package org.idd.cleanarchitecturekotlin.account.adapter.out.persistence

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import org.idd.cleanarchitecturekotlin.account.domain.Account
import org.idd.cleanarchitecturekotlin.account.domain.ActivityWindow
import org.idd.cleanarchitecturekotlin.account.domain.Money
import org.idd.cleanarchitecturekotlin.common.AccountTestData.Companion.defaultAccount
import org.idd.cleanarchitecturekotlin.common.ActivityTestData.Companion.defaultActivity
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.jdbc.Sql
import java.time.LocalDateTime

@DataJpaTest
@Import(AccountPersistenceAdapter::class, AccountMapper::class)
class AccountPersistenceAdapterTest(
    private val adapterUnderTest: AccountPersistenceAdapter,
    private val activityRepository: ActivityRepository
) : AnnotationSpec() {

    override fun extensions() = listOf(SpringExtension)

    init {
        @Test
        @Sql("AccountPersistenceAdapterTest.sql")
        fun loadsAccount() {
            val account: Account =
                adapterUnderTest.loadAccount(
                    Account.AccountId(1L),
                    LocalDateTime.of(2018, 8, 10, 0, 0)
                )

            account.getActivityWindow().getActivities().size shouldBe 2
            account.calculateBalance() shouldBe Money(500)
        }

        @Test
        fun updatesActivities() {
            val account: Account = defaultAccount()
                .withBaselineBalance(Money(555L))
                .withActivityWindow(
                    ActivityWindow(
                        defaultActivity()
                            .withMoney(Money(1L)).build()
                    )
                )
                .build()
            adapterUnderTest.updateActivities(account)
            activityRepository.count() shouldBe 1

            val savedActivity = activityRepository.findAll()[0]
            savedActivity.getAmount() shouldBe 1L
        }
    }
}
