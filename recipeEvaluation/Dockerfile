FROM openjdk

EXPOSE 8081

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} Tasty-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "Tasty-0.0.1-SNAPSHOT.jar"]