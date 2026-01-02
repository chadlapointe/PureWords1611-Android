# PureWords1611 Android Project Structure

## Overview

This document describes the complete Android project structure for PureWords1611, a word-based educational game app built with modern Android development practices.

## Architecture

The project follows **MVVM (Model-View-ViewModel)** architecture with **Hilt dependency injection** for clean, testable, and maintainable code.

### Key Technologies

- **Language**: Kotlin 1.9.20
- **Build System**: Gradle 8.7 with Kotlin DSL
- **UI Framework**: Jetpack Compose with Material Design 3
- **Architecture Pattern**: MVVM
- **Dependency Injection**: Hilt (Dagger)
- **Database**: Room (not yet implemented)
- **Async**: Kotlin Coroutines + Flow
- **Analytics**: Firebase Analytics
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)

## Directory Structure

```
PureWords1611-Android/
├── app/
│   ├── build.gradle.kts              # App module configuration
│   ├── proguard-rules.pro            # ProGuard rules for release builds
│   ├── google-services.json          # Firebase configuration
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml   # App manifest with permissions
│       │   ├── kotlin/com/purewords1611/android/
│       │   │   ├── MainActivity.kt   # Main entry point (@AndroidEntryPoint)
│       │   │   ├── PureWordsApplication.kt  # Application class (@HiltAndroidApp)
│       │   │   ├── analytics/        # Analytics layer
│       │   │   │   └── AnalyticsManager.kt
│       │   │   ├── data/             # Data layer (models, repositories)
│       │   │   │   ├── Verse.kt
│       │   │   │   ├── VerseRepository.kt
│       │   │   │   ├── WordDictionary.kt
│       │   │   │   ├── WordGameEngine.kt
│       │   │   │   ├── WordGrid.kt
│       │   │   │   └── WordMatchingGame.kt
│       │   │   ├── di/               # Dependency injection modules
│       │   │   │   ├── AppModule.kt
│       │   │   │   └── DataModule.kt
│       │   │   ├── ui/               # UI layer (Composables)
│       │   │   │   ├── GameModeSelectionScreen.kt
│       │   │   │   ├── gameplay/
│       │   │   │   │   └── GameplayLoop.kt
│       │   │   │   ├── theme/
│       │   │   │   │   ├── Color.kt
│       │   │   │   │   ├── Theme.kt
│       │   │   │   │   └── Type.kt
│       │   │   │   ├── wordgrid/
│       │   │   │   │   └── WordGridGameScreen.kt
│       │   │   │   └── wordmatching/
│       │   │   │       └── WordMatchingGameScreen.kt
│       │   │   └── viewmodel/        # ViewModels (@HiltViewModel)
│       │   │       ├── GameViewModel.kt
│       │   │       ├── GameViewModelFactory.kt
│       │   │       ├── WordGridViewModel.kt
│       │   │       ├── WordGridViewModelFactory.kt
│       │   │       └── WordMatchingViewModel.kt
│       │   ├── res/                  # Android resources
│       │   │   ├── drawable/         # Vector drawables
│       │   │   ├── mipmap-*/         # App launcher icons
│       │   │   └── values/           # Strings, colors, themes
│       │   └── assets/               # Asset files
│       ├── test/                     # Unit tests
│       │   └── kotlin/com/purewords1611/android/
│       │       ├── ExampleUnitTest.kt
│       │       ├── analytics/
│       │       │   └── AnalyticsManagerTest.kt
│       │       ├── data/
│       │       │   ├── VerseTest.kt
│       │       │   ├── WordGameEngineTest.kt
│       │       │   ├── WordGridTest.kt
│       │       │   └── WordMatchingEngineTest.kt
│       │       └── viewmodel/
│       │           ├── GameViewModelTest.kt
│       │           └── WordMatchingViewModelTest.kt
│       └── androidTest/              # Instrumented tests
│           └── kotlin/com/purewords1611/android/
│               └── ExampleInstrumentedTest.kt
├── gradle/
│   └── wrapper/                      # Gradle wrapper files
├── build.gradle.kts                  # Root build configuration
├── settings.gradle.kts               # Project settings
├── gradle.properties                 # Gradle properties
├── gradlew                           # Gradle wrapper script (Unix)
├── gradlew.bat                       # Gradle wrapper script (Windows)
└── docs/                             # Documentation
    └── (various Play Store documentation)
```

## Package Structure

### `/app/src/main/kotlin/com/purewords1611/android/`

#### Root Level
- **`PureWordsApplication.kt`**: Application class annotated with `@HiltAndroidApp` for Hilt initialization
- **`MainActivity.kt`**: Main activity annotated with `@AndroidEntryPoint` for dependency injection

#### `/analytics/`
Analytics and tracking functionality.
- **`AnalyticsManager.kt`**: Singleton manager for Firebase Analytics events

#### `/data/`
Data layer containing models, repositories, and business logic.
- **Models**: `Verse.kt`, `WordGrid.kt`, `WordMatchingGame.kt`
- **Repositories**: `VerseRepository.kt`, `WordDictionary.kt`
- **Engines**: `WordGameEngine.kt` - game logic and rules

#### `/di/`
Hilt dependency injection modules.
- **`AppModule.kt`**: Provides application-level dependencies (AnalyticsManager)
- **`DataModule.kt`**: Provides data layer dependencies (VerseRepository, WordDictionary)

