# Google Play Store Submission - Pre-Flight Checklist

**Project**: PureWords1611 - Daily KJV Bible Verses  
**Date**: _________________  
**Submitted By**: _________________  
**Version**: 1.0.0  
**Last Updated**: January 2, 2026

---

## ✈️ Purpose

This is your **FINAL CHECKLIST** before submitting your app to Google Play Store. Go through each item carefully. Every checkbox must be checked before you click "Submit for Review."

**Estimated Time**: 30-45 minutes to complete this checklist

---

## 🔐 SECTION 1: Account & Access (Critical)

### Google Play Developer Account
- [ ] Account created at play.google.com/console
- [ ] $25 registration fee paid
- [ ] Developer profile complete (name, email, phone)
- [ ] Account verified (if Google requested verification)
- [ ] Can successfully log into Play Console
- [ ] Two-factor authentication (2FA) enabled
- [ ] Recovery email/phone added to account

**Notes**: _________________________________________

**⚠️ CRITICAL**: Do you have full access to the Google account used? **YES** / **NO**

---

## 📝 SECTION 2: Documentation & Legal (Required)

### Privacy Policy
- [ ] Privacy policy customized from template
- [ ] All `[placeholder]` text replaced with real information
- [ ] Privacy policy uploaded to web hosting
- [ ] Privacy policy accessible via HTTPS (not HTTP)
- [ ] Tested URL in incognito/private browser window
- [ ] URL does not return 404 or error
- [ ] Privacy policy accurately describes app's data practices
- [ ] Date on privacy policy is current (2026)

**Privacy Policy URL**: _________________________________________

**Test Result** (paste in browser): ☐ Loads successfully

### Contact Information
- [ ] Support email address created and active
- [ ] Support email can receive messages (send test)
- [ ] Support email will be monitored regularly
- [ ] Developer/organization name finalized
- [ ] Website URL prepared (if including one)

**Support Email**: _________________________________________  
**Developer Name**: _________________________________________  
**Website** (optional): _________________________________________

---

## 🎨 SECTION 3: Graphics & Assets (Required)

### App Icon
- [ ] Created at exactly 512 × 512 pixels
- [ ] Format: 32-bit PNG with alpha channel
- [ ] File size under 1024 KB
- [ ] Icon looks clear and professional
- [ ] Icon recognizable at small sizes
- [ ] Icon matches app's purpose/brand
- [ ] No pixelation or blurriness
- [ ] Follows Material Design guidelines (optional but recommended)

**File Name**: _________________________________________  
**File Size**: __________ KB

### Feature Graphic
- [ ] Created at exactly 1024 × 500 pixels
- [ ] Format: PNG or JPEG
- [ ] File size under 1024 KB
- [ ] Graphic looks professional
- [ ] Text is readable (if included)
- [ ] No important content in bottom-right corner (Play icon overlay)
- [ ] Represents app accurately

**File Name**: _________________________________________  
**File Size**: __________ KB

### Phone Screenshots
- [ ] Minimum 2 screenshots created
- [ ] Recommended 4-6 screenshots prepared
- [ ] Format: JPEG or 24-bit PNG (no alpha)
- [ ] Minimum dimension: 320px
- [ ] Maximum dimension: 3840px
- [ ] Aspect ratio: 16:9 or 9:16
- [ ] Screenshots show actual app content
- [ ] Screenshots are recent/accurate
- [ ] Text is readable
- [ ] Professional appearance

**Number of Screenshots**: __________  
**Screenshot Files**: ☐ Ready in folder

### Optional Assets (Recommended)
- [ ] 7-inch tablet screenshots (if targeting tablets)
- [ ] 10-inch tablet screenshots (if targeting tablets)
- [ ] Promotional video (YouTube URL)

---

## 🔧 SECTION 4: Technical Build (Critical)

### App Development
- [ ] App fully developed and feature-complete
- [ ] All planned v1.0 features implemented
- [ ] No placeholder or "TODO" content in app
- [ ] App has been tested thoroughly
- [ ] No known critical bugs or crashes

### Testing
- [ ] Tested on minimum SDK device (API 21)
- [ ] Tested on current SDK device (API 34)
- [ ] Tested on at least 2 different physical devices
- [ ] Tested on different screen sizes (phone, tablet)
- [ ] All features work as expected
- [ ] App doesn't crash on startup
- [ ] App doesn't crash during normal use
- [ ] Notifications work (if implemented)
- [ ] Sharing feature works (if implemented)
- [ ] Offline mode works (if implemented)
- [ ] Permissions are requested appropriately

