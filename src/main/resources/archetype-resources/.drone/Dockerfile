ARG BASE_IMAGE

FROM $BASE_IMAGE

LABEL maintainer="dev@ujar.org"
ARG BUILD_VERSION
ENV APPLICATION_BUILD_VERSION ${BUILD_VERSION}

WORKDIR /opt/app

COPY ./target/*.jar /opt/app/app.jar

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -Duser.timezone=UTC -jar /opt/app/app.jar"]
EXPOSE 8080
