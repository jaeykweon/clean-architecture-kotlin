package org.idd.cleanarchitecturekotlin.account.application.port.out

import org.idd.cleanarchitecturekotlin.account.domain.Account

interface AccountLock {
    fun lockAccount(accountId: Account.AccountId)

    fun releaseAccount(accountId: Account.AccountId)
}
