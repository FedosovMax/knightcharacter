FROM openjdk:11.0.4-jre-slim

WORKDIR /usr/src/myapp

COPY target/knightexperience*.jar /knightexperience.jar

CMD ["java", "-jar", "/knightexperience.jar"]