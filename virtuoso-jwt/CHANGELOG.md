# Changelog

All notable changes to this repository are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/), and this project adheres to
[Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Changed

- **Breaking:** Spring Boot 4.1.
- JWT claim handling and configuration are more extensible.
- Reusable CI workflows; versions come from the BOM.

### Fixed

- **Breaking:** `extracAuthenticationFromToken` renamed to `extractAuthenticationFromToken`; the old name is removed.

### Removed

- IntelliJ IDEA configuration files.

## [1.2.0] - 2025-11-17

### Changed

- **Breaking:** groupId changed to `io.github.positivinh.virtuoso`; artifacts are published to Maven Central.

## [1.1.0] - 2025-08-19

### Changed

- Parent upgraded to 1.1.0.

## [1.0.0] - 2025-04-15

### Added

- JWT creation and verification (`JwtTokenCreator`, `JwtTokenDecoder`), with an auto-configuration and a dummy
  project.
- Authentication extraction from a token.
- Token expiration.

[Unreleased]: https://github.com/positivinh/virtuoso-jwt/compare/v1.2.0...HEAD
[1.2.0]: https://github.com/positivinh/virtuoso-jwt/compare/v1.1.0...v1.2.0
[1.1.0]: https://github.com/positivinh/virtuoso-jwt/compare/v1.0.0...v1.1.0
[1.0.0]: https://github.com/positivinh/virtuoso-jwt/releases/tag/v1.0.0
