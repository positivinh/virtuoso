# Changelog

All notable changes to this repository are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/), and this project adheres to
[Semantic Versioning](https://semver.org/spec/v2.0.0.html).

> Since 2.0.0 this library lives in the [`virtuoso`](https://github.com/positivinh/virtuoso) repository and is released
> with it. New changes go in the root [`CHANGELOG.md`](../CHANGELOG.md); this file is kept as history, and its
> `[Unreleased]` entries ship in 2.0.0.

## [Unreleased]

### Added

- `web-mvc-starter`: RFC 7807 `ProblemDetail` error responses built by `ProblemDetailFactory` (allow-listed context
  keys, `errors` without rejected values, `WARN` for 4xx), and a fallback advice mapping any `ApplicationException` to
  `400`.
- Dummy project and reusable CI workflows.

[Unreleased]: https://github.com/positivinh/virtuoso-web-mvc/commits/main
