package org.idd.cleanarchitecturekotlin.account.application.port.`in`

import org.idd.cleanarchitecturekotlin.account.domain.Account
import org.idd.cleanarchitecturekotlin.account.domain.Money
import java.time.LocalDateTime

interface GetAccountBalanceQuery {
    fun getAccountBalance(
        accountId: Account.AccountId,
        baselineTime: LocalDateTime = LocalDateTime.now()
    ): Money
}