**Devices Tested**:
1. _________________________________________
2. _________________________________________
3. _________________________________________

### Build Configuration
- [ ] Package name finalized (e.g., com.purewords1611.android)
- [ ] Version code set correctly (1 for first release)
- [ ] Version name set correctly (1.0.0)
- [ ] Minimum SDK set to 21 (Android 5.0)
- [ ] Target SDK set to 34 (Android 14)
- [ ] Compile SDK set to 34 or higher
- [ ] App name matches Play Store listing
- [ ] All required permissions declared in AndroidManifest.xml
- [ ] No unnecessary permissions requested

**Package Name**: _________________________________________  
**Version Code**: __________  
**Version Name**: __________

### Signing
- [ ] Release keystore created
- [ ] Keystore password documented (stored securely)
- [ ] Key alias documented (stored securely)
- [ ] Key password documented (stored securely)
- [ ] Keystore backed up in Location 1: _________________________________________
- [ ] Keystore backed up in Location 2: _________________________________________
- [ ] Keystore backed up in Location 3: _________________________________________
- [ ] Signing configuration added to build.gradle
- [ ] Release build uses correct keystore

**⚠️ CRITICAL**: Without this keystore, you can NEVER update your app. Confirm backups exist!

### Release Build
- [ ] Release AAB built successfully: `./gradlew bundleRelease`
- [ ] AAB file located at: `app/build/outputs/bundle/release/app-release.aab`
- [ ] AAB file is not empty (check file size > 1 MB)
- [ ] AAB installs correctly using `bundletool`
- [ ] Installed version shows correct version name
- [ ] Proguard/R8 enabled (if using obfuscation)
- [ ] Proguard rules tested (app still works with obfuscation)

**AAB File Size**: __________ MB  
**AAB Creation Date**: __________

---

## 📋 SECTION 5: Play Console Content (Required)

### Store Listing - Basic Information
- [ ] App name entered (under 50 characters)
- [ ] Short description entered (under 80 characters)
- [ ] Short description is compelling and clear
- [ ] Full description entered (under 4000 characters)
- [ ] Full description accurately describes app
- [ ] Full description includes key features
- [ ] Full description has no spelling/grammar errors
- [ ] App icon uploaded (512×512)
- [ ] Feature graphic uploaded (1024×500)
- [ ] Screenshots uploaded (minimum 2)
- [ ] Category selected: Books & Reference
- [ ] Contact email entered
- [ ] Privacy policy URL entered
- [ ] Store listing shows green checkmark in Play Console

**App Name**: _________________________________________  
**Character Count**: ___/50

**Short Description Character Count**: ___/80  
**Full Description Character Count**: ___/4000

### Content Rating
- [ ] Content rating questionnaire started
- [ ] Questionnaire category selected: Reference
- [ ] All questions answered honestly
- [ ] Violence questions answered: NO
- [ ] Sexual content questions answered: NO
- [ ] Language questions answered: NO
- [ ] Controlled substances questions answered: NO
- [ ] Gambling questions answered: NO
- [ ] User interaction questions answered correctly
- [ ] Questionnaire submitted
- [ ] Rating certificate received
- [ ] Content rating shows green checkmark in Play Console

**Expected Rating**: Everyone / PEGI 3 / All Ages

### App Content - Privacy Policy
- [ ] Privacy policy URL entered
- [ ] URL uses HTTPS (required)
- [ ] URL verified and accessible
- [ ] Privacy policy content is accurate
- [ ] Shows green checkmark in Play Console

### App Content - App Access
- [ ] App access section completed
- [ ] Selected: "All functionality is available without restrictions"  
  **OR**
- [ ] If login required: Provided test credentials
- [ ] Shows green checkmark in Play Console

### App Content - Ads
- [ ] Ads declaration completed
- [ ] Selected correct option:
  - [ ] "No, my app does not contain ads" (for ad-free app)
  - [ ] "Yes, my app contains ads" (if applicable)
- [ ] Shows green checkmark in Play Console

### App Content - Target Audience & Content
- [ ] Target audience section completed
- [ ] Age groups selected: All ages
- [ ] Declared: "Not primarily directed at children under 13"
- [ ] Shows green checkmark in Play Console

### App Content - Data Safety
- [ ] Data safety section completed
- [ ] Data collection accurately declared:
  - [ ] "No data collected or shared" (if true)
  - [ ] OR specific data types listed accurately
- [ ] Data usage purposes declared (if collecting data)
- [ ] Data sharing practices declared
- [ ] Security practices selected appropriately
- [ ] Shows green checkmark in Play Console

**Data Collection**: ☐ None  ☐ Analytics only  ☐ Other: __________

