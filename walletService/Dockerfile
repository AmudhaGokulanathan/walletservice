FROM openjdk:17-slim as build
copy target/walletService-0.0.1-SNAPSHOT.jar walletService-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/walletService-0.0.1-SNAPSHOT.jar"]