Simple Java web application with a REST API that allows you to create, read, update and delete movies. The application uses the following technologies:
- Spring Boot to set up the application
- Spring WEB to create a REST API
- Spring JDBC to connect to a database
- Spring Actuator to monitor the application
- PostgreSQL as a database
- Docker to containerize the application
- Docker Compose to run the application and the database together
- Maven to build the application
- JUnit to test the application
- Mockito to mock dependencies
- Testcontainers to test the database
- Prometheus to collect metrics
- Grafana to visualize metrics

To build application just run the following command in the root directory of the project:
```
mvn clean package
```
Then run the following command in the root directory of the project:
```
docker-compose up
```

Make sure you have Java 17, Maven, Docker and Docker Compose installed on your machine.


Movies application is available on the following URL: http://localhost:8080/
Grafana is available on the following URL: http://localhost:3000/.  Username: admin, password: grafana (as per configuration in docker-compose.yml file)