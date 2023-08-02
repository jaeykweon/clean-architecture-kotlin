package org.idd.cleanarchitecturekotlin.account.application.service

import org.idd.cleanarchitecturekotlin.account.application.port.`in`.GetAccountBalanceQuery
import org.idd.cleanarchitecturekotlin.account.application.port.out.LoadAccountPort
import org.idd.cleanarchitecturekotlin.account.domain.Account
import org.idd.cleanarchitecturekotlin.account.domain.Money
import java.time.LocalDateTime

class GetAccountBalanceService(
    private val loadAccountPort: LoadAccountPort
) : GetAccountBalanceQuery {

    override fun getAccountBalance(
        accountId: Account.AccountId,
    ): Money {
        return loadAccountPort
            .loadAccount(
                accountId = accountId,
                baselineDate = LocalDateTime.now()
            )
            .calculateBalance()
    }
}
