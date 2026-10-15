# Changelog

All notable changes to this repository are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/), and this project adheres to
[Semantic Versioning](https://semver.org/spec/v2.0.0.html).

> Since 2.0.0 this library lives in the [`virtuoso`](https://github.com/positivinh/virtuoso) repository and is released
> with it, under the same version. Its changes are still listed here; the root [`CHANGELOG.md`](../CHANGELOG.md) links to
> each release's section. Links to 1.x point to the archived repository.

## [Unreleased]

### Changed

- **Breaking:** Spring Boot 4.1 and Spring Security 7.
- **Breaking:** permission evaluation is delegated to `virtuoso-authorization`, which `web-security-starter` now
  includes.
- Authorization filter precedence and registration: it runs only inside the Spring Security chain.
- Actuator endpoint exposure restricted.
- Reusable CI workflows; versions come from the BOM.

### Removed

- `web-security-dummy-project` is no longer published: it is a sample app for the integration tests.
- IntelliJ IDEA configuration files.

## [1.2.0] - 2025-11-17

### Changed

- **Breaking:** groupId changed to `io.github.positivinh.virtuoso`; artifacts are published to Maven Central.

## [1.1.0] - 2025-08-19

### Changed

- Configuration properties enabled explicitly.
- Plugin configuration delegated to the parent.

## [1.0.0] - 2025-04-15

### Added

- Endpoint authorization configuration, including permit-all paths.
- CORS configuration.
- Authentication through `X-Virtuoso-*` headers (`VirtuosoHeaderAuthorizationFilter`), replaceable by an
  `appAuthorizationFilter` bean; header names are configurable.
- Custom permission evaluator bean `appCustomPermissionEvaluator`.

[Unreleased]: https://github.com/positivinh/virtuoso/commits/main/virtuoso-web-security
[1.2.0]: https://github.com/positivinh/virtuoso-web-security/compare/v1.1.0...v1.2.0
[1.1.0]: https://github.com/positivinh/virtuoso-web-security/compare/v1.0.0...v1.1.0
[1.0.0]: https://github.com/positivinh/virtuoso-web-security/releases/tag/v1.0.0
