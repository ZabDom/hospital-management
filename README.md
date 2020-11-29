**What's the goal?**

Three modules project to manage patients of hospital:

Legacy - supports MYSQL database by connecting via JDBC. When it starts
it has empty database which we can populate by SOAP requests.

Fancy - supports Memcached to use cache with database queries. Does the same
thing as Legacy but using REST to expose data. Using JMS to send patients
with specified status to morgue. Accessable by JSP pages.

Morgue - using JMS to retreive data sent from Fancy module. Accessable by
JSP pages.

**How to run the app?**

Use Java 11

1. Clone the repository.
2. Execute command `make build` to build project.
3. Run Docker and execute command `make run` to run project and all 
required docker images.
4. To terminate and remove the docker containers execute `make stop`.

**How to access Fancy and Morgue?**

Simply put `http://localhost:8082/fancy` to access Fancy JSP page and 
`http://localhost:8083` to access Morgue JSP page. To receive upcoming 
JMS message with patient use `Update` link.

**On which ports are the containers running?**

Legacy - port: 8081

Fancy - port: 8082

Morgue - port: 8083

**How to populate database using SOAP?**

Simply put, go to `/legacy/src/main/resources/soap` where are prepared
templates to add, delete and get patient.

To use above SOAP requests, use `POST` method with given 
url: `http://localhost:8081/soap`.

**How to get and update patient using REST?**

Simply put, `http://localhost:8082/patients/{id}` to get patient with given
Id and `http://localhost:8082/patient/{id}/{dateOfLeave}/{currentStatus}` to
update patient with given values. Remember to use proper date pattern which is:
`yyyy-mm-dd`.

**Technologies used in this project:**

1. `Docker` to run required images and application in containers.
2. `Spring Boot`.
3. `MYSQL` database.
4. `JDBC` connection with database.
5. `Flyway` to create database schema.
6. `Memcached` for caching the database queries.
7. `ActiveMQ` message broker.
8. `JMS` to send patients to morgue.
9. `JSP` to display pages.
10. `JSTL`