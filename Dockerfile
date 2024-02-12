#
# Build stage
#
FROM ghcr.io/graalvm/native-image-community:17 AS build
WORKDIR /build
COPY . /build
RUN ./mvnw native:compile -Pnative -DskipTests

#
# Run stage
#
FROM ubuntu:jammy
EXPOSE 8080
COPY --from=build /build/target/looks-like-tests /looks-like-tests
CMD ["/looks-like-tests"]