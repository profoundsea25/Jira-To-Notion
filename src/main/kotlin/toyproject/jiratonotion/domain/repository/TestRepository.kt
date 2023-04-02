package toyproject.jiratonotion.domain.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import toyproject.jiratonotion.domain.entity.TestEntity

@Repository
interface TestRepository : JpaRepository<TestEntity, Long>