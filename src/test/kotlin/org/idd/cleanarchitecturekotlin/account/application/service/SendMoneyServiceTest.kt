package org.idd.cleanarchitecturekotlin.account.application.service

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe
import org.idd.cleanarchitecturekotlin.account.application.port.`in`.SendMoneyCommand
import org.idd.cleanarchitecturekotlin.account.application.port.out.AccountLock
import org.idd.cleanarchitecturekotlin.account.application.port.out.LoadAccountPort
import org.idd.cleanarchitecturekotlin.account.application.port.out.UpdateAccountStatePort
import org.idd.cleanarchitecturekotlin.account.domain.Account
import org.idd.cleanarchitecturekotlin.account.domain.Money
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.then
import org.mockito.kotlin.times
import org.mockito.kotlin.stub

class SendMoneyServiceTest : FunSpec() {

    override fun isolationMode(): IsolationMode {
        return IsolationMode.InstancePerTest
    }

    private val loadAccountPort = mock<LoadAccountPort> {}

    private val accountLock = mock<AccountLock> {}

    private val updateAccountStatePort = mock<UpdateAccountStatePort> {}

    private val sendMoneyService =
        SendMoneyService(loadAccountPort, accountLock, updateAccountStatePort, MoneyTransferProperties())

    init {
        test("withdrawal fails then only source account is locked and released") {
            val sourceAccountId = Account.AccountId(41L)
            val sourceAccount: Account = givenAnAccountWithId(sourceAccountId)
            val targetAccountId = Account.AccountId(42L)
            val targetAccount: Account = givenAnAccountWithId(targetAccountId)
            val command = SendMoneyCommand(
                sourceAccountId,
                targetAccountId,
                Money(300L)
            )
            givenWithdrawalWillFail(sourceAccount)
            givenDepositWillSucceed(targetAccount)

            val success: Boolean = sendMoneyService.sendMoney(command)
            success shouldBe false

            then(accountLock).should().lockAccount(eq(sourceAccountId))
            then(accountLock).should().releaseAccount(eq(sourceAccountId))
            then(accountLock).should(times(0)).lockAccount(eq(targetAccountId))
        }

        test("transaction succeeds") {
            val sourceAccount: Account = givenSourceAccount()
            val targetAccount: Account = givenTargetAccount()
            val money = Money(500L)
            val command = SendMoneyCommand(
                sourceAccount.getIdOrThrow(),
                targetAccount.getIdOrThrow(),
                money
            )
            givenWithdrawalWillSucceed(sourceAccount)
            givenDepositWillSucceed(targetAccount)

            val success = sendMoneyService.sendMoney(command)

            success shouldBe true
            val sourceAccountId: Account.AccountId = sourceAccount.getIdOrThrow()
            val targetAccountId: Account.AccountId = targetAccount.getIdOrThrow()
            then(accountLock).should().lockAccount(eq(sourceAccountId))
            then(sourceAccount).should().withdraw(eq(money), eq(targetAccountId))
            then(accountLock).should().releaseAccount(eq(sourceAccountId))
            then(accountLock).should().lockAccount(eq(targetAccountId))
            then(targetAccount).should().deposit(eq(money), eq(sourceAccountId))
            then(accountLock).should().releaseAccount(eq(targetAccountId))
            thenAccountsHaveBeenUpdated(sourceAccountId, targetAccountId)
        }
    }

    private fun givenAnAccountWithId(accountId: Account.AccountId): Account {
        val account = mock<Account> {
            on { getIdOrThrow() } doReturn accountId
            on { getIdOrNull() } doReturn accountId
        }
        loadAccountPort.stub {
            on { loadAccount(eq(accountId), any()) } doReturn account
        }
        return account
    }

    private fun givenSourceAccount(): Account {
        return givenAnAccountWithId(Account.AccountId(41L))
    }

    private fun givenTargetAccount(): Account {
        return givenAnAccountWithId(Account.AccountId(42L))
    }

    private fun givenWithdrawalWillSucceed(account: Account) {
        account.stub {
            on { withdraw(any(), any()) } doReturn true
        }
    }

    private fun givenWithdrawalWillFail(account: Account) {
        account.stub {
            on { withdraw(any(), any()) } doReturn false
        }
    }

    private fun givenDepositWillSucceed(account: Account) {
        account.stub {
            on { deposit(any(), any()) } doReturn true
        }
    }

    private fun thenAccountsHaveBeenUpdated(vararg accountIds: Account.AccountId) {

        val accountCaptor = argumentCaptor<Account>()

        then(updateAccountStatePort).should(times(accountIds.size))
            .updateActivities(accountCaptor.capture())

        val updatedAccountIds: List<Account.AccountId> = accountCaptor.allValues
            .map { it.getIdOrThrow() }

        updatedAccountIds shouldContainAll accountIds.toList()
    }
}
