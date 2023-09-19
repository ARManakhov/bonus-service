FROM maven:3.8.5-openjdk-17 as backend-build-stage
COPY ./ .
RUN --mount=type=cache,target=~/.m2 mvn -B clean package -Dmaven.test.skip=true

FROM openjdk:17
COPY --from=backend-build-stage target/*.jar /main.jar
ENTRYPOINT java $JAVA_OPTS -jar /main.jar
