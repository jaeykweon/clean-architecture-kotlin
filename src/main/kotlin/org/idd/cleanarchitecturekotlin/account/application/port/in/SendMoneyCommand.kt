package org.idd.cleanarchitecturekotlin.account.application.port.`in`

import org.idd.cleanarchitecturekotlin.account.domain.Account
import org.idd.cleanarchitecturekotlin.account.domain.Money
import org.idd.cleanarchitecturekotlin.common.SelfValidating

data class SendMoneyCommand(
    val sourceAccountId: Account.AccountId,
    val targetAccountId: Account.AccountId,
    val money: Money
) : SelfValidating<SendMoneyCommand>()
