package practice.toyapi.domain.service

import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service
import practice.toyapi.domain.entity.TestEntity
import practice.toyapi.domain.repository.TestRepository
import practice.toyapi.global.logging.log

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