FROM openjdk:17-oracle
COPY target/naming.service-0.0.1-SNAPSHOT.jar naming.service-0.0.1-SNAPSHOT.jar
EXPOSE 8761
ENTRYPOINT ["java","-jar","/naming.service-0.0.1-SNAPSHOT.jar"]