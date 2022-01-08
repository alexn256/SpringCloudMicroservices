FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY test /home/app/test
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml -DskipTests=true package

FROM bellsoft/liberica-openjdk-alpine:11
COPY --from=build /home/app/target/service-config-0.0.1-SNAPSHOT.jar /usr/local/lib/service-config.jar
EXPOSE 8010
ENTRYPOINT ["java","-jar","/usr/local/lib/service-config.jar"]