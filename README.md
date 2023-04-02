# Server Info : TOY API (Jira To Notion)
- Jira와 Notion을 각 Open API를 통해 연동
  - Jira의 티켓을 Notion의 특정 페이지 추가 자동화

## Constraints
- Kotlin 사용
- JPA 기반 모듈 사용
  - MyBatis 금지
- indent 3-depth 이상 금지
    - 예를 들어, `while`문 안에 `if`문을 사용했다면 2-depth
- 테스트 코드 필수
  - ~~kotest/mockk로 만든 커버리지 50 %~~
- 모든 테스트는 Build 시 반드시 성공해야 함.
    - 테스트용 DB는 In-Memory를 사용
- SOLID
    - 그 중 `open/closed principle`에 유의할 것

## Versions
- Kotlin 1.7.22 (Java 17)
- SpringBoot 3.0.4
  - Spring Data JPA
  - Validation
  - Web 
  - WebFlux
  - Log4j2
  - Test (JUnit5)
- H2 2.1.214
- Swagger 2.0.4 (springdoc)