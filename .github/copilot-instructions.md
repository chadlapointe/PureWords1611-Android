# Copilot Instructions for PureWords1611-Android

You are building a high-quality Android app using Jetpack Compose and modern practices. Focus on clean architecture, error handling, and performance.

## Project Context
- **App Name**: PureWords1611
- **Purpose**: Word-based content app for Google Play featuring engaging, educational word games
- **Target Platform**: Android (min SDK 24, target SDK 34)
- **Architecture**: Modern Android with Jetpack Compose, MVVM pattern

## Code Standards
- Use Kotlin for all new code
- Follow Material Design 3 guidelines
- Use Jetpack Compose for UI
- Implement MVVM architecture pattern
- Use Hilt for dependency injection
- Use Room for local data persistence
- Write clean, well-documented code with meaningful variable names

## Best Practices
- **Error Handling**: Always handle potential errors gracefully
- **Performance**: Optimize for smooth UI and efficient data operations
- **Testing**: Write unit tests for business logic and UI tests for user flows
- **Accessibility**: Ensure the app is accessible to all users
- **Security**: Never commit sensitive data or API keys to the repository

## Build & Test
- Use `./gradlew build` to build the project
- Use `./gradlew test` to run unit tests
- Use `./gradlew connectedAndroidTest` to run instrumented tests
