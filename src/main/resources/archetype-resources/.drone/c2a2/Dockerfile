FROM docker-registry.c2a2.com/jdk:openjdk-19-slim
ARG BUILD_VERSION
ENV APPLICATION_BUILD_VERSION=$BUILD_VERSION
WORKDIR /opt/app

COPY ./target/*.jar /opt/opt/app/app.jar
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -Duser.timezone=UTC -jar /opt/app/app.jar"]
EXPOSE 8080
