# virtuoso-observability

Tracing, metrics and log correlation for Virtuoso applications, on top of Spring Boot's OpenTelemetry support
(Micrometer Tracing with the OpenTelemetry bridge, OTLP exporters).

## What the starter does

`observability-starter` brings `spring-boot-starter-opentelemetry` and adds:

- **MongoDB command spans** from the driver's own Micrometer support (Boot only configures MongoDB metrics). Spans never
  carry the command text, which would include password hashes and personal data, unless
  `virtuoso.observability.mongodb.command-payload=true`.
- **Log export**: the OpenTelemetry Logback appender, attached to the root logger in code (no `logback-spring.xml`)
  when `management.opentelemetry.logging.export.otlp.endpoint` is set. Records carry the message, level, logger and
  the active trace and span ids, not the raw arguments or the MDC. The appender is alpha-only; its version in
  `virtuoso-bom` must match Spring Boot's OpenTelemetry version (ADR-0005).
- **No observations for noise**: requests (served or sent) whose path matches `virtuoso.observability.ignored-paths`,
  by default `/actuator/**` (health checks) and `/eureka/**` (registration and heartbeats).
- **Safe defaults** (`observability.properties`, lowest priority, so any application or deployment setting wins):
  - `management.tracing.sampling.probability=0.1`
  - `management.otlp.metrics.export.enabled=false`
  - no OTLP endpoint, so nothing is exported until a deployment sets one.
  - fixed latency buckets (`management.metrics.distribution.slo.*`) for HTTP server and client requests, MongoDB
    commands and gateway routes, so dashboards can compute percentiles.

## Properties

| Property                                           | Default                       | Meaning                                    |
|----------------------------------------------------|-------------------------------|--------------------------------------------|
| `virtuoso.observability.ignored-paths`             | `/actuator/**`, `/eureka/**`  | Request paths (Ant patterns) not observed  |
| `virtuoso.observability.mongodb.enabled`           | `true`                        | MongoDB command spans                      |
| `virtuoso.observability.mongodb.command-payload`   | `false`                       | Command text on MongoDB spans              |
| `virtuoso.observability.logs.enabled`              | `true`                        | Log export, when a logs endpoint is set    |

To export, a deployment sets for example:

```properties
management.opentelemetry.tracing.export.otlp.endpoint=http://lgtm:4318/v1/traces
management.otlp.metrics.export.enabled=true
management.otlp.metrics.export.url=http://lgtm:4318/v1/metrics
management.opentelemetry.logging.export.otlp.endpoint=http://lgtm:4318/v1/logs
management.tracing.sampling.probability=1.0
```

## Extension points

- Replace the MongoDB customizer with a bean named `virtuosoMongoObservabilityCustomizer`.
- Replace the path filter with your own `IgnoredPathsObservationPredicate` bean.

## Commands

### Install

Docker must be running: the dummy project's tests start MongoDB through Docker Compose.

```shell
mvn clean install
```

### Run sonarqube analysis

```shell
export SONAR_TOKEN=[...]
mvn clean verify sonar:sonar -P sonarqube
```
