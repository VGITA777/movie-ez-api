FROM openjdk:25-ea-25-slim AS build_app
WORKDIR /app
COPY . .
RUN ./gradlew build

FROM openjdk:25-ea-25-slim
WORKDIR /app
ENV TMDB_API_KEY="your_api_key_here"
ENV DB_USERNAME="root"
ENV DB_PASSWORD="root"
ENV DB_URL="jdbc:mysql://localhost:3306/movie-ez"
ENV SECURITY_HEADER_VALUE="SomeSecurityHeader"
COPY --from=build_app /app/build/libs/movie-ez-api-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]