FROM openjdk:11-jre-slim
COPY target/morgue-0.0.1.war .
COPY agent/agent-jar-with-dependencies.jar .
CMD ["java", "-javaagent:agent-jar-with-dependencies.jar", "-jar", "morgue-0.0.1.war"]
#CMD ["java", "-jar", "morgue-0.0.1.war"]