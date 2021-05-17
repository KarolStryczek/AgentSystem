FROM jdk-11.0.10_9-alpine as builder

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

COPY pom.xml /usr/src/app
RUN mvn -T 1C dependency:go-offline

COPY src /usr/src/app/src

RUN mvn -T 1C package

FROM openjdk:11-jre-alpine
WORKDIR /usr/src/app
COPY --from=builder /usr/src/app/target/agent.jar .
EXPOSE 8080
CMD java $JAVA_OPTS -jar agent.jar