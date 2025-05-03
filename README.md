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
http://localhost:8080/api/events/publish"
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
As part of this MVP, I’ve implemented a monolithic Spring Boot application for simplicity. In production, I would separate it into at least two services: one for the API/Kafka producer and another for the Kafka consumer/RocketMQ producer,
enabling independent deployment, scalability, and failure isolation.  
While this API uses Spring MVC, a reactive version with WebFlux could better support high-throughput scenarios.   
Production setups would also benefit from resilience features like exponential backoff (e.g., Resilience4j) and rate limiting (e.g., Bucket4j).  
Kafka and RocketMQ are used with default settings; tuning (e.g., partitions, compression, batching) would be essential for real-world loads.  
Health checks are available via Spring Boot Actuator (/actuator/health). For observability, Prometheus integration is a logical next step.   
Although end-to-end testing is out of scope here, tools like TestContainers and WireMock would be used in a full test strategy.   
Configs and SQL files should ideally reside under a /release folder for clarity.  
