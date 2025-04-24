# Spring Cloud gateway 학습

Spring Cloud로 개발하는 마이크로서비스 애플리케이션(MSA)

강의 학습 내용 실습 자료

https://inf.run/4n2P6

## 프로젝트별 설명

| 프로젝트                   | 설명                     | 포트    | 비고                        |
|----------------------------|--------------------------|---------|-----------------------------|
| spring-cloud-gateway       | 게이트웨이(메인)         | 8000    |                             |
| spring-cloud-eureka        | 유레카 서버              | 8761    |                             |
| spring-cloud-eureka-client | 유레카 클라이어트 예제   | 0(랜덤) |                             |
| spring-cloud-zuul-service  | netflix zuul 예제        | 8000    | deprecated로 gateway로 대체 |
| spring-cloud-zuul          | first-service            | 8081    |                             |
| spring-cloud-zuul          | second-service           | 8082    |                             |
