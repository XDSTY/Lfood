FROM java:8-alpine
ADD alguserservice-1.0-SNAPSHOT.jar app.jar
EXPOSE 9094
ENTRYPOINT ["java", "-jar", "-Duser.timezone=GMT+8", "app.jar"]