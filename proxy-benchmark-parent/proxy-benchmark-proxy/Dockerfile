FROM openjdk:slim

ADD ./target/proxy.jar /proxy.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "server.jar"]