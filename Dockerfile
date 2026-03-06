FROM gradle:jdk25-alpine AS builder
WORKDIR /app
COPY gradlew gradlew.bat settings.gradle build.gradle ./
COPY gradle gradle
RUN chmod +x gradlew
RUN ./gradlew dependencies
COPY . .
RUN ./gradlew bootJar -x test --no-daemon

FROM eclipse-temurin:25-jre-alpine
WORKDIR /app
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

COPY --from=builder /app/build/libs/*.jar app.jar
RUN chmod +x app.jar && chown appuser:appgroup app.jar
USER appuser

ENV TMDB_API_KEY="your_api_key_here"
ENV DB_USERNAME="root"
ENV DB_PASSWORD="root"
ENV DB_URL="jdbc:mysql://localhost:3306/movie-ez"
ENV SECURITY_HEADER_VALUE="SomeSecurityHeader"

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]