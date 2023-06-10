package prototype.domain.service

import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service
import prototype.domain.entity.TestEntity
import prototype.domain.repository.TestRepository
import prototype.global.logging.log

@Service
class TestService(
    private val testRepository: TestRepository
) {
    private val log = this.log()

    @PostConstruct
    fun test() {
        val testEntity = TestEntity()
        testEntity.message = "TEST"
        testRepository.save(testEntity)
        log.info("testEntity = $testEntity")
    }
}