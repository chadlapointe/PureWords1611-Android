# App Configuration for PureWords1611

This document contains the configuration details for the PureWords1611 Android app.

## App Identity

### Package Name
**Production**: `com.purewords1611.android`  
**Alternative**: `com.purewords.kjv1611`

**Important**: Choose carefully - the package name cannot be changed after the first upload to Google Play.

### App Name
**Display Name**: PureWords1611  
**Full Name**: Pure Words 1611 - KJV Daily Verse  
**Short Name**: PW1611 (for notifications/home screen if needed)

## Version Information

### Semantic Versioning
Follow format: MAJOR.MINOR.PATCH

- **MAJOR**: Incompatible API changes or major feature overhauls
- **MINOR**: New features in a backwards-compatible manner
- **PATCH**: Backwards-compatible bug fixes

### Initial Release
```
Version Code: 1
Version Name: 1.0.0
```

### Version Code Scheme
Version code must increase with each release:
- Major.Minor.Patch → Version Code
- 1.0.0 → 1
- 1.0.1 → 2
- 1.1.0 → 3
- 2.0.0 → 4
- etc.

## Build Configuration

### Minimum SDK
```groovy
minSdkVersion 21  // Android 5.0 (Lollipop)
```
Covers ~99% of active devices (as of 2024)

### Target SDK
```groovy
targetSdkVersion 34  // Android 14
```
Update this with each new Android release

### Compile SDK
```groovy
compileSdkVersion 34  // Android 14
```
Should match targetSdkVersion

## App Permissions

### Required Permissions
```xml
<!-- For downloading daily verses -->
<uses-permission android:name="android.permission.INTERNET" />

<!-- For saving verses offline -->
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"
    android:maxSdkVersion="28" />

<!-- For notifications (Android 13+) -->
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />

<!-- For scheduling notifications -->
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
```

### Permissions NOT Used
The app should NOT request:
- Location permissions
- Camera permissions
- Microphone permissions
- Contacts access
- Phone state
- SMS/MMS access
- Calendar access
- Call logs

## App Features

### Core Features
1. **Daily Verse Display**
   - Shows one verse per day
   - Automatic daily refresh at midnight
   - Offline access to downloaded verses

2. **Verse History**
   - View past daily verses
   - Scroll through previous days
   - Limited history (e.g., 30 days)

3. **Favorites/Bookmarks**
   - Save favorite verses
   - Quick access to saved verses
   - Export/share favorites

4. **Sharing**
   - Share verses via social media
   - Copy verse to clipboard
   - Share as image (optional)

5. **Notifications**
   - Daily reminder notification
   - Customizable notification time
   - Toggle on/off in settings

6. **Settings**
   - Notification preferences
   - Text size adjustment
   - Theme selection (Light/Dark)
   - About/Credits screen

### Optional Features (Future Versions)
- Verse search functionality
- Bible book navigation
- Multiple verse selections per day
- Widget support
- Reading plans
- Note-taking capability

## Design Specifications

### Color Scheme
**Primary Colors**:
- Primary: #8B4513 (Saddle Brown - represents traditional Bible)
- Primary Dark: #5D2F0E
- Primary Light: #A66A3D
- Accent: #D4AF37 (Gold - represents value of God's Word)

**Background Colors**:
- Light theme background: #FAFAFA
- Light theme surface: #FFFFFF
- Dark theme background: #121212
- Dark theme surface: #1E1E1E

**Text Colors**:
- Primary text (light): #212121
- Secondary text (light): #757575
- Primary text (dark): #FFFFFF
- Secondary text (dark): #BDBDBD

### Typography
- **Display Font**: Serif (for verse text - traditional Bible feel)
  - Consider: "Crimson Text", "Literata", or system serif
- **Body Font**: Sans-serif (for UI elements)
  - Use system default or "Roboto"

**Text Sizes**:
- Verse text: 18-24sp (adjustable by user)
- Title: 20sp
- Subtitle: 16sp
- Body: 14sp
- Caption: 12sp

### Layout Guidelines
- Margins: 16dp (sides), 8dp (between elements)
- Card elevation: 2dp
- Corner radius: 4dp
- Button height: 48dp minimum (for touch targets)

## Data Storage

### Local Database (SQLite)
**Verses Table**:
```sql
CREATE TABLE verses (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    date TEXT NOT NULL UNIQUE,
    book TEXT NOT NULL,
    chapter INTEGER NOT NULL,
    verse_number INTEGER NOT NULL,
    verse_text TEXT NOT NULL,
    reference TEXT NOT NULL,
    is_favorite INTEGER DEFAULT 0,
    created_at INTEGER NOT NULL
);
```

**Settings Table**:
```sql
CREATE TABLE settings (
    key TEXT PRIMARY KEY,
    value TEXT NOT NULL,
    updated_at INTEGER NOT NULL
);
```

### SharedPreferences
Store lightweight preferences:
- Notification enabled: boolean
- Notification time: string (HH:mm format)
- Text size preference: int (0=small, 1=medium, 2=large)
- Theme preference: string ("light", "dark", "system")
- First launch flag: boolean
- Last version seen: int

### File Storage
- Cache directory: for temporary verse images
- App private directory: for database backups

## Network Configuration

### API Endpoints (If applicable)
```
Base URL: https://api.purewords1611.com/
Verse of the day: GET /api/v1/daily-verse?date=YYYY-MM-DD
Verse by reference: GET /api/v1/verse?book=Genesis&chapter=1&verse=1
```

### Offline Support
- Cache all downloaded verses
- Store at least 7 days of future verses
- Gracefully handle network errors
- Show cached content when offline

