package org.idd.cleanarchitecturekotlin.account.application.port.`in`

import org.idd.cleanarchitecturekotlin.account.domain.Account
import org.idd.cleanarchitecturekotlin.account.domain.Money

interface GetAccountBalanceQuery {
    fun getAccountBalance(accountId: Account.AccountId): Money
}
