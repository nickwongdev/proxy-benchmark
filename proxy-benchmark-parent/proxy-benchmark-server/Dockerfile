FROM openjdk:slim

ADD ./target/server.jar /server.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "server.jar"]