FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY build/libs/backend-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9001
ENTRYPOINT ["java", "-jar", "/app.jar"]