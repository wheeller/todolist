FROM alpine:3.16
LABEL maintainer="AndreyS"
RUN apk add openjdk11-jre-headless
COPY target/*.jar /app.jar
#EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
