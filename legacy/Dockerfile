FROM openjdk:11-jre-slim
COPY target/legacy-0.0.1-jar-with-dependencies.jar .
COPY agent/agent-jar-with-dependencies.jar .
CMD ["java", "-javaagent:agent-jar-with-dependencies.jar", "-jar", "legacy-0.0.1-jar-with-dependencies.jar"]
#CMD ["java", "-jar", "legacy-0.0.1-jar-with-dependencies.jar"]