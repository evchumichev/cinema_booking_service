# CINEMA BOOKING SERVICE REST API 

### Setup
Set proper database properties in file `src/main/resources/database.properties`
### Running
```
./gradlew clean build
java -jar build/libs/cinema_booking_service-1.0-SNAPSHOT-all.jar
```
### Instructions
Given four GET and one POST requests. The response type for all is JSON.

First GET request `/api/cinema` return cinemas list.

Second GET request `/api/show` has `?cinemaID=` parameter and return shows list.

Third GET request `/api/seats` has `?showID=` parameter and return seats list with 'reserved' or 'free' status.

POST request `/api/booking` has two parameters: `?showID=` and array of seats `?seatID=` and return ticket or tickets list if booking was success.

In any other case, JSON will contain an error message.

### Testing
Before test cases, the database will automatically clean to just migrated state. 

### Build with
* [Spark framework](http://sparkjava.com) - A micro framework for creating web applications
* [Gradle](https://gradle.org) - Build-automation system
* [Junit](https://junit.org/junit5/) - Unit testing framework
* [REST-Assured](http://rest-assured.io) - Rest API testing and validating framework
* [Flyway](https://flywaydb.org) - Database migration and version control tool
* [Jackson](https://github.com/FasterXML/jackson) - Suite of data-processing tools
* [Google Guava](https://github.com/google/guava) - Set of common libraries for Java
* [Gradle Shadow](https://github.com/johnrengelman/shadow) - Gradle plugin for creating fat/uber JARs with support for package relocation
