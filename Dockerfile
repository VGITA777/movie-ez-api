FROM openjdk:21-slim AS build_app
WORKDIR /app
COPY . .
RUN ./gradlew build

FROM openjdk:21-slim
WORKDIR /app
ENV TMDB_API_KEY="your_api_key_here"
COPY --from=build_app /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]