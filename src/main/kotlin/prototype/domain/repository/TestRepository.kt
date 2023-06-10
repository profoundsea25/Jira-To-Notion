package prototype.domain.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import prototype.domain.entity.TestEntity

@Repository
interface TestRepository : JpaRepository<TestEntity, Long>