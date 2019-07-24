# Estate Registry

REST API for a simple real estate registry for buildings.

## Getting Started

Project is located in github (), download it using Git or manually download zip file.

### Prerequisites

To run this application there are several things needed:

Java 11 (https://adoptopenjdk.net/?variant=openjdk11&jvmVariant=hotspot)

Maven (https://maven.apache.org/download.cgi/)


### Installing

Open command line or terminal, navigate to project folder, where project was Git-cloned (unpacked from zip file)

There should be _pom.xml_ file

Run Maven clean install command

```
mvn clean install
```

Run Spring Boot run command
```
mvn spring-boot:run
```

Open your browser and enter following address:

```
http://localhost:8080/swagger-ui.html
```

## Tests

There are three unit tests for each Controller.

Code Coverage is ~90%

## Built With

* [SpringBoot](https://spring.io/projects/spring-boot) - Framework for Spring framework
* [Maven](https://maven.apache.org/) - Dependency Management
* [Apache Derby](https://db.apache.org/derby/) - In-memory relational data base
* [Swagger UI](https://swagger.io/tools/swagger-ui/) - Visualisation of REST Api
* [Spring Data](https://spring.io/projects/spring-data) - Spring-based programming model for data access
