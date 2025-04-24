# mallapi

Todo REST API 서버입니다.
로그인, 카카오로그인, TodoList, 상품 등록, 장바구니 담기

## 🛠 기술 스택

| 분류 | 기술 |
|------|------|
| Language | Java 17 |
| Framework | Spring Boot 3.4.2 |
| Build Tool | Gradle |
| ORM | Spring Data JPA, QueryDSL 5.0.0 |
| DB | PostgreSQL / MySQL / MongoDB |
| Infra | Docker, AWS (EC2, ECR, RDS, S3), Nginx |
| 인증 | JWT 기반 로그인 / Kakao 로그인 ( OAuth2 ) |

## 주요 기능

- 로그인 ( JWT 기반 ) / 카카오 로그인 ( OAuth2 ) 
- Todo 등록, 조회, 수정, 삭제
- 상품 등록, 조회, 수정, 삭제
- 장바구니 추가, 조회, 수정, 삭제

## 배운점
- JPA N+1 문제 해결
- MySQL, PostgreSQL, MongoDB의 선택기준
- Strategy Pattern 전략으로 의존성 역전 원칙 지키기 (DIP)
