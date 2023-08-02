package org.idd.cleanarchitecturekotlin.account.application.service

import org.idd.cleanarchitecturekotlin.account.application.port.`in`.SendMoneyCommand
import org.idd.cleanarchitecturekotlin.account.application.port.`in`.SendMoneyUseCase
import org.idd.cleanarchitecturekotlin.account.application.port.out.AccountLock
import org.idd.cleanarchitecturekotlin.account.application.port.out.LoadAccountPort
import org.idd.cleanarchitecturekotlin.account.application.port.out.UpdateAccountStatePort
import org.idd.cleanarchitecturekotlin.account.domain.Account
import org.idd.cleanarchitecturekotlin.common.UseCase
import java.time.LocalDateTime
import javax.transaction.Transactional

@UseCase
@Transactional
class SendMoneyService(
    private val loadAccountPort: LoadAccountPort,
    private val accountLock: AccountLock,
    private val updateAccountStatePort: UpdateAccountStatePort,
    private val moneyTransferProperties: MoneyTransferProperties,
) : SendMoneyUseCase {

    override fun sendMoney(command: SendMoneyCommand): Boolean {
        checkThreshold(command)

        val baselineDate = LocalDateTime.now().minusDays(10)

        val sourceAccount: Account = loadAccountPort.loadAccount(
            command.getSourceAccountId(),
            baselineDate
        )

        val targetAccount: Account = loadAccountPort.loadAccount(
            command.getTargetAccountId(),
            baselineDate
        )

        val sourceAccountId: Account.AccountId = sourceAccount.getIdOrNull()
            ?: throw IllegalStateException("expected source account ID not to be empty")

        val targetAccountId: Account.AccountId = targetAccount.getIdOrNull()
            ?: throw IllegalStateException("expected target account ID not to be empty")

        accountLock.lockAccount(sourceAccountId)
        if (!sourceAccount.withdraw(command.getMoney(), targetAccountId)) {
            accountLock.releaseAccount(sourceAccountId)
            return false
        }

        accountLock.lockAccount(targetAccountId)
        if (!targetAccount.deposit(command.getMoney(), sourceAccountId)) {
            accountLock.releaseAccount(sourceAccountId)
            accountLock.releaseAccount(targetAccountId)
            return false
        }

        updateAccountStatePort.updateActivities(sourceAccount)
        updateAccountStatePort.updateActivities(targetAccount)

        accountLock.releaseAccount(sourceAccountId)
        accountLock.releaseAccount(targetAccountId)
        return true
    }

    private fun checkThreshold(command: SendMoneyCommand) {
        if (command.getMoney().isGreaterThan(moneyTransferProperties.getMaximumTransferThreshold())) {
            throw ThresholdExceededException(moneyTransferProperties.getMaximumTransferThreshold(), command.getMoney())
        }
    }
}
