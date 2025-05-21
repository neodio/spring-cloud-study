FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY /build/libs/spring-cloud-eureka-1.0.jar spring-cloud-eureka.jar
ENTRYPOINT ["java", "-jar", "spring-cloud-eureka.jar"]