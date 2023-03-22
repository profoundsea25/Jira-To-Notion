package practice.toyapi.domain.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import practice.toyapi.domain.entity.TestEntity

@Repository
interface TestRepository : JpaRepository<TestEntity, Long>