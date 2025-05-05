# Bet Settlement Service
A backend service simulating a sports event outcome handling and bet settlement via Kafka and RocketMQ.

## Design and Implementation
The application is implemented using Spring Boot in a monolithic structure, exposing a REST API that accepts sports event outcomes 
and publishes them to a Kafka topic (event-outcomes).  
A Kafka consumer within the same application listens to this topic and processes any bets from an in-memory H2 database that match the event.   
Once matching bets are found, the application sends settlement messages to RocketMQ, which is fully configured and running as part of the docker-compose.yml environment.  
The architecture cleanly separates concerns into components such as controllers, services, consumers, and producers, 
allowing for easy future refactoring into separate services.   
The use of an in-memory H2 database simplifies persistence during development while still representing real-world storage interaction.   
All infrastructure dependencies, including Kafka, RocketMQ, and the application itself, are managed within Docker Compose, 
ensuring reproducibility and ease of setup.  
Due to compatibility issues with the rocketmq-spring-boot-starter dependency, Spring was downgraded to 2.7.15 and Java to 17.

## Installation
### Pre-requisite
Please install Docker and Docker compose within your Docker CLI/Engine.

### Running the Bet Settlement Service
The installation is quite easy.

You can move into the Dockerfile path within the application repository and build the Bet Settlement Service image:
```bash
docker build -t bookmaker/bet-settlement-service .
```

Lastly, just run the docker compose command (provided you are in the same folder path as above):
```bash
docker compose up
```

And that's it!
Now you have the Bet Settlement Service available on localhost on port 8080.

## Execution 

### **Event Outcomes Rest API**
In order to successfully test and execute the correct flow of the application, you can use Postman (or a curl) towards the endpoint:

**Request Format:**
```
http://localhost:8080/api/events/publish
```
with a raw json body as (for example):
```json
{
  "eventId": 1001,
  "eventName": "Soccer Match Final",
  "eventWinnerId": 501
}
```

**Response Format:**
```raw
(Status 200) Event processed successfully
```

### Notes
As part of this MVP, I implemented a monolithic Spring Boot application for simplicity.  
In a production setup, I would split it into at least two services: one handling the API and Kafka producer, the other managing the Kafka consumer and RocketMQ producer. This separation enables independent development, deployment, scalability, and better failure isolation.  
  
Although this version uses Spring MVC, a reactive alternative with WebFlux could better support high-throughput use cases.  
Production environments would also benefit from resilience mechanisms such as exponential backoff using Resilience4j, and rate limiting via tools like Bucket4j.  
  
Kafka and RocketMQ are currently used with default configurations. Tuning parameters, such as partitions, compression, and batching, would be critical for handling real-world traffic efficiently.  
  
Health checks are exposed via Spring Boot Actuator (/actuator/health).    
For observability, Prometheus integration would be a natural next step.  
  
End-to-end testing was out of scope for this MVP, but in a full testing strategy, tools like TestContainers and WireMock would be incorporated.  
  
Configuration files and SQL scripts should ideally be organized under a /release directory for clarity and maintainability.  