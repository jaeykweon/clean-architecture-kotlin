package org.idd.cleanarchitecturekotlin.account.application.service

import org.idd.cleanarchitecturekotlin.account.domain.Money

data class MoneyTransferProperties(
    private val maximumTransferThreshold: Money = Money(1_000_000L)
) {
    fun getMaximumTransferThreshold() = this.maximumTransferThreshold
}
