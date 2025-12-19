# Surest Member Management

Spring Boot app for managing members (JWT-secured), with PostgreSQL, Flyway migrations, caching, pagination/sorting, unit & integration tests, and JaCoCo coverage.

## Tech Stack
- Spring Boot 3.3 (Web, Data JPA, Security, Cache)
- PostgreSQL
- Flyway for DB migrations
- JWT via jjwt
- MapStruct DTO mapping
- JUnit 5, Mockito, Testcontainers
- Gradle, JaCoCo

## Prereqs
- Java 17+
- Gradle 8+
- PostgreSQL running locally with:
  - URL: jdbc:postgresql://localhost:5432/surestdb
  - Username: postgres
  - Password: Krishna@92

> You can also run integration tests entirely using Testcontainers (no local DB required).

## How to run

1. Create DB surestdb locally and ensure credentials match `src/main/resources/application.yml`.
2. Start the app:

in bash
./gradlew bootRun

Flyway will create tables and seed two users:
- balakrishna / balakrishna123 (ROLE_ADMIN)
- balu / balu123   (ROLE_USER)

3. Obtain JWT token:

```bash
curl -X POST http://localhost:8080/auth/login   -H 'Content-Type: application/json'   -d '{"username":"balakrishna","password":"balakrishna123"}'
```
Response:
```json
{"token":"<JWT>"}
```

4. Use token for API requests:

```bash
TOKEN=<JWT>
curl -H "Authorization: Bearer $TOKEN" 'http://localhost:8080/members?page=0&size=10&sort=lastName,asc'
```

### Members API
- `GET /members` (USER/ADMIN) → pagination, sorting, filtering by `firstName`, `lastName`
- `GET /members/{id}` (USER/ADMIN) → cached
- `POST /members` (ADMIN)
- `PUT /members/{id}` (ADMIN)
- `DELETE /members/{id}` (ADMIN)

## Testing & Coverage

Run all tests and generate JaCoCo report:

```bash
./gradlew clean test jacocoTestReport
```

HTML report: `build/jacocoHtml/index.html`

## Integration tests with Testcontainers
No local DB needed for tests. They start a disposable PostgreSQL automatically.

## Security Notes
- **Change** `jwt.secret` in `application.yml` for production.
- Passwords are seeded as `{noop}` and should be migrated to BCrypt for production.

## Git Workflow (suggested)
- Feature branches per API/task
- Small, descriptive commits
- PR and merge to `main` after tests/coverage pass

