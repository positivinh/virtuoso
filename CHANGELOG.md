# Changelog

All notable changes to this repository are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/), and this project adheres to
[Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added

- `virtuoso-bom` manages the `security`, `web-mvc` and `observability` starters, the OpenTelemetry Logback appender,
  `mongodb-starter-test`, the OpenAPI generation tools and the crabshue dependencies.
- Reusable, centralized GitHub Actions workflows (`ci-stable` tag); snapshots are published to Maven Central.
- Dependabot configuration.

### Changed

- **Breaking:** Spring Boot 4.1 (`spring-boot-starter-parent` 4.1.1) and Spring Cloud 2025.1.
- Kotlin 2.4, Dokka 2.2, Testcontainers 2, Mockito 5.24, springdoc 3.1, JaCoCo 0.8.15 and other plugin and dependency
  upgrades.
- All versions are BOM properties; sibling modules use `${project.version}`.
- Development follows GitHub flow.

### Removed

- Lombok plugin and dependencies, unused repositories, IntelliJ IDEA configuration files.

## [1.2.0] - 2025-11-16

### Changed

- **Breaking:** groupId changed to `io.github.positivinh.virtuoso`; artifacts are published to Maven Central.
- Spring Boot 3.5.7.
- Virtuoso dependency versions fixed in the BOM.

## [1.1.0] - 2025-09-28

### Added

- `virtuoso-bom` manages the Virtuoso libraries, including the domain validation starter.
- Sources and javadoc jars are published.
- OCI image coordinates can be customized.

### Changed

- Kotlin compilation configured in the parent POM.
- Configuration split into files named after their concern.
- Dependency upgrades.

## [1.0.0] - 2025-04-14

### Added

- Software factory BOM and parent POMs.
- Mongock BOM for data migrations.
- AssertJ and mockito-kotlin as default test dependencies.
- SonarQube analysis and JaCoCo coverage.
- OCI image build (`docker` profile).
- Maven build pipeline and package publishing.

[Unreleased]: https://github.com/positivinh/virtuoso/compare/virtuoso-1.2.0...HEAD
[1.2.0]: https://github.com/positivinh/virtuoso/compare/virtuoso-1.1.0...virtuoso-1.2.0
[1.1.0]: https://github.com/positivinh/virtuoso/compare/virtuoso-1.0.0...virtuoso-1.1.0
[1.0.0]: https://github.com/positivinh/virtuoso/releases/tag/virtuoso-1.0.0
