package org.idd.cleanarchitecturekotlin.account.adapter.out.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface SpringDataAccountRepository : JpaRepository<AccountJpaEntity, Long>
