# Server Info : Prototype
- Server Base With Kotlin

## Constraints
- Kotlin 사용
- JPA 기반 모듈 사용
  - MyBatis 금지
- indent 3-depth 이상 금지
    - 예를 들어, `while`문 안에 `if`문을 사용했다면 2-depth
- 단위 테스트 코드 필수
- TDD
- <오브젝트>의 내용 : 메시지를 통한 객체 간의 협력
- 모든 테스트는 Build 시 반드시 성공해야 함.
    - 테스트용 DB는 In-Memory를 사용

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