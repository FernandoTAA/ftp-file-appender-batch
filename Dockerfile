FROM maven AS build
COPY pom.xml /build/
WORKDIR /build
RUN mvn dependency:go-offline --batch-mode
COPY ./src /build/src/
RUN mvn clean install -DskipTests --batch-mode

FROM openjdk:17 AS release
COPY --from=build /build/target/*.jar /app.jar
ENTRYPOINT java -Dspring.profiles.active=$SPRING_PROFILE -jar /app.jar
#ENTRYPOINT tail -f /dev/null
