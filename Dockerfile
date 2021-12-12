FROM adoptopenjdk:15-jre-hotspot

RUN mkdir  /app

WORKDIR /app

ADD ./api/target/api-1.0.0-SNAPSHOT.jar /app

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "api-1.0.0-SNAPSHOT.jar"]
#CMD ["java", "-jar", "api-1.0.0-SNAPSHOT.jar"]