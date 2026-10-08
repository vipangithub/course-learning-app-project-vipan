# Course Learning App

Android assignment built with **Kotlin and Jetpack Compose**.

## 1. Architecture

The app follows a lightweight **MVVM + Repository** architecture:

```text
Compose UI
    ↓
ViewModel
    ↓
Repository
    ↓
Mock API + Room Database
```

* **UI:** Jetpack Compose screens
* **ViewModel:** Manages UI state and user actions using `StateFlow`
* **Repository:** Single source of data and handles API/local fallback
* **Mock API:** Simulates remote course data
* **Room:** Stores courses and lesson completion for offline access

This structure keeps UI, business logic, and data access separated while remaining simple enough for the assignment.

## 2. Offline Support

Courses loaded successfully from the API are stored locally in **Room**.

When the API is unavailable:

```text
API request
    ↓
Failure
    ↓
Room cache
    ↓
Previously loaded courses
```

Lesson completion is also persisted in Room, so completed lessons remain available after reopening the course or app.

## 3. Production Token Storage

Authentication tokens should **not** be stored in plain `SharedPreferences`, files, or the database.

For production, I would use **Android Keystore**, preferably through a secure encrypted storage solution, to protect sensitive credentials/tokens.

## 4. Scaling to 1M Users / Hundreds of Courses

For production scale, I would improve the solution by:

1. Use a real backend with proper authentication, authorization, and scalable APIs.
2. Add pagination and server-side filtering so clients do not download hundreds of courses at once.
3. Use a robust network/cache strategy with Room, HTTP caching, retry policies, and background synchronization.
4. Add backend caching/CDN, database indexing, load balancing, monitoring, and analytics.
5. Add automated CI/CD, crash reporting, performance monitoring, and comprehensive automated testing.

## 5. Second Platform

For iOS, I would keep the same overall architecture and API/data contracts while implementing the presentation layer using **SwiftUI** and an appropriate local persistence solution.

The key business rules—course progress, lesson completion, repository behavior, and API contracts—should remain consistent across platforms.
