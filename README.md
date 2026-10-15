# virtuoso

Software factory components: the build platform (`virtuoso-bom`, `virtuoso-parent`, `virtuoso-spring-boot-parent`)
and the Spring Boot starter libraries, built and released together.

| Directory                     | Starter                                                       |
|-------------------------------|---------------------------------------------------------------|
| `virtuoso-domain-validation/` | `domain-validation-starter`: entity validation                |
| `virtuoso-data-mongodb/`      | `mongodb-starter`: repositories, auditing, Mongock            |
| `virtuoso-jwt/`               | `jwt-starter`: JWT creation and verification                  |
| `virtuoso-authorization/`     | `authorization-starter`: method security, permission policies |
| `virtuoso-web-security/`      | `web-security-starter`: security filter chain, CORS           |
| `virtuoso-web-mvc/`           | `web-mvc-starter`: `ProblemDetail` error responses            |
| `virtuoso-observability/`     | `observability-starter`: OpenTelemetry tracing, metrics, logs |

## Commands

### Install

Builds and installs the parents and every library, in dependency order (Docker must be running for the MongoDB
tests):

```shell
mvn clean install
```

### Run sonarqube analysis

Set environment variable `SONAR_TOKEN`

```shell
export SONAR_TOKEN=[...]
```

Run sonarqube analysis

```shell
mvn clean verify sonar:sonar -P sonarqube
```