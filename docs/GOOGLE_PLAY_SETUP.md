# Google Play Console Setup Guide

This document provides step-by-step instructions for setting up the Google Play Console account and registering the PureWords1611 Android app.

## Prerequisites

Before you begin, ensure you have:
- A Google account (Gmail)
- A valid payment method (one-time $25 registration fee)
- App details and assets ready (icon, screenshots, descriptions)
- A signed APK or AAB (Android App Bundle) file

## Part 1: Create Google Play Developer Account

### Step 1: Register for Google Play Console

1. Go to [Google Play Console](https://play.google.com/console)
2. Sign in with your Google account
3. Click **Create Developer Account**
4. Accept the Google Play Developer Distribution Agreement
5. Pay the one-time $25 registration fee
6. Complete your account details:
   - Developer name: Choose a public-facing name (e.g., "Pure Words 1611" or your organization name)
   - Contact email address
   - Website URL (optional but recommended)
   - Phone number

### Step 2: Set Up Account Verification

1. Google may require identity verification
2. Follow the verification process (may require government-issued ID)
3. Wait for verification approval (can take 24-48 hours)

### Step 3: Configure Account Settings

1. Navigate to **Settings** → **Developer account**
2. Complete your developer profile:
   - Add a developer profile picture/logo
   - Provide contact information
   - Add payment methods for paid apps (if applicable)

## Part 2: Create and Register the App

### Step 1: Create a New App

1. From the Google Play Console home page, click **Create app**
2. Fill in the basic app details:
   - **App name**: PureWords1611 (or "Pure Words 1611 - KJV Daily Verse")
   - **Default language**: English (United States)
   - **App or game**: App
   - **Free or paid**: Free
3. Declare if your app is:
   - A game or app
   - Whether it's free or paid
4. Accept the declarations and click **Create app**

### Step 2: Set Up Store Listing

Navigate to **Store presence** → **Main store listing** and complete:

#### Basic Information
- **App name**: PureWords1611
- **Short description** (80 characters max): Daily KJV Bible verses from the 1611 King James Version
- **Full description** (4000 characters max): See `STORE_LISTING.md` for prepared content

#### Graphics Assets
Required assets to prepare:
- **App icon**: 512 x 512 px, 32-bit PNG with alpha
- **Feature graphic**: 1024 x 500 px
- **Phone screenshots**: Minimum 2, up to 8 (JPEG or 24-bit PNG, no alpha)
  - Minimum dimension: 320px
  - Maximum dimension: 3840px
  - Aspect ratio: 16:9 or 9:16
- **7-inch tablet screenshots**: Minimum 2 (optional but recommended)
- **10-inch tablet screenshots**: Minimum 2 (optional but recommended)

#### Categorization
- **App category**: Books & Reference (or Lifestyle)
- **Tags**: Bible, KJV, Daily Verse, Scripture, Christian

#### Contact Details
- **Email**: Your support email address
- **Phone**: Optional
- **Website**: Optional but recommended

### Step 3: Content Rating

1. Navigate to **Policy** → **App content**
2. Click **Start questionnaire** under Content rating
3. Select your app category: Reference, News & Information
4. Answer questions about content (violence, sexual content, etc.)
   - For a Bible verse app, most answers will be "No"
5. Submit and receive your content rating

### Step 4: Privacy Policy

1. Navigate to **Policy** → **App content**
2. Under Privacy Policy, add your privacy policy URL
3. If you don't have a website, you can:
   - Host it on GitHub Pages
   - Use a free service like iubenda
   - See `PRIVACY_POLICY.md` for a template

### Step 5: Set Up App Access

1. Navigate to **Policy** → **App content**
2. Under **App access**, declare if:
   - All functionality is available without restrictions
   - Or if special access is needed
3. For PureWords1611, select "All functionality is available"

### Step 6: Ads Declaration

1. Navigate to **Policy** → **App content**
2. Under **Ads**, declare whether your app contains ads
3. Select **Yes** or **No** based on your app's monetization

### Step 7: Target Audience

1. Navigate to **Policy** → **App content**
2. Under **Target audience and content**, select:
   - Age groups: All ages (or specific age groups)
   - For a Bible app, typically "All ages" is appropriate

### Step 8: Data Safety

1. Navigate to **Policy** → **App content**
2. Under **Data safety**, declare:
   - What data you collect (if any)
   - How data is used
   - Security practices
3. For a simple Bible verse app with no user accounts:
   - Select "No data collected" if applicable
   - Or declare minimal data collection (analytics, crash reports)

## Part 3: Upload App Bundle

### Step 1: Prepare Your App Bundle

1. Build a signed release version of your app:
   ```bash
   ./gradlew bundleRelease
   ```
2. The AAB file will be in: `app/build/outputs/bundle/release/app-release.aab`
3. Ensure it's signed with your release keystore

### Step 2: Create a Release

1. Navigate to **Release** → **Production**
2. Click **Create new release**
3. Upload your AAB file
4. Set the release name (typically version name, e.g., "1.0.0")
5. Add release notes (what's new in this version)

### Step 3: Review and Roll Out

1. Complete all required sections (indicated by red exclamation marks)
2. Review the release for any warnings or errors
3. You cannot publish until all policy and content sections are complete
4. For first release, consider starting with:
   - **Internal testing**: Limited to specific email addresses
   - **Closed testing**: Up to defined testers
   - **Open testing**: Public beta
   - **Production**: Full public release

## Part 4: Complete Required Declarations

Before publishing, ensure you've completed:

- ✅ Store listing information
- ✅ Content rating questionnaire
- ✅ Privacy policy
- ✅ App access declaration
- ✅ Ads declaration
- ✅ Target audience selection
- ✅ Data safety section
- ✅ At least one release track with an uploaded app

## Part 5: Submit for Review

1. Once all sections are complete, navigate to **Publishing overview**
2. Review all sections to ensure everything is ready
3. Click **Send for review** or **Publish**
4. Google will review your app (typically 1-3 days, can be longer)
5. You'll receive an email when the review is complete

## Post-Launch Tasks

After your app is published:

1. **Monitor reviews**: Respond to user reviews promptly
2. **Check statistics**: Monitor installs, crashes, and ANRs
3. **Update regularly**: Keep your app updated with bug fixes and features
4. **Optimize listing**: Test different descriptions, screenshots, and graphics
5. **Set up alerts**: Configure email alerts for crashes and important events

## Common Issues and Solutions

### Issue: "Your app contains policy violations"
- **Solution**: Review the policy violation email carefully and make necessary changes

### Issue: "Your app is missing required screenshots"
- **Solution**: Ensure you have at least 2 phone screenshots uploaded

### Issue: "Privacy policy URL is not accessible"
- **Solution**: Verify your privacy policy URL is publicly accessible via HTTPS

### Issue: "Content rating questionnaire incomplete"
- **Solution**: Complete all questions in the content rating questionnaire

### Issue: "App bundle signature verification failed"
- **Solution**: Ensure your AAB is signed with the correct keystore

## Additional Resources

- [Google Play Console Help Center](https://support.google.com/googleplay/android-developer)
- [Launch Checklist](https://developer.android.com/distribute/best-practices/launch/launch-checklist)
- [App Bundle Documentation](https://developer.android.com/guide/app-bundle)
- [Store Listing Best Practices](https://developer.android.com/distribute/best-practices/launch/store-listing)

## Security Notes

- **Keep your signing key secure**: Store your keystore file in a secure location
- **Back up your keystore**: You cannot update your app without the original keystore
- **Use Play App Signing**: Consider enrolling in Google Play App Signing for added security
- **Enable two-factor authentication**: Protect your Google Play Console account

## Next Steps

After completing this setup:
1. Proceed to build the Android app (if not already built)
2. Prepare all required graphics assets
3. Test thoroughly before uploading
4. Follow the deployment checklist in `DEPLOYMENT_CHECKLIST.md`