## App Signing

### Debug Signing
- Uses Android debug keystore automatically
- Location: `~/.android/debug.keystore`
- Do NOT use for release builds

### Release Signing
**Keystore Information** (Store securely, NOT in version control):
```
Keystore file: purewords1611-release.jks
Keystore password: [SECURE PASSWORD]
Key alias: purewords1611
Key password: [SECURE PASSWORD]
Validity: 10000 days (25+ years)
```

**Store keystore securely**:
- Cloud storage (encrypted)
- Password manager
- Secure backup drive
- Physical secure location

### Play App Signing
**Recommended**: Enroll in Google Play App Signing
- Google manages your app signing key
- You upload with an upload certificate
- More secure and flexible
- Can recover if upload key is lost

## Analytics and Monitoring (Optional)

### Crash Reporting
Consider using:
- Firebase Crashlytics (recommended)
- Or similar crash reporting service

### Analytics
If implementing analytics:
- Firebase Analytics
- Track: app opens, feature usage, retention
- Do NOT track: personal information, content viewed
- Ensure compliance with privacy policy

### Events to Track
- App launch
- Daily verse viewed
- Verse shared
- Verse favorited
- Settings changed
- Notification clicked

## Testing Configuration

### Unit Tests
Location: `app/src/test/java/`
- Test business logic
- Test data models
- Test utilities

### Instrumented Tests
Location: `app/src/androidTest/java/`
- Test UI components
- Test database operations
- Test app navigation

### Test Devices
Minimum test coverage:
- One phone with minSdk (Android 5.0)
- One phone with current Android version
- One tablet (7-inch or 10-inch)
- One device in dark mode
- One device with large text size

## Build Variants

### Debug Build
```groovy
debug {
    applicationIdSuffix ".debug"
    versionNameSuffix "-debug"
    debuggable true
    minifyEnabled false
}
```

### Release Build
```groovy
release {
    minifyEnabled true
    shrinkResources true
    proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
    debuggable false
}
```

## ProGuard Rules

Essential rules for release build:
```proguard
# Keep data model classes
-keep class com.purewords1611.android.model.** { *; }

# Keep database classes
-keep class com.purewords1611.android.database.** { *; }

# Keep Parcelables
-keepclassmembers class * implements android.os.Parcelable {
    public static final ** CREATOR;
}
```

## Localization

### Initial Release
- English (United States) - en-US

### Future Localization
Consider adding:
- Spanish (es)
- Portuguese (pt-BR)
- French (fr)
- German (de)
- Chinese Simplified (zh-CN)
- Korean (ko)
- Japanese (ja)

### Translatable Strings
Ensure all user-facing text is in `strings.xml`:
- No hardcoded strings in code
- Use string resources
- Provide context comments for translators

## Accessibility

### Requirements
- Content descriptions for all images
- Minimum touch target size: 48dp x 48dp
- Sufficient color contrast (WCAG AA)
- Support for TalkBack screen reader
- Scalable text (respect user's text size preference)
- No reliance on color alone for information

### Testing
Test with:
- TalkBack enabled
- Large text size (Settings → Display → Font size)
- High contrast mode
- Voice commands

## Dependencies Management

### Update Policy
- Check for updates monthly
- Update security-critical dependencies immediately
- Test thoroughly after major version updates
- Document breaking changes

### Key Dependencies
```groovy
// Android core
implementation 'androidx.core:core-ktx:1.x.x'
implementation 'androidx.appcompat:appcompat:1.x.x'

// UI components
implementation 'com.google.android.material:material:1.x.x'
implementation 'androidx.constraintlayout:constraintlayout:2.x.x'

// Lifecycle
implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.x.x'
implementation 'androidx.lifecycle:lifecycle-livedata-ktx:2.x.x'

// Database
implementation 'androidx.room:room-runtime:2.x.x'
kapt 'androidx.room:room-compiler:2.x.x'

// Networking (if needed)
implementation 'com.squareup.retrofit2:retrofit:2.x.x'
implementation 'com.squareup.okhttp3:okhttp:4.x.x'

// Kotlin coroutines
implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.x.x'
```

## Quality Assurance

### Code Quality Tools
- **Lint**: Built-in Android lint checks
- **Detekt**: Kotlin static analysis
- **ktlint**: Kotlin code style

### CI/CD (Continuous Integration)
Consider setting up:
- GitHub Actions
- Automated build on commit
- Automated tests
- Lint checks
- Build APK/AAB artifacts

## Release Checklist Summary

Before each release:
1. [ ] Increment version code
2. [ ] Update version name
3. [ ] Update release notes
4. [ ] Run all tests
5. [ ] Run lint checks
6. [ ] Test on real devices
7. [ ] Build signed AAB
8. [ ] Test signed build
9. [ ] Upload to Play Console
10. [ ] Monitor for crashes

## Support and Maintenance

### User Support
- Response time goal: 48 hours
- Support email: [your-email@example.com]
- FAQ document (create as issues arise)

### Update Frequency
- Bug fixes: As needed (hot fixes within days)
- Minor updates: Monthly or bi-monthly
- Major updates: Quarterly or bi-annually

### Deprecation Policy
- Support Android versions for at least 3 years
- Announce deprecated features in advance
- Provide migration guides

## Notes

- This configuration assumes a Kotlin-based Android app
- Adjust package names and specifics to match your actual implementation
- Keep sensitive information (keys, passwords) out of version control
- Review and update this document as the app evolves

---

**Document Version**: 1.0  
**Last Updated**: [Date]  
**Maintained By**: [Your Name]
