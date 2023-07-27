package org.idd.cleanarchitecturekotlin.account.domain

import java.math.BigInteger

data class Money(
    private val amount: BigInteger
) {

    constructor(amount: Long) : this(amount.toBigInteger())

    fun getAmount() = this.amount

    fun isPositiveOrZero(): Boolean {
        return this.amount >= BigInteger.ZERO
    }

    fun isNegative(): Boolean {
        return this.amount < BigInteger.ZERO
    }

    fun isPositive(): Boolean {
        return this.amount > BigInteger.ZERO
    }

    fun isGreaterThanOrEqualTo(money: Money): Boolean {
        return this.amount >= money.amount
    }

    fun isGreaterThan(money: Money): Boolean {
        return this.amount > money.amount
    }

    operator fun minus(money: Money): Money {
        return Money(this.amount - money.amount)
    }

    operator fun plus(money: Money): Money {
        return Money(this.amount + money.amount)
    }

    fun negate(): Money {
        return Money(amount.negate())
    }

    companion object {
        val ZERO = Money(0L)

        @JvmStatic
        fun add(a: Money, b: Money): Money {
            return Money(a.amount.add(b.amount))
        }

        @JvmStatic
        fun subtract(a: Money, b: Money): Money {
            return Money(a.amount.subtract(b.amount))
        }
    }
}
