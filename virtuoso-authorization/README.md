# virtuoso-authorization

Software factory components for method security and permission evaluation, independent of the web stack.

Any Spring Boot application (web, batch, message consumer) that depends on `authorization-starter` gets:

- method security (`@PreAuthorize`, `@Secured`, JSR-250) enabled;
- a `PermissionEvaluator` bean named `appCustomPermissionEvaluator` that resolves `hasPermission(id, type, operation)`
  by delegating to the application's `PermissionPolicy` beans (fail closed: no policy for a target type means denied);
- a `RequesterProvider` bean exposing the current caller as a plain `Requester`.

Domain modules depend only on `authorization-core` (`Requester`, `PermissionPolicy`, `RequesterProvider`), which has no
Spring code. Most policies follow one rule, "administrators may do anything, the owner may do X": extend
`OwnerOrAdminPermissionPolicy` for it, with administrators defaulting to `VirtuosoAuthorities.ADMINISTRATORS`
(`ROLE_ADMIN`, `ROLE_SYSTEM`):

```kotlin
@Component
class UserProfilePermissionPolicy : OwnerOrAdminPermissionPolicy(USER_PROFILE, setOf(READ, UPDATE))
```
See [ADR-0002](https://github.com/positivinh/virtuoso-agentic/blob/main/docs/adr/0002-virtuoso-authorization-starter.md).

## Modules

| Module                        | Content                                                                                                     |
|-------------------------------|-------------------------------------------------------------------------------------------------------------|
| `authorization-core`          | `Requester`, `PermissionPolicy`, `RequesterProvider`, `OwnerOrAdminPermissionPolicy`, `VirtuosoAuthorities` |
| `authorization-autoconfigure` | `VirtuosoAuthorizationAutoConfiguration`, `PolicyPermissionEvaluator`, `SecurityContextRequesterProvider`   |
| `authorization-starter`       | `authorization-core` + `authorization-autoconfigure` + Spring Security core/config (no web)                 |
| `authorization-dummy-project` | Non-web sample application and integration tests                                                            |

## Extension points

| Bean                              | Default                                                           | Override                           |
|-----------------------------------|-------------------------------------------------------------------|------------------------------------|
| `appCustomPermissionEvaluator`    | `PolicyPermissionEvaluator` over all `PermissionPolicy` beans     | declare a bean with that name      |
| `RequesterProvider`               | `SecurityContextRequesterProvider`                                | declare a `RequesterProvider` bean |
| `MethodSecurityExpressionHandler` | `DefaultMethodSecurityExpressionHandler` with the evaluator above | declare one                        |

## Commands

### Install

```shell
mvn clean install
```

### Run sonarqube analysis

Set environment variable `SONAR_TOKEN`

```shell
export SONAR_TOKEN=[...]
```

Run sonarqube analysis

```shell
mvn clean verify sonar:sonar -P sonarqube
```
