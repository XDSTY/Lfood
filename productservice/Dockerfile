FROM java:8-alpine
ADD productservice-1.0-SNAPSHOT.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "-Duser.timezone=GMT+8", "app.jar"]