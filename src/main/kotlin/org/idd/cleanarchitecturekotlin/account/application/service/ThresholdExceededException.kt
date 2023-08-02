package org.idd.cleanarchitecturekotlin.account.application.service

import org.idd.cleanarchitecturekotlin.account.domain.Money

class ThresholdExceededException(
    threshold: Money,
    actual: Money
) : RuntimeException(
    "Maximum threshold for transferring money exceeded: " +
        "tried to transfer $actual but threshold is $threshold!"
)
