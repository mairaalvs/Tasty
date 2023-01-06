FROM openjdk

EXPOSE 8081

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} recipe-evaluation-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "recipe-evaluation-0.0.1-SNAPSHOT.jar"]