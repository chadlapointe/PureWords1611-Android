# Google Play Console - Quick Reference Card

**Project**: PureWords1611 - Daily KJV Bible Verses  
**Last Updated**: January 2, 2026  
**Purpose**: One-page quick reference for Google Play Console setup

---

## 🚀 Essential Links

| Resource | Link |
|----------|------|
| Google Play Console | https://play.google.com/console |
| Play Console Help | https://support.google.com/googleplay/android-developer |
| Android Developer Docs | https://developer.android.com/ |
| Privacy Policy Template | [docs/PRIVACY_POLICY.md](PRIVACY_POLICY.md) |
| App Listing Content | [docs/PLAY_CONSOLE_APP_LISTING_DRAFT.md](PLAY_CONSOLE_APP_LISTING_DRAFT.md) |

---

## 📋 5-Minute Setup Summary

### What You Need
- [ ] Google account
- [ ] $25 USD (one-time fee)
- [ ] 30 minutes + 1-2 days wait
- [ ] Support email address
- [ ] Privacy policy URL (HTTPS)
- [ ] App icon (512×512 px PNG)
- [ ] Feature graphic (1024×500 px)
- [ ] 2+ screenshots
- [ ] Signed AAB file

### Your 5 Tasks
1. **Create account** → play.google.com/console ($25, 30 min)
2. **Make graphics** → Icon, feature graphic, screenshots (2-4 hrs)
3. **Host privacy policy** → GitHub Pages or website (30 min)
4. **Build app** → `./gradlew bundleRelease` (varies)
5. **Fill forms** → Copy from PLAY_CONSOLE_APP_LISTING_DRAFT.md (1-2 hrs)

---

## 📱 App Information At-a-Glance

### Basic Info
```
App Name: PureWords1611
Package Name: com.purewords1611.android (suggested)
Category: Books & Reference
Price: Free
Ads: No
```

### Short Description (57/80 chars)
```
Daily KJV Bible verses from the 1611 King James Version
```

### Target Audience
```
All ages - Bible/devotional content
```

---

## 🎨 Required Assets Checklist

| Asset | Size | Format | Min | Max | Status |
|-------|------|--------|-----|-----|--------|
| App Icon | 512×512 | PNG (32-bit, alpha) | - | 1024KB | ☐ |
| Feature Graphic | 1024×500 | PNG/JPG | - | 1024KB | ☐ |
| Phone Screenshots | Min 320px | PNG/JPG (24-bit) | 2 | 8 | ☐ |
| 7" Tablet Screens | - | PNG/JPG | 0 | 8 | ☐ (opt) |
| 10" Tablet Screens | - | PNG/JPG | 0 | 8 | ☐ (opt) |

**Note**: Screenshots must be 16:9 or 9:16 aspect ratio, max dimension 3840px

---

## ✅ Content Rating Quick Answers

**Category**: Reference, News & Information

**All Questions for Bible App**:
- Violence? **NO**
- Sexual content? **NO**
- Profanity? **NO**
- Drugs/alcohol/tobacco? **NO**
- Gambling? **NO**
- User communication? **NO**
- Shares location? **NO**
- Purchases? **NO**
- Ads? **NO**

**Expected Rating**: Everyone / PEGI 3 / All Ages

---

## 🛡️ Policy Declarations Quick Guide

### Privacy Policy
```
Status: Required
Must be: HTTPS URL
Template: docs/PRIVACY_POLICY.md
Hosting: GitHub Pages (free)
```

### Data Safety
```
For simple Bible app:
☑ No data collected or shared with third parties
```

### App Access
```
☑ All functionality available without restrictions
```

### Ads
```
☑ No, my app does not contain ads
```

### Target Audience
```
☑ All ages
☐ Not primarily directed at children under 13
```

---

## 🔧 Build Commands Quick Reference

### Debug Build
```bash
./gradlew assembleDebug
```

### Release AAB (for Play Store)
```bash
./gradlew bundleRelease
```

### Run Tests
```bash
./gradlew test
./gradlew connectedAndroidTest
```

### Create Keystore
```bash
keytool -genkey -v -keystore purewords1611.keystore \
  -alias purewords1611 -keyalg RSA -keysize 2048 \
  -validity 10000
```

---

## 📞 Contact Information Template

```
Support Email: [your-email@example.com]
Developer Name: [Your Name/Organization]
Website: [https://yourwebsite.com] (optional)
Privacy Policy: [https://yoursite.com/privacy-policy.html]
```

**⚠️ Replace brackets with actual information!**

---

## 🚨 Critical Reminders

### ❗ DO NOT LOSE YOUR KEYSTORE
- Back up in 3 secure locations
- Store password safely
- Without it, you can NEVER update your app

### ❗ HTTPS Required
- Privacy policy must be HTTPS (not HTTP)
- Test in incognito browser before submitting

