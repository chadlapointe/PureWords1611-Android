# Google Play Deployment Checklist

Use this checklist to ensure all steps are completed before submitting your app to the Google Play Store.

## Pre-Deployment Phase

### Developer Account Setup
- [ ] Google Play Developer account created
- [ ] $25 registration fee paid
- [ ] Identity verification completed (if required)
- [ ] Developer profile completed
- [ ] Two-factor authentication enabled
- [ ] Payment methods configured (if using paid features)

### App Preparation
- [ ] App fully developed and tested
- [ ] All features working as intended
- [ ] App tested on multiple devices/screen sizes
- [ ] App tested on different Android versions (minimum SDK to target SDK)
- [ ] All lint warnings reviewed and addressed
- [ ] ProGuard/R8 configured for release build (if applicable)
- [ ] App signing configured with release keystore
- [ ] Release keystore backed up securely (CRITICAL)
- [ ] Build variant set to "release"
- [ ] Debug logging disabled
- [ ] App version code and version name set correctly

### Build and Sign
- [ ] Generated signed release AAB (Android App Bundle)
  ```bash
  ./gradlew bundleRelease
  ```
- [ ] Verified AAB signature is correct
- [ ] Tested AAB on physical devices using `bundletool`
- [ ] APK size optimized (removed unused resources, enabled shrinking)
- [ ] App startup time is acceptable
- [ ] No crashes during testing

### Legal and Compliance
- [ ] Privacy policy created
- [ ] Privacy policy hosted at public HTTPS URL
- [ ] Terms of service created (if applicable)
- [ ] Copyright and trademark compliance verified
- [ ] License agreements for third-party libraries reviewed
- [ ] App name doesn't infringe on trademarks

## Google Play Console Setup

### App Creation
- [ ] New app created in Google Play Console
- [ ] App name finalized (PureWords1611)
- [ ] Default language set (English - United States)
- [ ] App category selected (Free)
- [ ] Developer declarations accepted

### Store Listing
- [ ] **App name** entered (50 characters max)
- [ ] **Short description** written (80 characters max)
- [ ] **Full description** written (4000 characters max)
- [ ] App icon uploaded (512x512 px, PNG with alpha)
- [ ] Feature graphic uploaded (1024x500 px)
- [ ] Phone screenshots uploaded (minimum 2, maximum 8)
  - [ ] Screenshot 1: Main screen
  - [ ] Screenshot 2: Key feature
  - [ ] Additional screenshots (optional)
- [ ] 7-inch tablet screenshots uploaded (optional but recommended)
- [ ] 10-inch tablet screenshots uploaded (optional but recommended)
- [ ] Promo video link added (optional)
- [ ] App category selected (Books & Reference)
- [ ] Tags added (if available)
- [ ] Contact email provided
- [ ] Contact phone number (optional)
- [ ] Website URL (optional)

### Content Rating
- [ ] Content rating questionnaire started
- [ ] App category selected in questionnaire
- [ ] All questions answered truthfully
- [ ] Questionnaire submitted
- [ ] Content rating certificate received
- [ ] Ratings appropriate for your app (should be All Ages for Bible app)

### Privacy Policy
- [ ] Privacy policy URL added to Store Listing
- [ ] Privacy policy URL publicly accessible
- [ ] Privacy policy covers all data collection practices
- [ ] Privacy policy URL uses HTTPS

### App Content Declarations

#### App Access
- [ ] App access section completed
- [ ] Declared whether special access is needed
- [ ] If restricted, access instructions provided

#### Ads
- [ ] Ads declaration completed
- [ ] Selected "No ads" (if applicable) or "Contains ads"
- [ ] If ads present, ad format disclosed

#### Target Audience
- [ ] Target age groups selected
- [ ] For PureWords1611: "All ages" recommended
- [ ] Compliance with children's online privacy laws confirmed

#### Data Safety
- [ ] Data safety form completed
- [ ] Data collection practices disclosed:
  - [ ] No data collected (if true)
  - [ ] Or specific data types listed (analytics, crash logs, etc.)
