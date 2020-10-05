# Supersonic Session (SUSE)
## Proof of Concept

This project is an attempt to build a generic REST API with a CRUD of backend session and explores a reactive and event-driven stack. 
This is just an experiment. The first version is based on a Generic Task Demo Service.

## Tech Stack

The project uses [Quarkus](https://quarkus.io/) to build the CRUD API Rest service for (reduced and lightweight version) a backend/server session handling. The data persistance relies on DynamoDB. For reactive programming support it uses [Vertx](https://vertx.io/) and [Smallrye](https://smallrye.io/). The architecture follows the [MicroProfile](https://projects.eclipse.org/projects/technology.microprofile) specification for building microservices and deliver portable applications across multiple runtimes.
As an Event-Driven Backbone the project uses Kafka.  

![](./documentation/images/quarkus.png#250x) ![](./documentation/images/vertx.png#250x) ![](./documentation/images/dynamodb.png#250x) ![](./documentation/images/microprofile.png#250x)

### Requirements:

- Java 11
- Docker
- Gradle
- GraalVM 20

## Local Development

### How to run SUSE locally
- Spin up dependencies: `make docker.run.dependencies`: Kafka + DynamoDB
- Run SUSE `./gradlew quarkusDev`  
- The folder `postman` includes the CRUD requests to test.

The default profile is `dev`

## Build Native

- Set `quarkus.package.type:native` in [application configuration](./src/main/resources/application.yaml) 
- Build the application: `./gradlew quarkusBuild`
- Grab a coffee and wait :)
- Build the docker image: `docker build -f src/main/docker/Dockerfile.native -t suse/suse .`
- Run the docker container: `docker run -i --rm -p 8080:8080 suse/suse`

## Build (fast) JAR

- Set `quarkus.package.type:fast-jar` in [application configuration](./src/main/resources/application.yaml) 
- Build the application: `./gradlew quarkusBuild`
- Build the docker image: `docker build -f src/main/docker/Dockerfile.jvm -t suse/suse .`
- Run the docker container: `docker run -i --rm -p 8080:8080 suse/suse`

## Metrics

- [http://localhost:8080/health-ui/](http://localhost:8080/health-ui/)
- [http://localhost:8080/health/](http://localhost:8080/health/)
- [http://localhost:8080/metrics](http://localhost:8080/metrics)
- [http://localhost:8080/health/live](http://localhost:8080/health/live)
- [http://localhost:8080/health/ready](http://localhost:8080/health/ready)
- [http://localhost:8080/metrics/application](http://localhost:8080/metrics/application)


- Build the application: `./gradlew quarkusBuild`
- Build the docker image: `docker build -f src/main/docker/Dockerfile.jvm -t suse/suse .`


### What's next
- Performance Benchmark
- ETag header implementation for Optimistic Locking

### Architecture
coming soon

### How to run API tests
coming soon...
