
FROM openjdk:17

WORKDIR /app

COPY /target/eureka-discovery-server.jar /app/eureka-discovery-server.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "eureka-discovery-server.jar"]

