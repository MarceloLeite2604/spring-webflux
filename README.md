# Spring WebFlux

Checking data streaming possibilities with Spring WebFlux.

## Prerequisites

The following list presents the software version which the code has been developed. Probably newer versions might work also, but there are no guarantees.

- Maven 3.6.3
- Docker 20.10.8
- Docker Compose 1.25.0
- OpenJDK 16.0.1
- Curl 7.74.0

## Execution

The following commands assumes that you are on project root directory.
 
 1. Create and start a Mongo container through `docker-compose up -d` command.
 2. Compile and build the executable jar with `mvn clean package` command.
 3. Start the program with `java -jar target/*.jar` command.
 4. Open a new terminal and send a request using curl: `curl -is "http://localhost:8080/person"`
 5. The response will be a streaming of JSON objects delimited by newline characters.