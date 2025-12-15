# PureWords1611 - Daily KJV Bible Verses

[![Platform](https://img.shields.io/badge/platform-Android-green.svg)](https://developer.android.com/)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

**Pure Words 1611** is an Android application that delivers daily Bible verses from the authentic 1611 King James Version. Start each day with God's Word, conveniently accessible on your mobile device.

## 📖 About

> "The words of the LORD are pure words: as silver tried in a furnace of earth, purified seven times." - Psalm 12:6 (KJV)

PureWords1611 brings the timeless wisdom of the 1611 King James Bible to your Android device with a fresh verse delivered daily. The app features:

- 📅 Daily verse delivery from the 1611 KJV
- 💾 Offline access to verses
- 🔔 Optional daily notifications
- ⭐ Bookmark favorite verses
- 📤 Share verses with others
- 🌙 Light and dark theme support

## 🚀 Getting Started

### Prerequisites

- Android Studio Arctic Fox or later
- JDK 11 or higher
- Android SDK with minimum API level 21 (Android 5.0)
- Git

### Building the App

```bash
# Clone the repository
git clone https://github.com/chadlapointe/PureWords1611-Android.git
cd PureWords1611-Android

# Build debug APK
./gradlew assembleDebug

# Build release AAB (for Play Store)
./gradlew bundleRelease
```

### Running Tests

```bash
# Run unit tests
./gradlew test

# Run instrumented tests (requires connected device/emulator)
./gradlew connectedAndroidTest
```

## 📱 Google Play Store Setup

This repository includes comprehensive documentation for setting up and publishing the app to the Google Play Store.

### Documentation

📚 **Start Here**: [Documentation Setup Guide](docs/SETUP_GUIDE.md) - Learn how to use these templates

- **[Quick Start Guide](docs/QUICKSTART.md)** - ⚡ Fast-track guide for Google Play registration
- **[Google Play Setup Guide](docs/GOOGLE_PLAY_SETUP.md)** - Complete walkthrough for creating a Google Play Developer account and registering the app
- **[Assets Guide](docs/ASSETS_GUIDE.md)** - Detailed specifications for all required graphics and screenshots
- **[Store Listing Content](docs/STORE_LISTING.md)** - Pre-written descriptions, keywords, and marketing copy for the Play Store
- **[Privacy Policy](docs/PRIVACY_POLICY.md)** - Template privacy policy required for Play Store submission
- **[Deployment Checklist](docs/DEPLOYMENT_CHECKLIST.md)** - Step-by-step checklist to ensure nothing is missed before deployment
- **[App Configuration](docs/APP_CONFIG.md)** - Technical configuration details, build settings, and app specifications

### Quick Start for Play Store Submission

**New to Google Play?** Start with the [Quick Start Guide](docs/QUICKSTART.md) for a condensed walkthrough!

**Detailed Process:**

1. Review the [Google Play Setup Guide](docs/GOOGLE_PLAY_SETUP.md)
2. Create your Google Play Developer account ($25 one-time fee)
3. Prepare required assets using the [Assets Guide](docs/ASSETS_GUIDE.md) (app icon, screenshots, feature graphic)
4. Complete the [Deployment Checklist](docs/DEPLOYMENT_CHECKLIST.md)
5. Build and sign your release AAB
6. Upload to Google Play Console
7. Complete all required declarations
8. Submit for review

## 📋 Project Structure

```
PureWords1611-Android/
├── docs/                          # Documentation
│   ├── SETUP_GUIDE.md            # How to use the documentation templates
│   ├── QUICKSTART.md             # Quick start guide
│   ├── GOOGLE_PLAY_SETUP.md      # Play Store setup guide
│   ├── ASSETS_GUIDE.md           # Graphics assets specifications
│   ├── STORE_LISTING.md          # Store listing content
│   ├── PRIVACY_POLICY.md         # Privacy policy template
│   ├── DEPLOYMENT_CHECKLIST.md   # Deployment checklist
│   └── APP_CONFIG.md             # App configuration details
├── app/                           # Android app source (to be created)
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/             # Kotlin/Java source files
│   │   │   ├── res/              # Resources (layouts, strings, etc.)
│   │   │   └── AndroidManifest.xml
│   │   ├── test/                 # Unit tests
│   │   └── androidTest/          # Instrumented tests
│   └── build.gradle              # App-level build configuration
├── gradle/                        # Gradle wrapper files
├── build.gradle                  # Project-level build configuration
├── settings.gradle               # Project settings
└── README.md                     # This file
```

## 🔐 Security and Privacy

PureWords1611 respects your privacy:

- ✅ No personal information collected
- ✅ No user accounts required
- ✅ No tracking or analytics (optional)
- ✅ All data stored locally on device
- ✅ 100% free, no ads, no in-app purchases

See our [Privacy Policy](docs/PRIVACY_POLICY.md) for complete details.

## 🛠️ Technology Stack

- **Language**: Kotlin
- **Minimum SDK**: API 21 (Android 5.0 Lollipop)
- **Target SDK**: API 34 (Android 14)
- **Architecture**: MVVM (Model-View-ViewModel)
- **Database**: Room (SQLite)
- **Dependency Injection**: Hilt/Koin (TBD)
- **Networking**: Retrofit + OkHttp (for verse downloads)
- **Async Operations**: Kotlin Coroutines
- **UI**: Material Design Components

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📧 Contact

For questions, support, or feedback:

- **Email**: [your-email@example.com]
- **Issues**: [GitHub Issues](https://github.com/chadlapointe/PureWords1611-Android/issues)

## 🙏 Acknowledgments

- The 1611 King James Version Bible text (Public Domain)
- Material Design icons and components
- All contributors to this project

## 📚 Additional Resources

- [Android Developer Documentation](https://developer.android.com/)
- [Google Play Console Help](https://support.google.com/googleplay/android-developer)
- [Material Design Guidelines](https://material.io/design)
- [King James Bible Online](https://www.kingjamesbibleonline.org/)

---

**Made with ❤️ for spreading God's Word**

*"Thy word is a lamp unto my feet, and a light unto my path." - Psalm 119:105 (KJV)*
