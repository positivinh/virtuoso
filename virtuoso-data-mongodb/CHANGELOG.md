# Changelog

All notable changes to this repository are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/), and this project adheres to
[Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Changed

- **Breaking:** Spring Boot 4.1.
- **Breaking:** `AuditData` timestamps are `Instant` instead of `LocalDateTime`.
- The application's `Validator` is taken through an `ObjectProvider`, so Boot's own validator auto-configuration no
  longer backs off.
- `mongodb-starter-test` depends on `spring-boot-starter-data-mongodb-test`.
- Reusable CI workflows; versions come from the BOM.

### Fixed

- Testcontainers 2 artifact names in `mongodb-starter-test`.

### Removed

- IntelliJ IDEA configuration files.

## [1.2.0] - 2025-11-16

### Changed

- **Breaking:** groupId changed to `io.github.positivinh.virtuoso`; artifacts are published to Maven Central.

## [1.1.0] - 2025-08-19

### Added

- `mongodb-starter-test` module with the test dependencies.

### Changed

- `-autoconfigure` dependencies are optional.
- Entity validation uses `ValidatingEntityCallback` instead of the deprecated `ValidatingMongoEventListener`.

## [1.0.0] - 2025-04-15

### Added

- `mongodb-autoconfigure` and `mongodb-starter`, with a dummy project.
- Mongock data migrations.
- MongoDB auditing (`AuditData`).

[Unreleased]: https://github.com/positivinh/virtuoso-data-mongodb/compare/v1.2.0...HEAD
[1.2.0]: https://github.com/positivinh/virtuoso-data-mongodb/compare/v1.1.0...v1.2.0
[1.1.0]: https://github.com/positivinh/virtuoso-data-mongodb/compare/v1.0.0...v1.1.0
[1.0.0]: https://github.com/positivinh/virtuoso-data-mongodb/releases/tag/v1.0.0
