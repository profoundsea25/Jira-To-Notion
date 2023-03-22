# Server Info : TOY API
- 결제 모듈 API를 로컬로 구현

## Requirement
- ~~Oauth 2.0를 활용한 인증~~
- API가 제공하는 기능 수행
- 다른 도메인으로부터 호출하여 사용할 수 있음
- Spring Cloud(Eureka, Config 등)을 도입할 수도, 그렇지 않을 수도 있음.
- 인증/인가 기능을 분리할 수도, 그렇지 않을 수도 있음.
- Rate Limit = 100/s

## Constraints
- MyBatis 금지
  - 반드시 JPA 기반 모듈 사용
- Kotlin 사용
- indent 3-depth 이상 금지
    - 예를 들어, `while`문 안에 `if`문을 사용했다면 2-depth
- 테스트 코드 필수 
  - kotest/mockk로 만든 커버리지 50 %
- 모든 테스트는 Build 시 반드시 성공해야 함.
    - 테스트용 DB는 In-Memory를 사용
- SOLID
    - 그 중 `open/closed principle`에 유의할 것

## Versions
- Kotlin 1.7.22 (Java 17)
- SpringBoot 3.0.4
    - Spring Data JPA
    - ~~Spring Security~~
    - Spring Actuator
    - Validation
- H2 2.1.214
    - (MySQL)