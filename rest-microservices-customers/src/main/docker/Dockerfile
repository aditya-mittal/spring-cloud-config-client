FROM java:7
VOLUME /tmp
ADD rest-microservices-customers-1.0.1.BUILD-SNAPSHOT.jar /app.jar
RUN bash -c 'touch /app.jar'
EXPOSE 9000
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]