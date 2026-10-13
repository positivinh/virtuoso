# Changelog

All notable changes to this repository are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/), and this project adheres to
[Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added

- `authorization-core`: `Requester`, `RequesterProvider` and `PermissionPolicy`.
- `authorization-autoconfigure` / `authorization-starter`: method security and the `appCustomPermissionEvaluator`, which
  resolves `hasPermission(...)` against every `PermissionPolicy` bean, without a web dependency.
- Dummy project and reusable CI workflows.

[Unreleased]: https://github.com/positivinh/virtuoso-authorization/commits/main
