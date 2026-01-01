# Google Analytics Setup Guide

## Overview

This app integrates Firebase Analytics (Google's recommended analytics solution for Android apps) to track user engagement and app usage patterns.

## Setup Instructions

### 1. Create Firebase Project

1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Click "Add project" or select an existing project
3. Follow the setup wizard to create your project

### 2. Add Android App to Firebase

1. In your Firebase project, click "Add app" and select Android
2. Enter the package name: `com.purewords1611.android`
3. Download the `google-services.json` file
4. Replace the placeholder file at `app/google-services.json` with your downloaded file

### 3. Verify Integration

After replacing the `google-services.json` file:

1. Build the app: `./gradlew assembleDebug`
2. Run the app on a device or emulator
3. Check Firebase Console > Analytics > Events to see tracked events (may take a few hours for data to appear)

## Current Analytics Implementation

### Automatic Events

Firebase automatically tracks:
- `app_open` - When the app is opened
- `screen_view` - Screen navigation tracking
- `user_engagement` - User engagement metrics
- `session_start` - Session tracking

### Custom Events

The following custom events are tracked:

#### App Navigation
- `app_launch` - Tracked when the app starts
- `game_mode_selected` - When user selects a game mode
  - Parameters: `game_mode` (verse_game or word_grid)
- `return_to_menu` - When user returns to the menu
  - Parameters: `from_screen`

#### Game Events
- `game_start` - When a game begins
  - Parameters: `game_mode`
- `game_complete` - When a game is completed
  - Parameters: `game_mode`, `score`, `duration_ms`
- `game_reset` - When user resets a game
  - Parameters: `game_mode`

#### Word Grid Specific
- `word_submitted` - When a word is submitted in Word Grid
  - Parameters: `word`, `is_valid`, `score`, `word_length`

#### Verse Game Specific
- `verse_answer_validated` - When an answer is validated
  - Parameters: `is_correct`, `attempt_number`

## Privacy & User Consent

### Current Implementation
- Analytics is enabled by default
- No personally identifiable information (PII) is collected
- All tracking is anonymous

### Future Enhancements (Optional)
If you want to add user consent:

1. Add a settings screen with analytics opt-in/opt-out
2. Use `Firebase.analytics.setAnalyticsCollectionEnabled(enabled)` to control tracking
3. Update the Privacy Policy to reflect user choices

## Testing Analytics

### Debug Mode
To see analytics events in real-time during development:

```bash
adb shell setprop debug.firebase.analytics.app com.purewords1611.android
```

Then check the logcat output for analytics events.

### Disable Debug Mode
```bash
adb shell setprop debug.firebase.analytics.app .none.
```

### View Events in Firebase Console

1. Go to Firebase Console > Analytics > Events
2. Wait a few hours for initial data to populate
3. View real-time events in the DebugView (when debug mode is enabled)

## Code Structure

### Files Added
- `app/src/main/kotlin/com/purewords1611/android/PureWordsApplication.kt` - Application class for Firebase initialization
- `app/src/main/kotlin/com/purewords1611/android/analytics/AnalyticsManager.kt` - Centralized analytics wrapper
- `app/google-services.json` - Firebase configuration (placeholder - replace with your own)

### Files Modified
- `build.gradle.kts` - Added Google Services plugin
- `app/build.gradle.kts` - Added Firebase dependencies and plugin
- `app/proguard-rules.pro` - Added ProGuard rules for Firebase
- `app/src/main/AndroidManifest.xml` - Registered Application class
- `app/src/main/kotlin/com/purewords1611/android/MainActivity.kt` - Integrated analytics tracking

## Troubleshooting

### Build Errors
- **Error: google-services.json is missing** - Make sure you've downloaded and placed the file in the `app/` directory
- **Error: Plugin not found** - Sync your project with Gradle files

### Analytics Not Showing
- Wait at least 24 hours for initial data to appear in Firebase Console
- Enable debug mode to see real-time events
- Check that your `google-services.json` is properly configured
- Verify internet connectivity on the test device

### Placeholder Configuration
The included `google-services.json` is a placeholder. The app will build and run, but analytics won't be sent until you replace it with your actual Firebase configuration.

## Additional Resources

- [Firebase Analytics Documentation](https://firebase.google.com/docs/analytics)
- [Firebase Console](https://console.firebase.google.com/)
- [Analytics Events Reference](https://firebase.google.com/docs/reference/android/com/google/firebase/analytics/FirebaseAnalytics.Event)
- [Best Practices for Analytics](https://firebase.google.com/docs/analytics/best-practices)

## Maintenance

### Adding New Events
To add new analytics events:

1. Add a new method to `AnalyticsManager.kt`
2. Call the method where the event occurs
3. Document the event in this guide

### Example
```kotlin
// In AnalyticsManager.kt
fun trackFeatureUsed(featureName: String) {
    val params = Bundle().apply {
        putString("feature_name", featureName)
    }
    logEvent("feature_used", params)
}

// In your code
analyticsManager.trackFeatureUsed("favorite_verse")
```
