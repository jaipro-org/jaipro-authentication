FROM openjdk:11-jre-slim-buster
EXPOSE 8080
COPY eureka-authentication-0.0.1-SNAPSHOT.jar app.jar
#RUN apk add --no-cache tzdata
RUN cp /usr/share/zoneinfo/America/Bogota /etc/localtime
RUN echo "America/Bogota" > /etc/timezone
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]