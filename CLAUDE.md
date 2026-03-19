# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build Commands

```bash
# Android
./gradlew androidApp:build

# Desktop (run)
./gradlew IntensityApp:runDesktop

# Desktop (package distributable)
./gradlew IntensityApp:createDistributable

# iOS — build from Xcode or via:
./gradlew IntensityApp:assembleIosSimulatorArm64   # simulator
./gradlew IntensityApp:assembleIosArm64            # device

# Tests
./gradlew test

# Clean
./gradlew clean
```

## Project Structure

| Module | Role |
|--------|------|
| `IntensityApp/` | Shared KMP module — all business logic and Compose UI lives here |
| `androidApp/` | Thin Android wrapper; delegates entirely to `IntensityApp` |
| `iosApp/` | Xcode project; imports the KMP framework |

All feature code goes in `IntensityApp/src/commonMain/kotlin/com/intensityrecords/app/`.

## Architecture

**Pattern**: Clean Architecture + MVVM with Compose Multiplatform.

### Layer Stack (per feature)

```
Remote Data Source (interface + Ktor impl)
  → Repository (interface + Default impl)
    → ViewModel (Koin-injected)
      → Compose Screen
```

DTOs are mapped to domain models inside repositories. Network calls are wrapped in `safeCall` returning `Result<T, DataError.Remote>`.

### Key Packages

- `core/data/` — `HttpClientFactory`, `SessionProvider` (token persistence via DataStore)
- `core/domain/` — `Result<D,E>` sealed type, `DataError` definitions
- `core/presentation/` — app theme, `DimensProvider` (compact/expanded responsive breakpoints)
- `di/` — Koin module declarations (`Modules.kt` shared, `Modules.Android.kt`, `Modules.Desktop.kt`, `Modules.iOS.kt`)
- `app/` — top-level navigation graph and routing
- Feature packages: `home/`, `live/`, `login/`, `mobility/`, `workouts/`, `steptrip/`

### Dependency Injection

Koin 4.1.1. Shared modules in `di/Modules.kt`; platform engines and DataStore paths are wired in platform-specific `Modules.*.kt` files. ViewModels are registered with `viewModelOf()`.

### Networking

Ktor 3.4.0 with platform-specific engines:
- Android / Desktop → OkHttp
- iOS → Darwin

`HttpClientFactory` adds: JSON content negotiation, `Authorization: Token {token}` header (from `SessionProvider`), logging, and a 20-second timeout.

Base API URL: `https://intensityapi.exmetrica.be/api/`

### Navigation

Type-safe Jetbrains Navigation Compose 2.9.1. Routes are sealed/data objects; the nav graph is declared in `app/`.

### Storage

DataStore Preferences for token persistence. Paths are platform-specific (wired in DI); `SessionProvider` provides the read/write interface consumed by `HttpClientFactory`.

### Video

Mux player (`com.mux.player:android:1.0.0`) on Android. Custom Maven repo required: `https://muxinc.jfrog.io/artifactory/default-maven-release-local`.

### Room Database

Room 2.8.4 with KSP. Schema output directory: `IntensityApp/schemas/`.

## Platform Targets

- **Android**: minSdk 26, compileSdk 36; `androidLibrary` plugin (not `androidApplication`)
- **iOS**: arm64 (device) + x64/arm64 simulator; static framework
- **Desktop**: JVM; main class `com.intensityrecords.app.MainKt`; packages to DMG/MSI/Deb/Exe