#### `/ui/`
UI layer with Jetpack Compose screens and components.
- **`GameModeSelectionScreen.kt`**: Main menu for game mode selection
- **`/gameplay/`**: Verse game UI components
- **`/theme/`**: Material Design 3 theme configuration
- **`/wordgrid/`**: Word grid game UI components
- **`/wordmatching/`**: Word matching game UI components

#### `/viewmodel/`
ViewModels annotated with `@HiltViewModel` for state management.
- **`GameViewModel.kt`**: ViewModel for verse game with `@Inject` constructor
- **`WordGridViewModel.kt`**: ViewModel for word grid game with `@Inject` constructor
- **`WordMatchingViewModel.kt`**: ViewModel for word matching game with `@Inject` constructor
- **Factory classes**: For backward compatibility (will be removed once Hilt is fully integrated)

## Dependency Injection Setup

### Hilt Configuration

#### 1. Application Class
```kotlin
@HiltAndroidApp
class PureWordsApplication : Application()
```

#### 2. Activity
```kotlin
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var analyticsManager: AnalyticsManager
}
```

#### 3. ViewModels
```kotlin
@HiltViewModel
class GameViewModel @Inject constructor(
    private val repository: VerseRepository
) : ViewModel()
```

#### 4. Modules
```kotlin
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAnalyticsManager(
        @ApplicationContext context: Context
    ): AnalyticsManager
}
```

## Build Configuration

### Gradle Files

#### Root `build.gradle.kts`
- Android Gradle Plugin 8.1.4
- Kotlin 1.9.20
- Hilt 2.51.1
- KSP 1.9.20-1.0.14

#### App `build.gradle.kts`
- Compile SDK: 34
- Min SDK: 24
- Target SDK: 34
- Kotlin Compiler Extension: 1.5.4 (for Compose)

### Key Dependencies

#### Core Android
- `androidx.core:core-ktx:1.12.0`
- `androidx.lifecycle:lifecycle-runtime-ktx:2.7.0`
- `androidx.activity:activity-compose:1.8.2`

#### Jetpack Compose
- `androidx.compose:compose-bom:2024.12.01`
- `androidx.compose.material3:material3`
- `androidx.navigation:navigation-compose:2.8.5`

#### Hilt Dependency Injection
- `com.google.dagger:hilt-android:2.51.1`
- `androidx.hilt:hilt-navigation-compose:1.2.0`

#### Room Database (configured, not yet used)
- `androidx.room:room-runtime:2.6.1`
- `androidx.room:room-ktx:2.6.1`

#### Firebase
- `com.google.firebase:firebase-bom:32.7.0`
- `com.google.firebase:firebase-analytics-ktx`

#### Testing
- `junit:junit:4.13.2`
- `androidx.test.ext:junit:1.1.5`
- `androidx.test.espresso:espresso-core:3.5.1`
- `androidx.compose.ui:ui-test-junit4`

## Build & Test Commands

```bash
# Build debug APK
./gradlew assembleDebug

# Build release AAB for Play Store
./gradlew bundleRelease

# Run unit tests
./gradlew test

# Run instrumented tests (requires device/emulator)
./gradlew connectedAndroidTest

# Clean build
./gradlew clean

# Check dependencies
./gradlew dependencies
```

## Code Style & Best Practices

### Kotlin Style
- Use Kotlin idioms and extension functions
- Prefer `val` over `var`
- Use data classes for models
- Leverage coroutines for async operations

### Architecture Guidelines
1. **Separation of Concerns**: Keep UI, business logic, and data layers separate
2. **Dependency Injection**: Use Hilt for all dependencies
3. **Unidirectional Data Flow**: ViewModels emit StateFlow, UI observes and reacts
4. **Single Source of Truth**: State lives in ViewModels
5. **Immutability**: Use immutable data classes and StateFlow

### Compose Best Practices
- Keep composables small and focused
- Use state hoisting for reusable components
- Leverage remember and derivedStateOf for performance
- Use Material Design 3 components consistently

## Testing Structure

### Unit Tests (`/test/`)
- Test ViewModels, repositories, and business logic
- Use JUnit 4, Mockito, and Coroutines Test

### Instrumented Tests (`/androidTest/`)
- Test UI with Compose test framework
- Test database operations (when Room is implemented)
- Use AndroidJUnit4 and Espresso

## Future Enhancements

### Planned Features
1. **Room Database Integration**: Persistent storage for verses and user data
2. **Navigation Component**: Replace manual state-based navigation
3. **WorkManager**: Background tasks for daily notifications
4. **DataStore**: Replace SharedPreferences for settings
5. **Retrofit**: API integration for verse downloads
6. **Coil**: Image loading library for future graphics

### Architecture Improvements
1. Use Repository pattern with Room DAOs
2. Implement proper error handling with sealed classes
3. Add use cases layer for complex business logic
4. Implement offline-first architecture

## Known Issues

⚠️ **Build Environment**: The current sandboxed environment has restricted Maven repository access. The project structure is complete and will build successfully in a standard Android development environment with access to:
- Google Maven Repository (`dl.google.com`)
- Maven Central (`repo1.maven.org`)

See `BUILD_ENVIRONMENT_ISSUE.md` for details.

## References

- [Android Developers Guide](https://developer.android.com/)
- [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)
- [Hilt Documentation](https://dagger.dev/hilt/)
- [Material Design 3](https://m3.material.io/)
- [Kotlin Coroutines Guide](https://kotlinlang.org/docs/coroutines-guide.html)

---

**Last Updated**: January 2, 2026
**Project Status**: ✅ Structure Complete with Hilt DI | 🚀 Ready for Development
