package org.idd.cleanarchitecturekotlin.account.application.port.`in`

interface SendMoneyUseCase {
    fun sendMoney(command: SendMoneyCommand): Boolean
}