### App Content - Government Apps
- [ ] Government app declaration completed (select "No" for most apps)

### Store Presence - Countries & Regions
- [ ] Countries/regions selected
- [ ] Recommended: "All current and future countries" selected
- [ ] OR specific countries manually selected

**Distribution**: ☐ All countries  ☐ Specific: __________

### Pricing & Distribution
- [ ] App price set to "Free"
- [ ] In-app purchases declared correctly:
  - [ ] No in-app purchases (for free app)
  - [ ] OR in-app products listed
- [ ] Distribution agreements accepted

---

## 🚀 SECTION 6: Release Track (Required)

### Choose Release Track
- [ ] Release track chosen:
  - [ ] Internal Testing (up to 100 testers, instant publishing)
  - [ ] Closed Testing (named testers, instant publishing)
  - [ ] Open Testing (public beta, requires review)
  - [ ] Production (full public release, requires review)

**Recommended for First Release**: Internal or Closed Testing first

### Release Creation
- [ ] Release created in chosen track
- [ ] Release name entered (e.g., "1.0.0")
- [ ] Release notes prepared
- [ ] Release notes are clear and informative
- [ ] Release notes under character limit
- [ ] AAB file uploaded to release
- [ ] Upload successful (no errors)
- [ ] Google Play App Signing enrollment completed (recommended)
- [ ] Release shows "Ready to publish" status

**Release Name**: _________________________________________  
**Track**: _________________________________________

### Release Notes
```
[Paste your release notes here for verification]







```

**Character Count**: ___/500 (or track limit)

---

## ✅ SECTION 7: Final Verification (Critical)

### Play Console Status
- [ ] All dashboard sections show GREEN checkmarks
- [ ] No RED exclamation marks or errors
- [ ] No YELLOW warnings that need addressing
- [ ] "Publishing overview" page shows "Ready to publish"
- [ ] Reviewed "What's new" section if exists

### Content Accuracy
- [ ] App name matches what's in the app
- [ ] Package name is final (cannot be changed!)
- [ ] Version information is correct
- [ ] All descriptions accurately represent the app
- [ ] No misleading claims or promises
- [ ] No copyrighted content used without permission
- [ ] No trademark violations
- [ ] Screenshots show actual app content (not mockups)

### Policy Compliance
- [ ] Read Google Play Developer Program Policies
- [ ] App complies with all policies
- [ ] No prohibited content (violence, hate speech, etc.)
- [ ] No deceptive behavior or misleading functionality
- [ ] Permissions are justified and necessary
- [ ] Privacy policy matches actual data practices
- [ ] All declarations are truthful and accurate

**Policy Review**: ☐ Reviewed ☐ Compliant

### Cross-Reference Check
- [ ] Compared app description to PLAY_CONSOLE_APP_LISTING_DRAFT.md
- [ ] All placeholders replaced with real information
- [ ] No "TODO" or "[INSERT]" text remaining
- [ ] Support email is real (not placeholder)
- [ ] Privacy policy URL is real (not placeholder)
- [ ] All dates are current (2026, not 2024 or earlier)

---

## 🎯 SECTION 8: Team & Communication

### Stakeholder Notification
- [ ] Team informed of pending submission
- [ ] Support team ready to monitor support email
- [ ] Marketing team notified (if applicable)
- [ ] Social media ready (if announcing launch)

### Post-Submission Plan
- [ ] Know who will monitor Play Console daily
- [ ] Plan for responding to Google's review feedback
- [ ] Plan for responding to user reviews (post-launch)
- [ ] Plan for monitoring crash reports
- [ ] First update roadmap prepared

**Primary Contact for Play Console**: _________________________________________  
**Backup Contact**: _________________________________________

---

## 🚨 SECTION 9: Emergency Readiness

### Backup Verification
- [ ] All source code backed up (Git repository)
- [ ] Keystore files backed up (3 locations minimum)
- [ ] Keystore passwords documented securely
- [ ] Build configuration documented
- [ ] Privacy policy source backed up
- [ ] Graphics source files backed up (PSD, AI, etc.)

### Rollback Preparedness
- [ ] Can build previous version if needed
- [ ] Can revert changes if issues found
- [ ] Have plan for emergency hotfix if critical bug found

---

## ✈️ FINAL GO/NO-GO DECISION

### Count Your Checkmarks

**Total Required Checkboxes**: ~150  
**Your Checked Boxes**: __________

**Completion Percentage**: __________%

### Critical Sections Status

