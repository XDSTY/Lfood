FROM java:8-alpine
ADD algapi-1.0-SNAPSHOT.jar app.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "-Duser.timezone=GMT+8", "app.jar"]