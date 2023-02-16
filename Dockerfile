FROM adoptopenjdk/openjdk11:alpine-jre

EXPOSE 5500

COPY target/transferService-0.0.1-SNAPSHOT.jar myapp.jar

CMD ["java", "-jar", "myapp.jar"]