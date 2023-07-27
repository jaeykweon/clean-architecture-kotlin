package org.idd.cleanarchitecturekotlin.account.application.port.out

import org.idd.cleanarchitecturekotlin.account.domain.Account

interface UpdateAccountStatePort {
    fun updateActivities(account: Account)
}
