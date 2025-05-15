# Spring Cloud gateway 학습

Spring Cloud로 개발하는 마이크로서비스 애플리케이션(MSA)

강의 학습 내용 실습 자료

https://inf.run/4n2P6

## 프로젝트별 설명

| 프로젝트                     | 설명                      | 포트     | 비고                        |
|----------------------------|--------------------------|---------|-----------------------------|
| config-service             | 설정(구성) 서버             | 8000    |                             |
| gateway-first-service      | first-service 예제        | 8081    | netflix zuul 사용 예제1       |
| gateway-second-service     | second-service 예제       | 8082    | netflix zuul 사용 예제2       |
| spring-cloud-gateway       | 게이트웨이(메인)             | 8000    |                             |
| spring-cloud-eureka        | 유레카 서버                 | 8761    |                             |
| spring-cloud-eureka-client | 유레카 클라이어트 예제         | 0(랜덤)  |                             |
| spring-cloud-zuul-service  | netflix zuul 예제         | 8000    | deprecated로 gateway로 대체   |
| user-service               | 사용자 서비스               | 8001    |                             |
| order-service              | 주문 서비스                 | 8002    |                             |
| catalog-service            | 카탈로그 서비스              | 8003    |                             |

## h2 console을 이용한 db접속
### h2
<img src="image/1.png"  width="600"/>

### mariaDb
<img src="image/2.png"  width="600"/>
