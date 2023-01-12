FROM openjdk:18

EXPOSE 8081

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} recipeEvaluation-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "recipeEvaluation-0.0.1-SNAPSHOT.jar"]