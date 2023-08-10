package org.idd.cleanarchitecturekotlin.account.adapter.out.persistence

import org.idd.cleanarchitecturekotlin.account.application.port.out.LoadAccountPort
import org.idd.cleanarchitecturekotlin.account.application.port.out.UpdateAccountStatePort
import org.idd.cleanarchitecturekotlin.account.domain.Account
import org.idd.cleanarchitecturekotlin.common.PersistenceAdapter
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDateTime
import javax.persistence.EntityNotFoundException

@PersistenceAdapter
class AccountPersistenceAdapter(
    private val accountRepository: SpringDataAccountRepository,
    private val activityRepository: ActivityRepository,
    private val accountMapper: AccountMapper
) : LoadAccountPort, UpdateAccountStatePort {

    override fun loadAccount(
        accountId: Account.AccountId,
        baselineDate: LocalDateTime
    ): Account {
        val account = accountRepository.findByIdOrNull(accountId.getValue())
            ?: throw EntityNotFoundException()

        val activities: List<ActivityJpaEntity> =
            activityRepository.findByOwnerSince(
                accountId.getValue(),
                baselineDate
            )
        val withdrawalBalance: Long =
            activityRepository
                .getWithdrawalBalanceUntil(
                    accountId.getValue(),
                    baselineDate
                )
        val depositBalance: Long =
            activityRepository
                .getDepositBalanceUntil(
                    accountId.getValue(),
                    baselineDate
                )
        return accountMapper.mapToDomainEntity(
            account,
            activities,
            withdrawalBalance,
            depositBalance
        )
    }

    override fun updateActivities(account: Account) {
        for (activity in account.getActivityWindow().getActivities()) {
            if (activity.getIdOrNull() == null) {
                activityRepository.save(accountMapper.mapToJpaEntity(activity))
            }
        }
    }
}
