FROM openjdk:21-jdk 

WORKDIR /src

COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn
COPY src src
COPY pom.xml .

RUN mvn package -Dmaven.test.skip=true

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=builder /src/target/miniproject-0.0.1-SNAPSHOT.jar app.jar

ENV SPRING_REDIS_HOST=localhost SPRING_REDIS_PORT=6379
ENV SPRING_REDIS_USERNAME=default SPRING_REDIS_PASSWORD=password
ENV PORT=8080 

EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar app.jar
