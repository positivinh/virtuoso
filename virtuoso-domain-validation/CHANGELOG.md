# Changelog

All notable changes to this repository are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/), and this project adheres to
[Semantic Versioning](https://semver.org/spec/v2.0.0.html).

> Since 2.0.0 this library lives in the [`virtuoso`](https://github.com/positivinh/virtuoso) repository and is released
> with it, under the same version. Its changes are still listed here; the root [`CHANGELOG.md`](../CHANGELOG.md) links to
> each release's section. Links to 1.x point to the archived repository.

## [Unreleased]

### Changed

- **Breaking:** Spring Boot 4.1.
- Validation failures are logged with their violations.
- Reusable CI workflows; versions come from the BOM.

### Removed

- IntelliJ IDEA configuration files.

## [1.2.0] - 2025-11-17

### Changed

- **Breaking:** groupId changed to `io.github.positivinh.virtuoso`; artifacts are published to Maven Central.

## [1.1.0] - 2025-08-19

### Added

- `domain-validation-starter`.
- `EntityErrorsProvider`.
- Sources and javadoc jars are published.

### Changed

- Replaced a deprecated `StringUtils` method.

## [1.0.0] - 2025-04-15

### Added

- `AbstractEntityValidator` / `SimpleValidator` domain validation framework, with a dummy project.
- `ValidationException` is an `ApplicationException`.
- `domain-validation-test` helpers to test domain object constraint violations.

[Unreleased]: https://github.com/positivinh/virtuoso/commits/main/virtuoso-domain-validation
[1.2.0]: https://github.com/positivinh/virtuoso-domain-validation/compare/v1.1.0...v1.2.0
[1.1.0]: https://github.com/positivinh/virtuoso-domain-validation/compare/v1.0.0...v1.1.0
[1.0.0]: https://github.com/positivinh/virtuoso-domain-validation/releases/tag/v1.0.0
