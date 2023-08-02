package org.idd.cleanarchitecturekotlin.account.application.port.`in`

import org.idd.cleanarchitecturekotlin.account.domain.Account
import org.idd.cleanarchitecturekotlin.account.domain.Money
import org.idd.cleanarchitecturekotlin.common.SelfValidating

data class SendMoneyCommand(
    private val sourceAccountId: Account.AccountId,
    private val targetAccountId: Account.AccountId,
    private val money: Money
) : SelfValidating<SendMoneyCommand>() {

    fun getSourceAccountId() = this.sourceAccountId
    fun getTargetAccountId() = this.targetAccountId
    fun getMoney() = this.money
}
