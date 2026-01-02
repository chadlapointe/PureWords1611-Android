# PureWords1611 - Daily KJV Bible Verses

[![Platform](https://img.shields.io/badge/platform-Android-green.svg)](https://developer.android.com/)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![Build Status](https://img.shields.io/badge/build-setup_complete-blue.svg)](SETUP_COMPLETE.md)

**Pure Words 1611** is an Android application that delivers daily Bible verses from the authentic 1611 King James Version. Start each day with God's Word, conveniently accessible on your mobile device.

> **📱 Project Status**: Android project structure is complete! See [SETUP_COMPLETE.md](SETUP_COMPLETE.md) for details.
>
> ⚠️ **Note**: Build requires Maven repository access. See [BUILD_ENVIRONMENT_ISSUE.md](BUILD_ENVIRONMENT_ISSUE.md) for details.

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

### 🎯 Start Here - Complete Setup Summary

**🚀 [Getting Started Guide](docs/GETTING_STARTED.md)** - ⭐ **NEW! START HERE!** Your navigation guide - pick your path and begin

**📋 [Quick Reference Card](docs/QUICK_REFERENCE.md)** - ⚡ **NEW!** One-page quick reference - essential info at a glance

**📋 [Manual Setup Instructions](docs/MANUAL_SETUP_INSTRUCTIONS.md)** - Your action plan - exactly what YOU need to do (5 tasks)

**📚 [Google Play Setup Summary](docs/GOOGLE_PLAY_SETUP_SUMMARY.md)** - Complete overview of what's been prepared and what you need to do

These documents provide:
- ✅ Status of all documentation (100% complete)
- 📋 Clear action plan: 5 manual tasks (4-7 hours + $25)
- ⏱️ Time estimates and cost breakdown for each task
- ✅ Pre-submission checklist
- 🎯 Recommended order and timeline

### Documentation

📚 **Learn the Templates**: [Documentation Setup Guide](docs/SETUP_GUIDE.md) - How to use these templates

#### Planning & Concept
- **[App Concept & Feature List](docs/APP_CONCEPT.md)** - 📱 Complete app concept definition, feature roadmap, user personas, and technical architecture

#### Play Store Setup Guides
- **[Getting Started Guide](docs/GETTING_STARTED.md)** - 🚀 **NEW!** Your navigation hub - understand the docs and pick your path
- **[Quick Reference Card](docs/QUICK_REFERENCE.md)** - ⚡ **NEW!** One-page quick reference with essential info
- **[Manual Setup Instructions](docs/MANUAL_SETUP_INSTRUCTIONS.md)** - ⭐ **ACTION PLAN**: Your 5 tasks to get on Play Store
- **[Google Play Setup Summary](docs/GOOGLE_PLAY_SETUP_SUMMARY.md)** - Complete overview: What's ready vs what you need to do
- **[Quick Start Guide](docs/QUICKSTART.md)** - Fast-track guide for Google Play registration
- **[Google Play Setup Guide](docs/GOOGLE_PLAY_SETUP.md)** - Complete walkthrough for creating a Google Play Developer account and registering the app
- **[Setup Progress Tracker](docs/PLAY_CONSOLE_SETUP_TRACKER.md)** - 📊 Step-by-step progress tracker with checkboxes for the entire setup process
- **[Pre-Flight Checklist](docs/PRE_FLIGHT_CHECKLIST.md)** - ✈️ **NEW!** Final verification checklist before submission

#### Ready-to-Use Content
- **[App Listing Draft](docs/PLAY_CONSOLE_APP_LISTING_DRAFT.md)** - ✅ Copy-paste ready content for Google Play Console (descriptions, categories, etc.)
- **[Store Listing Content](docs/STORE_LISTING.md)** - Pre-written descriptions, keywords, and marketing copy for the Play Store
- **[Privacy Policy](docs/PRIVACY_POLICY.md)** - Template privacy policy required for Play Store submission

#### Assets & Design
- **[Assets Guide](docs/ASSETS_GUIDE.md)** - Detailed specifications for all required graphics and screenshots
- **[Screenshot Mockup Guide](docs/SCREENSHOT_MOCKUP_GUIDE.md)** - 📸 Detailed mockup specifications and design guide for creating Play Store screenshots

#### Technical & Deployment
- **[Deployment Checklist](docs/DEPLOYMENT_CHECKLIST.md)** - Step-by-step checklist to ensure nothing is missed before deployment
- **[App Configuration](docs/APP_CONFIG.md)** - Technical configuration details, build settings, and app specifications

### Quick Start for Play Store Submission

**New to Google Play?** Follow this simple workflow:

1. **🚀 Navigate**: Start with [**Getting Started Guide**](docs/GETTING_STARTED.md) - choose your path (first-timer, experienced, or expert)
2. **⚡ Quick Facts**: Review [Quick Reference Card](docs/QUICK_REFERENCE.md) - one-page essential info
3. **🎯 Action Plan**: Read [**Manual Setup Instructions**](docs/MANUAL_SETUP_INSTRUCTIONS.md) - your exact 5-task action plan
4. **📊 Full Overview**: Read [Setup Summary](docs/GOOGLE_PLAY_SETUP_SUMMARY.md) to understand what's ready and what you need to do
5. **📖 Quick Guide**: Review [Quick Start Guide](docs/QUICKSTART.md) for a condensed walkthrough
6. **📋 Track Progress**: Use [Setup Progress Tracker](docs/PLAY_CONSOLE_SETUP_TRACKER.md) as you complete each step
7. **✍️ Copy Content**: Copy content from [App Listing Draft](docs/PLAY_CONSOLE_APP_LISTING_DRAFT.md) directly into Play Console
8. **🎨 Create Assets**: Create graphics using specifications in [Assets Guide](docs/ASSETS_GUIDE.md) and [Screenshot Mockup Guide](docs/SCREENSHOT_MOCKUP_GUIDE.md)
9. **✈️ Final Check**: Use [Pre-Flight Checklist](docs/PRE_FLIGHT_CHECKLIST.md) before submission
10. **✅ Last Verify**: Use [Deployment Checklist](docs/DEPLOYMENT_CHECKLIST.md) for final verification

**Detailed Step-by-Step Process:**

1. Create your Google Play Developer account ($25 one-time fee)
2. Customize and host your [Privacy Policy](docs/PRIVACY_POLICY.md)
3. Prepare all required assets (app icon, screenshots, feature graphic)
4. Build and sign your release AAB
5. Fill out Play Console using ready-to-paste content from [App Listing Draft](docs/PLAY_CONSOLE_APP_LISTING_DRAFT.md)
6. Complete all required declarations
7. Submit for review

**Everything you need is in the `/docs` folder!**

## 📋 Project Structure

```
PureWords1611-Android/
├── docs/                                    # Documentation
│   ├── GETTING_STARTED.md                   # 🚀 Navigation guide - START HERE! (NEW!)
│   ├── QUICK_REFERENCE.md                   # ⚡ One-page quick reference card (NEW!)
│   ├── PRE_FLIGHT_CHECKLIST.md              # ✈️ Final pre-submission checklist (NEW!)
│   ├── MANUAL_SETUP_INSTRUCTIONS.md         # ⭐ Your action plan - 5 tasks
│   ├── GOOGLE_PLAY_SETUP_SUMMARY.md         # Complete setup overview
│   ├── APP_CONCEPT.md                       # Complete app concept & feature definition
│   ├── SETUP_GUIDE.md                       # How to use the documentation templates
│   ├── QUICKSTART.md                        # Quick start guide
│   ├── GOOGLE_PLAY_SETUP.md                 # Play Store setup guide (detailed)
│   ├── PLAY_CONSOLE_SETUP_TRACKER.md        # Progress tracker with checkboxes
│   ├── PLAY_CONSOLE_APP_LISTING_DRAFT.md    # Copy-paste ready content for Play Console
│   ├── ASSETS_GUIDE.md                      # Graphics assets specifications
│   ├── SCREENSHOT_MOCKUP_GUIDE.md           # Screenshot design and mockup guide
│   ├── STORE_LISTING.md                     # Store listing content
│   ├── PRIVACY_POLICY.md                    # Privacy policy template
│   ├── DEPLOYMENT_CHECKLIST.md              # Deployment checklist
│   └── APP_CONFIG.md                        # App configuration details
├── app/                                     # Android app source (to be created)
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/                        # Kotlin/Java source files
│   │   │   ├── res/                         # Resources (layouts, strings, etc.)
│   │   │   └── AndroidManifest.xml
│   │   ├── test/                            # Unit tests
│   │   └── androidTest/                     # Instrumented tests
│   └── build.gradle                         # App-level build configuration
├── gradle/                                  # Gradle wrapper files
├── build.gradle                             # Project-level build configuration
├── settings.gradle                          # Project settings
└── README.md                                # This file
```

## 🔐 Security and Privacy

PureWords1611 respects your privacy:

- ✅ No personal information collected
- ✅ No user accounts required
- ✅ Anonymous analytics for app improvement (Firebase Analytics)
- ✅ All data stored locally on device
- ✅ 100% free, no ads, no in-app purchases

See our [Privacy Policy](docs/PRIVACY_POLICY.md) for complete details.

## 📊 Analytics

This app uses Firebase Analytics to track anonymous usage patterns and improve user experience. See [Analytics Setup Guide](docs/ANALYTICS_SETUP.md) for configuration details.

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

- **Issues**: [GitHub Issues](https://github.com/chadlapointe/PureWords1611-Android/issues)
- **Repository**: [chadlapointe/PureWords1611-Android](https://github.com/chadlapointe/PureWords1611-Android)

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
