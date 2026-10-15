# Changelog

All notable changes to this repository are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/), and this project adheres to
[Semantic Versioning](https://semver.org/spec/v2.0.0.html).

> Since 2.0.0 this library lives in the [`virtuoso`](https://github.com/positivinh/virtuoso) repository and is released
> with it, under the same version. Its changes are still listed here; the root [`CHANGELOG.md`](../CHANGELOG.md) links to
> each release's section.

## [Unreleased]

### Added

- `web-mvc-starter`: RFC 7807 `ProblemDetail` error responses built by `ProblemDetailFactory` (allow-listed context
  keys, `errors` without rejected values, `WARN` for 4xx), and a fallback advice mapping any `ApplicationException` to
  `400`.
- Dummy project and reusable CI workflows.

### Removed

- `web-mvc-dummy-project` is no longer published: it is a sample app for the integration tests.

[Unreleased]: https://github.com/positivinh/virtuoso/commits/main/virtuoso-web-mvc
