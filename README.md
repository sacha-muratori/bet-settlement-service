# Bet Settlement Service
A backend service simulating a sports event outcome handling and bet settlement via Kafka and RocketMQ

## Installation
### Pre-requisite
Please install docker compose within your Docker CLI/Engine.
https://docs.docker.com/compose/install/linux/

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

### Notes
Dockerfile and docker-compose.yml file can be adjusted to align with any CI/CD pipeline.
