#
# Build stage
#
FROM eclipse-temurin:21-jdk AS build
WORKDIR /build
COPY . /build
RUN ./mvnw package -DskipTests

#
# Run stage
#
FROM eclipse-temurin:21-jre
EXPOSE 8080
COPY --from=build /build/target/looks-like-tests-backend-*.jar /app.jar
CMD ["java", "-jar", "/app.jar"]