### ❗ Package Name is Permanent
- Choose carefully: `com.purewords1611.android`
- Cannot change after first upload

### ❗ Test Before Submit
- Install AAB on real devices
- Test all features thoroughly
- Check for crashes

---

## ⏱️ Timeline Quick View

| Phase | Time | Can Wait? |
|-------|------|-----------|
| Create account | 30 min | No |
| Account verification | 24-48 hrs | Yes (parallel) |
| Create graphics | 2-4 hrs | Yes (parallel) |
| Privacy policy | 30 min | Yes (parallel) |
| Build app | Varies | No |
| Fill forms | 1-2 hrs | No |
| Google review | 1-3 days | Yes (waiting) |

**Total Active Time**: ~4-7 hours (excluding app development)

---

## 💰 Cost Breakdown

| Item | Cost | Required |
|------|------|----------|
| Play Developer Account | $25 | Yes |
| Graphics Designer | $0-200 | Optional |
| Privacy Hosting | $0 | Yes (free) |
| **Minimum Total** | **$25** | - |

---

## 📚 Documentation Quick Map

| Need This | Go Here |
|-----------|---------|
| Action plan | [MANUAL_SETUP_INSTRUCTIONS.md](MANUAL_SETUP_INSTRUCTIONS.md) |
| Overview | [GOOGLE_PLAY_SETUP_SUMMARY.md](GOOGLE_PLAY_SETUP_SUMMARY.md) |
| Copy-paste content | [PLAY_CONSOLE_APP_LISTING_DRAFT.md](PLAY_CONSOLE_APP_LISTING_DRAFT.md) |
| Step-by-step | [GOOGLE_PLAY_SETUP.md](GOOGLE_PLAY_SETUP.md) |
| Progress tracker | [PLAY_CONSOLE_SETUP_TRACKER.md](PLAY_CONSOLE_SETUP_TRACKER.md) |
| Asset specs | [ASSETS_GUIDE.md](ASSETS_GUIDE.md) |
| Privacy template | [PRIVACY_POLICY.md](PRIVACY_POLICY.md) |
| Quick start | [QUICKSTART.md](QUICKSTART.md) |

---

## 🎯 Pre-Submit Checklist (Ultra-Short)

**Account**
- [ ] Play Developer account created ($25 paid)
- [ ] Account verified (if required)
- [ ] 2FA enabled

**Content**
- [ ] Privacy policy live on HTTPS
- [ ] Support email active
- [ ] All placeholders replaced

**Assets**
- [ ] App icon (512×512)
- [ ] Feature graphic (1024×500)
- [ ] 2+ screenshots

**Technical**
- [ ] App built and tested
- [ ] Signed AAB created
- [ ] Keystore backed up (3 places!)

**Forms**
- [ ] Store listing complete
- [ ] Content rating done
- [ ] All policies declared
- [ ] AAB uploaded

**✅ All green checkmarks in Play Console?** → Submit!

---

## 🆘 Common Issues & Quick Fixes

| Issue | Quick Fix |
|-------|-----------|
| "Privacy policy URL not accessible" | Verify HTTPS, test in incognito |
| "Missing required screenshots" | Upload at least 2 phone screenshots |
| "Content rating incomplete" | Answer all questions in questionnaire |
| "App bundle signature failed" | Rebuild with correct keystore |
| "Field exceeds character limit" | Check character counts in draft doc |

---

## 📱 Phone Checklist (Print This)

Before final submission, physically check:
- [ ] Can install AAB on phone?
- [ ] App opens without crashing?
- [ ] Privacy policy URL opens in browser?
- [ ] Support email sends test message?
- [ ] All graphics look professional?
- [ ] Keystore file backed up?
- [ ] All checkboxes in tracker done?
- [ ] Read through app description one final time?

---

## 🎉 After Submission

1. **Wait** 1-3 days for Google review
2. **Monitor** email for updates
3. **If approved** → Celebrate! App is live 🎊
4. **If rejected** → Read email, fix issues, resubmit

---

## 💡 Pro Tips

✅ **Do This**:
- Start account creation early (verification takes time)
- Create graphics in parallel while waiting
- Test AAB on multiple devices before upload
- Use Play Console's "Save as draft" frequently
- Enable Play App Signing for extra security

❌ **Don't Do This**:
- Rush through policy questionnaires
- Forget to back up keystore
- Use HTTP for privacy policy
- Skip testing on real devices
- Ignore Play Console warnings

---

## 🔗 Support Contacts

- **Play Console Help**: https://support.google.com/googleplay/android-developer
- **This Repo Issues**: https://github.com/chadlapointe/PureWords1611-Android/issues
- **Android Dev Community**: https://developer.android.com/community

---

**🎯 Quick Reference Version 1.0**  
**Last Updated**: January 2, 2026  
**For**: PureWords1611 Android App

**Print this page and keep it handy during setup!**
