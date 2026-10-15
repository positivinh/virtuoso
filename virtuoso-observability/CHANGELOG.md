# Changelog

All notable changes to this repository are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/), and this project adheres to
[Semantic Versioning](https://semver.org/spec/v2.0.0.html).

> Since 2.0.0 this library lives in the [`virtuoso`](https://github.com/positivinh/virtuoso) repository and is released
> with it, under the same version. Its changes are still listed here; the root [`CHANGELOG.md`](../CHANGELOG.md) links to
> each release's section.

## [Unreleased]

### Added

- `observability-starter`: OpenTelemetry tracing and OTLP metrics, MongoDB command spans without the command text, and
  a filter that keeps health checks out of traces.
- Logs shipped over OTLP through the OpenTelemetry Logback appender.
- Latency histogram buckets for percentile dashboards.

### Removed

- `observability-dummy-project` is no longer published: it is a sample app for the integration tests.

[Unreleased]: https://github.com/positivinh/virtuoso/commits/main/virtuoso-observability
