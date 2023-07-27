package org.idd.cleanarchitecturekotlin.account.application.port.out

import org.idd.cleanarchitecturekotlin.account.domain.Account
import java.time.LocalDateTime

interface LoadAccountPort {
    fun loadAccount(accountId: Account.AccountId, baselineDate: LocalDateTime): Account
}
