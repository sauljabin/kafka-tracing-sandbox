# Kafka OTEL Sandbox

This sandbox shows how to trace a kafka application.

> This repository is for educational purposes

## Get Started

Run Jaeger:

```shell
docker compose up -d
```

> Open [AKHQ](http://localhost:8080/) and [Jaeger](http://localhost:16686/)

Run Producer:

```shell
./gradlew producer:run
```

Run Consumer:

```shell
./gradlew consumer:run
```

## Screenshots

Search:

<p align="center">
<img alt="search" src="https://raw.githubusercontent.com/sauljabin/kafka-opentelemetry-sandbox/main/screenshots/search.png">
</p>

Tags:

<p align="center">
<img alt="spans" src="https://raw.githubusercontent.com/sauljabin/kafka-opentelemetry-sandbox/main/screenshots/spans.png">
</p>

## Development Commands

Generate Avro Schemas:

```shell
./gradlew avro:generateAvro
```

Download OpenTelemetry Agent:

```shell
wget https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar
```