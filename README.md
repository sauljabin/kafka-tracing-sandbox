# Kafka OTEL Sandbox

This sandbox shows how to trace a kafka application.

> This repository is for educational purposes

## Get Started

Create a docker network:

```shell
docker network create kafka-opentelemetry-sandbox_network
```

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

Spans:

<p align="center">
<img alt="spans" src="https://raw.githubusercontent.com/sauljabin/kafka-opentelemetry-sandbox/main/screenshots/spans.png">
</p>

## Interesting Links

- [OpenTelemetry](https://opentelemetry.io/docs/instrumentation/java/getting-started/)
- [OpenTelemetry Configurations](https://github.com/open-telemetry/opentelemetry-java/blob/main/sdk-extensions/autoconfigure/README.md)
- [OpenTelemetry Kafka Instrumentation](https://github.com/open-telemetry/opentelemetry-java-instrumentation/tree/main/instrumentation/kafka)
- [Jaeger](https://www.jaegertracing.io/docs/1.35/getting-started/)

## Development Commands

Generate Avro Schemas:

```shell
./gradlew avro:generateAvro
```

Download OpenTelemetry Agent:

```shell
wget https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar
```