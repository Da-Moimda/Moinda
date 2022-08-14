FROM openjdk:11-jdk
ARG JAR_FILE=moinda-api/build/libs/moinda-api-1.0-SNAPSHOT.jar
ADD ${JAR_FILE} moinda.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=core", "/moinda.jar"]