package org.idd.cleanarchitecturekotlin.account.adapter.out.persistence

import org.idd.cleanarchitecturekotlin.account.application.port.out.AccountLock
import org.idd.cleanarchitecturekotlin.account.domain.Account
import org.springframework.stereotype.Component

@Component
class NoOpAccountLock : AccountLock {
    override fun lockAccount(accountId: Account.AccountId) {
        // do nothing
    }

    override fun releaseAccount(accountId: Account.AccountId) {
        // do nothing
    }
}
