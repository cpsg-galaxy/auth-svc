FROM openjdk:13-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} auth-svc.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/auth-svc.jar"]