| Section | Status |
|---------|--------|
| Account & Access | ☐ Complete |
| Documentation & Legal | ☐ Complete |
| Graphics & Assets | ☐ Complete |
| Technical Build | ☐ Complete |
| Play Console Content | ☐ Complete |
| Release Track | ☐ Complete |
| Final Verification | ☐ Complete |
| Team & Communication | ☐ Complete |
| Emergency Readiness | ☐ Complete |

### Final Checklist Summary

- [ ] **ALL** required checkboxes are checked
- [ ] **ALL** critical sections are complete
- [ ] **NO** placeholders remain in any content
- [ ] **ALL** files are ready and accessible
- [ ] **KEYSTORE** is backed up in 3+ locations
- [ ] **PRIVACY POLICY** URL loads via HTTPS
- [ ] **AAB** installs and runs on real device
- [ ] **PLAY CONSOLE** shows all green checkmarks
- [ ] **POLICIES** reviewed and app is compliant
- [ ] **TEAM** is notified and ready

---

## 🎯 GO/NO-GO DECISION

Based on the above checklist:

**Decision**: ☐ GO (Submit Now)  ☐ NO-GO (Fix Issues First)

**If NO-GO, what needs to be fixed?**
_________________________________________________________________
_________________________________________________________________
_________________________________________________________________
_________________________________________________________________

**If GO, who is authorizing this submission?**

**Name**: _________________________________________  
**Date**: _________________________________________  
**Time**: _________________________________________

---

## 🚀 SUBMISSION EXECUTION

### During Submission
- [ ] Logged into Play Console
- [ ] Navigated to app's publishing overview
- [ ] Reviewed one final time
- [ ] Clicked "Send for Review" or "Publish" button
- [ ] Received confirmation screen
- [ ] Submission email received from Google

**Submission Date/Time**: _________________________________________  
**Confirmation Number** (if provided): _________________________________________

### Immediate Post-Submission
- [ ] Screenshot of confirmation screen taken
- [ ] Submission documented in project notes
- [ ] Team notified of successful submission
- [ ] Support email monitored for Google communication
- [ ] Calendar reminder set to check status in 24 hours

---

## 📧 EXPECTED TIMELINE

| Event | Timeframe |
|-------|-----------|
| Submission confirmation | Immediate |
| Google review begins | Within 24 hours |
| Review completes | 1-3 days (typically) |
| App goes live (if approved) | Automatically or on schedule |

**Next Check Date**: _________________________________________

---

## 🎉 POST-APPROVAL CHECKLIST

(Complete this section after Google approves your app)

- [ ] Approval email received
- [ ] App verified live on Play Store
- [ ] App installs from Play Store successfully
- [ ] Team notified of approval
- [ ] Social media announcement (if planned)
- [ ] Support email monitoring intensified
- [ ] Play Console monitoring daily for:
  - [ ] Crash reports
  - [ ] ANR (App Not Responding) reports
  - [ ] User reviews
  - [ ] Install statistics
- [ ] Celebration! 🎊

**Approval Date**: _________________________________________  
**Play Store Link**: _________________________________________

---

## 🔴 IF REJECTED CHECKLIST

(Complete this section if Google rejects your app)

- [ ] Rejection email read carefully
- [ ] Specific issues identified
- [ ] Root cause understood
- [ ] Fix plan created
- [ ] Fixes implemented
- [ ] Re-tested thoroughly
- [ ] This pre-flight checklist completed again
- [ ] Resubmitted

**Rejection Reason**: _________________________________________  
**Fix Applied**: _________________________________________  
**Resubmission Date**: _________________________________________

---

## 📞 HELP & SUPPORT

If you're unsure about any item:

- **Documentation**: Check docs/GOOGLE_PLAY_SETUP.md
- **Google Help**: https://support.google.com/googleplay/android-developer
- **Issues**: https://github.com/chadlapointe/PureWords1611-Android/issues

---

## ✍️ SIGN-OFF

I have reviewed this entire checklist and confirm that all items are complete and accurate.

**Name**: _________________________________________  
**Signature**: _________________________________________  
**Date**: _________________________________________  
**Time**: _________________________________________

---

**🎯 Pre-Flight Checklist Version 1.0**  
**Document Created**: January 2, 2026  
**For**: PureWords1611 Android App  
**Google Play Console Submission**

**Print this checklist and fill it out by hand as you go through each item!**

---

## 📝 NOTES SECTION

Use this space for any additional notes, observations, or issues encountered:

_________________________________________________________________
_________________________________________________________________
_________________________________________________________________
_________________________________________________________________
_________________________________________________________________
_________________________________________________________________
_________________________________________________________________
_________________________________________________________________
_________________________________________________________________
_________________________________________________________________
