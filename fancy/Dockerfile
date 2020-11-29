FROM openjdk:11-jre-slim
COPY target/fancy-0.0.1-war-with-dependencies.war .
COPY agent/agent-jar-with-dependencies.jar .
CMD ["java", "-javaagent:agent-jar-with-dependencies.jar", "-jar","fancy-0.0.1-war-with-dependencies.war"]
#CMD ["java", "-jar", "fancy-0.0.1-war-with-dependencies.war"]