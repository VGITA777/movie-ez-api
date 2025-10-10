FROM openjdk:21-slim AS build_app
WORKDIR /app
COPY . .
RUN ./gradlew build

FROM openjdk:21-slim
WORKDIR /app
ENV TMDB_API_KEY="your_api_key_here"
ENV DB_USERNAME="your_db_username_here"
ENV DB_PASSWORD="your_db_password_here"
ENV DB_URL="your_db_url_here"
COPY --from=build_app /app/build/libs/movie-ez-api-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]