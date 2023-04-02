package toyproject.jiratonotion.domain.service

import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service
import toyproject.jiratonotion.domain.entity.TestEntity
import toyproject.jiratonotion.domain.repository.TestRepository
import toyproject.jiratonotion.global.logging.log

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