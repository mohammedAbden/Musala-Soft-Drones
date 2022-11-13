# Musala-Soft-Drones

### Introduction

Technical task  of Musala Soft.

### Entities
- Drone is the device which carrying items to transport them
- Medications are the items which the Drone will carry and move them
- Trip is an entity to represent a trip for a drone to move some medications

### Technologies
- H2 InMemory database
- Spring Boot framework
- Maven building tool
- Swagger OpenAPI 


### Requirements
- java 11
- set `JAVA_HOME` env to java installation directory
- set `Maven_Home` env to maven installation directory


### Build/Run instruction
- open terminal in project root and run `./mvnw spring-boot:run`
- Database is automatically run with 10 drones on application start up


### Notes
- Project provided with BDD Test and cover most of the scenarios
- After running the application we can open swagger Link `http://localhost:9090/swagger-ui/index.html`

[//]: # (### Run Test)
