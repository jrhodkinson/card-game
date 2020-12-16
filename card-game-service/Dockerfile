FROM openjdk:15
ENTRYPOINT ["/usr/bin/java", "-Xms256m", "-Xmx512m", "-jar", "/usr/share/service/service.jar"]
ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/service/service.jar
EXPOSE 7000
