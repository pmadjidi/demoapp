FROM java:8-jdk-alpine
COPY ./target/demoApp-0.0.1-SNAPSHOT.jar  /usr/app/
WORKDIR /usr/app
RUN sh -c 'touch demoApp-0.0.1-SNAPSHOT.jar'
EXPOSE 8080
ENTRYPOINT ["java","-jar","demoApp-0.0.1-SNAPSHOT.jar"]  

