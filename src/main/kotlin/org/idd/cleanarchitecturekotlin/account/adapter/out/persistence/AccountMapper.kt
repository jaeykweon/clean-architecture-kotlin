package org.idd.cleanarchitecturekotlin.account.adapter.out.persistence

import org.idd.cleanarchitecturekotlin.account.domain.Account
import org.idd.cleanarchitecturekotlin.account.domain.Activity
import org.idd.cleanarchitecturekotlin.account.domain.ActivityWindow
import org.idd.cleanarchitecturekotlin.account.domain.Money
import org.springframework.stereotype.Component

@Component
class AccountMapper {

    fun mapToDomainEntity(
        account: AccountJpaEntity,
        activities: List<ActivityJpaEntity>,
        withdrawalBalance: Long,
        depositBalance: Long
    ): Account {
        val baselineBalance: Money = Money(depositBalance) - Money(withdrawalBalance)

        return Account.withId(
            Account.AccountId(account.getId()),
            baselineBalance,
            mapToActivityWindow(activities)
        )
    }

    fun mapToActivityWindow(activities: List<ActivityJpaEntity>): ActivityWindow {
        val mappedActivities: MutableList<Activity> = ArrayList()
        for (activity in activities) {
            mappedActivities.add(
                Activity(
                    Activity.ActivityId(activity.getId()!!),
                    Account.AccountId(activity.getOwnerAccountId()),
                    Account.AccountId(activity.getSourceAccountId()),
                    Account.AccountId(activity.getTargetAccountId()),
                    activity.getTimestamp(),
                    Money(activity.getAmount())
                )
            )
        }
        return ActivityWindow(mappedActivities)
    }

    fun mapToJpaEntity(activity: Activity): ActivityJpaEntity {
        return ActivityJpaEntity(
            activity.getIdOrNull()?.getValue(),
            activity.getTimestamp(),
            activity.getOwnerAccountId().getValue(),
            activity.getSourceAccountId().getValue(),
            activity.getTargetAccountId().getValue(),
            activity.getMoney().getAmount().toLong()
        )
    }
}
