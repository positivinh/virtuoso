# Changelog

All notable changes to this repository are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/), and this project adheres to
[Semantic Versioning](https://semver.org/spec/v2.0.0.html).

> Since 2.0.0 this library lives in the [`virtuoso`](https://github.com/positivinh/virtuoso) repository and is released
> with it. New changes go in the root [`CHANGELOG.md`](../CHANGELOG.md); this file is kept as history, and its
> `[Unreleased]` entries ship in 2.0.0.

## [Unreleased]

### Added

- `observability-starter`: OpenTelemetry tracing and OTLP metrics, MongoDB command spans without the command text, and
  a filter that keeps health checks out of traces.
- Logs shipped over OTLP through the OpenTelemetry Logback appender.
- Latency histogram buckets for percentile dashboards.

[Unreleased]: https://github.com/positivinh/virtuoso-observability/commits/main
