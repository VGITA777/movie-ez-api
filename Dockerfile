FROM gradle:jdk25-alpine AS builder
WORKDIR /app
COPY gradlew gradlew.bat settings.gradle build.gradle ./
COPY gradle gradle
RUN ./gradlew dependencies
COPY . .
RUN chmod +x gradlew
RUN ./gradlew bootJar -x test --no-daemon

FROM eclipse-temurin:25-jre-alpine
WORKDIR /app
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

COPY --from=builder /app/build/libs/*.jar app.jar
RUN chmod +x app.jar && chown appuser:appgroup app.jar
USER appuser

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]