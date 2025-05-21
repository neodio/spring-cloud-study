FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY build/libs/config-service-1.0.jar config-service.jar
ENTRYPOINT ["java","-jar","config-service.jar"]