# Repository Guidelines

## Project Structure & Module Organization
`QQE2EE` is a single-module Android app. Main code lives in `app/src/main/java/me/fuckqq/e2ee`, split by responsibility: `ui/` for Compose screens and dialogs, `service/handler/` for chat-app integration logic, `util/` for crypto, permissions, and Android helpers, and `data/` for persistence. Resources are under `app/src/main/res`. Local JVM tests live in `app/src/test`, and device/emulator tests live in `app/src/androidTest`. Project-level Gradle files are `build.gradle.kts`, `settings.gradle.kts`, and `gradle/libs.versions.toml`.

## Build, Test, and Development Commands
Use the Gradle wrapper from the repository root.

- `./gradlew assembleDebug`: build a debug APK.
- `./gradlew installDebug`: install the debug build on a connected device or emulator.
- `./gradlew testDebugUnitTest`: run local JUnit tests in `app/src/test`.
- `./gradlew connectedDebugAndroidTest`: run instrumented and Compose UI tests on a device.
- `start-test-env.bat`: helper script for bringing up the local Android test environment described in `TESTING_GUIDE.md`.

Prefer Android Studio for emulator setup, Layout Inspector, and Logcat when debugging accessibility-node issues.

## Coding Style & Naming Conventions
This project uses Kotlin official style (`kotlin.code.style=official`), 4-space indentation, and Kotlin DSL for Gradle. Keep packages lowercase, classes and composables in `UpperCamelCase`, functions and properties in `lowerCamelCase`, and constants in `UPPER_SNAKE_CASE` when truly global. Match existing naming such as `QQHandler`, `CryptoManager`, and `SettingsScreen`. Keep Compose UI state near the screen or dialog that owns it, and keep Android-specific service logic out of UI packages.

## Testing Guidelines
Write JVM tests for pure logic such as crypto and parsers; use instrumented tests for accessibility flows, Compose interactions, and Android framework behavior. Name test files after the target class, for example `CryptoManagerTest.kt`. Before opening a PR, run `./gradlew testDebugUnitTest`; run `connectedDebugAndroidTest` when touching UI, permissions, accessibility, or file handling.

## Commit & Pull Request Guidelines
Recent history favors short, focused commit subjects, often brief Chinese summaries of the change. Keep commits scoped to one change and use descriptive subjects; avoid placeholder messages like `.`. PRs should summarize user-visible impact, note risky areas like accessibility hooks or encryption changes, link related issues, and include screenshots or short recordings for UI changes.

## Security & Configuration Tips
Do not commit private keys, account data, or signed release artifacts. Test with emulator or secondary chat accounts only. If you change app IDs, permissions, or accessibility behavior, update both `AndroidManifest.xml` and the related test notes in `TESTING_GUIDE.md`.