- [ ] Data usage explained
- [ ] Data sharing practices disclosed
- [ ] Security practices described
- [ ] Data deletion policy stated

#### Government Apps
- [ ] Declared whether app is for government use (No for PureWords1611)

### App Pricing and Distribution

#### Pricing
- [ ] App pricing set (Free)
- [ ] Confirmed no plans for paid features (or configured if applicable)

#### Countries/Regions
- [ ] Selected countries where app will be available
- [ ] Considered all regions or specific markets
- [ ] Reviewed any country-specific requirements

#### Device Categories
- [ ] Phone selected
- [ ] Tablet selected (recommended)
- [ ] Wear OS (optional)
- [ ] TV (optional)
- [ ] Auto (optional)

### Release Management

#### App Releases
- [ ] Release track selected (Internal/Closed/Open/Production)
- [ ] Signed AAB uploaded
- [ ] Release name set (e.g., "1.0.0")
- [ ] Release notes written (What's new in this version)
- [ ] Rollout percentage selected (100% or staged rollout)
- [ ] Release reviewed for errors/warnings

#### App Signing
- [ ] Enrolled in Google Play App Signing (recommended)
- [ ] Upload certificate uploaded (if using Play App Signing)
- [ ] OR Verified using own signing key consistently

#### Release Tracks Decision
For first release, choose one:
- [ ] **Internal Testing**: 1-100 testers, immediate rollout
- [ ] **Closed Testing**: Up to defined testers, requires opt-in
- [ ] **Open Testing**: Public beta, anyone can join
- [ ] **Production**: Full public release (can start with this)

## Pre-Submission Review

### Testing
- [ ] App tested on real devices
- [ ] App tested on different screen sizes
- [ ] App tested on different Android versions
- [ ] All critical user flows tested
- [ ] Offline functionality tested
- [ ] Notifications tested (if applicable)
- [ ] Share functionality tested
- [ ] App permissions requested appropriately
- [ ] No crashes during testing
- [ ] Performance is acceptable (smooth scrolling, fast loading)

### Policy Compliance
- [ ] App complies with Google Play Policies
- [ ] No prohibited content (malware, violence, hate speech, etc.)
- [ ] Copyright and IP rights respected
- [ ] User data and privacy requirements met
- [ ] Accurate store listing (no misleading information)
- [ ] No spam or manipulative behavior

### Quality Guidelines
- [ ] App provides substantial value
- [ ] No broken or missing features
- [ ] UI is polished and professional
- [ ] App is stable (no frequent crashes)
- [ ] App follows Material Design guidelines (recommended)
- [ ] Text is readable and properly formatted
- [ ] Images are high quality
- [ ] Navigation is intuitive

### Security
- [ ] No hardcoded credentials or API keys
- [ ] HTTPS used for all network communications
- [ ] Sensitive data encrypted
- [ ] Input validation implemented
- [ ] No known security vulnerabilities
- [ ] Dependencies up to date

## Submission

### Final Checks
- [ ] All required sections marked complete in Play Console
- [ ] No red warning indicators in Play Console
- [ ] Publishing overview shows "Ready to publish"
- [ ] Store listing preview reviewed
- [ ] All links tested (privacy policy, website, etc.)
- [ ] Contact information verified

### Submit for Review
- [ ] Clicked "Submit for review" or "Publish"
- [ ] Confirmation email received
- [ ] App status changed to "In Review"

## Post-Submission

### Monitoring
- [ ] Email notifications enabled for Play Console
- [ ] Prepared to respond to policy questions (if any)
- [ ] Monitoring status in Play Console daily
- [ ] Ready to make changes if review issues arise

### Approval
- [ ] App review completed (typically 1-7 days)
- [ ] App approved and published
- [ ] App visible in Google Play Store
- [ ] Tested finding app via search
- [ ] Tested installation from Play Store
- [ ] Confirmed app works when installed from Play Store

## Post-Launch

### Immediate Tasks
- [ ] Monitor crash reports (Google Play Console → Quality)
- [ ] Monitor user reviews
- [ ] Respond to initial reviews
- [ ] Check download/install statistics
- [ ] Verify all features work in production
- [ ] Set up alerts for ANR (App Not Responding) issues

### Marketing and Promotion
- [ ] Share app link on social media
- [ ] Update website with Play Store badge (if applicable)
- [ ] Send announcement to mailing list (if applicable)
- [ ] Submit to app review sites (optional)
- [ ] Create promotional materials

### Ongoing Maintenance
- [ ] Schedule regular app updates
- [ ] Monitor for OS version updates (new Android releases)
- [ ] Track user feedback and feature requests
- [ ] Plan next version features
- [ ] Keep dependencies updated
- [ ] Respond to user reviews regularly
- [ ] Monitor security advisories

## Emergency Procedures

### If App is Rejected
1. [ ] Read rejection email carefully
2. [ ] Identify specific policy violations
3. [ ] Make necessary changes
4. [ ] Update AAB and/or store listing
5. [ ] Add explanation in "Release notes" about changes
6. [ ] Resubmit for review
7. [ ] Contact Play Console support if rejection unclear

### If Critical Bug Found
1. [ ] Fix bug immediately
2. [ ] Test thoroughly
3. [ ] Increment version code/name
4. [ ] Build new signed AAB
5. [ ] Create emergency release in Play Console
6. [ ] Submit for expedited review (if available)
7. [ ] Communicate with users via update notes

### If App is Suspended
1. [ ] Contact Google Play support immediately
2. [ ] Review suspension notification
3. [ ] Address all policy violations
4. [ ] Submit appeal with detailed explanation
5. [ ] Be prepared to make significant changes

## Important Reminders

### Security
⚠️ **CRITICAL**: Keep your signing keystore file backed up in multiple secure locations. You cannot update your app without it.

⚠️ **CRITICAL**: Never share your keystore password or upload it to version control.

### Release Keys
- Keystore file location: [Record your keystore location]
- Keystore password: [Store securely, NOT here]
- Key alias: [Record your key alias]
- Key password: [Store securely, NOT here]

### Version Management
- Current version code: 1
- Current version name: 1.0.0
- Next version code: 2
- Next version name: 1.0.1 (or 1.1.0 for features)

### Contact Information
- Support email: [your-email@example.com]
- Play Console account: [your-google-account@gmail.com]
- Developer name: [Your Developer Name]

## Useful Commands

### Build Release AAB
```bash
./gradlew bundleRelease
```

### Generate Signed AAB
```bash
./gradlew bundleRelease \
  -Pandroid.injected.signing.store.file=/path/to/keystore.jks \
  -Pandroid.injected.signing.store.password=your-keystore-password \
  -Pandroid.injected.signing.key.alias=your-key-alias \
  -Pandroid.injected.signing.key.password=your-key-password
```

### Test AAB Locally
```bash
# Download bundletool
# Then extract APKs from AAB
bundletool build-apks --bundle=app-release.aab --output=app.apks

# Install on connected device
bundletool install-apks --apks=app.apks
```

### Check APK Size
```bash
./gradlew app:assembleRelease
ls -lh app/build/outputs/apk/release/
```

## Resources

- [Google Play Console](https://play.google.com/console)
- [Developer Policy Center](https://play.google.com/about/developer-content-policy/)
- [Launch Checklist](https://developer.android.com/distribute/best-practices/launch/launch-checklist)
- [App Signing Documentation](https://developer.android.com/studio/publish/app-signing)
- [Android App Bundle](https://developer.android.com/guide/app-bundle)

---

**Last Updated**: [Date]  
**App Version**: 1.0.0  
**Completed By**: [Your Name]

## Notes Section

Use this section to record important dates, decisions, and issues encountered:

- App created in Play Console: [Date]
- First submission: [Date]
- Approval received: [Date]
- First production release: [Date]
- Issues encountered: [List any issues and resolutions]
