FROM java:8-alpine
ADD orderservice-1.0-SNAPSHOT.jar app.jar
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "-Duser.timezone=GMT+8", "app.jar"]