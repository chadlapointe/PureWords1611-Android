# Graphics Assets Requirements for Google Play Store

This document provides detailed specifications for all graphics assets required for publishing PureWords1611 on the Google Play Store.

## 📐 Asset Specifications Overview

| Asset Type | Dimensions | Format | Required | Quantity |
|------------|------------|--------|----------|----------|
| App Icon | 512 x 512 px | PNG (32-bit with alpha) | ✅ Yes | 1 |
| Feature Graphic | 1024 x 500 px | JPEG or PNG (24-bit, no alpha) | ✅ Yes | 1 |
| Phone Screenshots | Various | JPEG or PNG (24-bit, no alpha) | ✅ Yes | 2-8 |
| 7" Tablet Screenshots | Various | JPEG or PNG (24-bit, no alpha) | ⚪ Optional | 2-8 |
| 10" Tablet Screenshots | Various | JPEG or PNG (24-bit, no alpha) | ⚪ Optional | 2-8 |
| Promo Video | 30-120 sec | YouTube link | ⚪ Optional | 1 |
| TV Banner | 1280 x 720 px | PNG | ⚪ Optional | 1 |

## 🎨 Required Assets (Must Have)

### 1. App Icon (High-Res)

**Specifications**:
- **Dimensions**: 512 x 512 pixels (exactly)
- **Format**: 32-bit PNG with alpha channel
- **File size**: Maximum 1024 KB
- **Color space**: sRGB

**Design Guidelines**:
- ✅ Simple and recognizable
- ✅ Works well at small sizes
- ✅ Unique and memorable
- ✅ Avoid text (logo/symbol preferred)
- ✅ Consider background transparency
- ❌ Don't use Android UI elements
- ❌ Don't include rounded corners (system adds them)
- ❌ Don't use heavy drop shadows

**Design Ideas for PureWords1611**:
- Open Bible with "1611" visible
- Cross with ornate design
- Stylized "PW" monogram
- Bible with rays of light
- Classic religious iconography in brown/gold tones

**File naming**: `app_icon_512.png`

---

### 2. Feature Graphic

**Specifications**:
- **Dimensions**: 1024 x 500 pixels (exactly)
- **Format**: JPEG or 24-bit PNG (no alpha channel)
- **File size**: Maximum 1024 KB
- **Color space**: sRGB

**Design Guidelines**:
- ✅ Eye-catching and attractive
- ✅ Showcases app's purpose
- ✅ Include app name/logo
- ✅ Use brand colors
- ✅ Avoid too much text
- ✅ High contrast for visibility
- ❌ Don't make it too busy
- ❌ Don't use low-res images
- ❌ Don't include device frames

**Design Ideas for PureWords1611**:
- Bible with "Pure Words 1611" text overlay
- Verse of the day displayed on elegant background
- Cross and Bible imagery with app name
- "Daily KJV Bible Verses" tagline with imagery
- Warm, inviting colors (browns, golds, creams)

**File naming**: `feature_graphic_1024x500.png`

**Important**: This graphic appears:
- At the top of your store listing
- In promotional spaces in the Play Store
- In search results (sometimes)

---

### 3. Phone Screenshots

**Specifications**:
- **Quantity**: Minimum 2, maximum 8
- **Format**: JPEG or 24-bit PNG (no alpha channel)
- **Aspect ratio**: 16:9 or 9:16 (portrait or landscape)
- **Minimum dimension**: 320 pixels
- **Maximum dimension**: 3840 pixels
- **Recommended**: 1080 x 1920 px (portrait) or 1920 x 1080 px (landscape)

**Design Guidelines**:
- ✅ Show actual app screens
- ✅ Use real content (not lorem ipsum)
- ✅ Highlight key features
- ✅ Keep status bar clean
- ✅ Use same orientation for all screenshots
- ✅ Show app in best light
- ❌ Don't add device frames (Play Store may add them)
- ❌ Don't use heavily edited/filtered images
- ❌ Don't show content that violates policies

**Recommended Screenshots for PureWords1611**:

#### Screenshot 1: Daily Verse Screen
- **Content**: Main screen showing today's verse
- **Highlights**: Clean layout, readable text, verse reference
- **Caption**: "Start your day with God's Word"

#### Screenshot 2: Verse History/Archive
- **Content**: List of previous daily verses
- **Highlights**: Easy browsing, date organization
- **Caption**: "Browse past verses anytime"

#### Screenshot 3: Favorites/Bookmarks
- **Content**: Saved favorite verses
- **Highlights**: Bookmarking feature, quick access
- **Caption**: "Save and revisit meaningful verses"

#### Screenshot 4: Share Feature
- **Content**: Share dialog or social media preview
- **Highlights**: Easy sharing capability
- **Caption**: "Share verses with friends and family"

#### Screenshot 5: Settings (Optional)
- **Content**: Settings screen with notifications, themes
- **Highlights**: Customization options
- **Caption**: "Personalize your experience"

#### Screenshot 6: Dark Mode (Optional)
- **Content**: App in dark theme
- **Highlights**: Theme support
- **Caption**: "Beautiful light and dark themes"

**File naming**: 
- `phone_screenshot_1_daily_verse.png`
- `phone_screenshot_2_history.png`
- `phone_screenshot_3_favorites.png`
- etc.

**Pro Tips**:
1. Take screenshots on a device with clean status bar (full battery, strong signal)
2. Use Android Studio's screenshot tool for consistent quality
3. Consider adding subtle text overlays to highlight features
4. Show variety - different screens/features
5. Order them to tell a story about your app

---

## 📱 Optional But Recommended Assets

### 4. 7-inch Tablet Screenshots

**Specifications**:
- **Quantity**: Minimum 2, maximum 8
- **Format**: JPEG or 24-bit PNG (no alpha channel)
- **Recommended**: 1200 x 1920 px (portrait) or 1920 x 1200 px (landscape)

**Why Include**:
- Shows your app works well on tablets
- Increases user confidence
- Better conversion for tablet users

**If you don't have a tablet**: Use Android Studio emulator with 7" tablet AVD

**File naming**: `tablet_7_screenshot_1.png`, etc.

---

### 5. 10-inch Tablet Screenshots

**Specifications**:
- **Quantity**: Minimum 2, maximum 8
- **Format**: JPEG or 24-bit PNG (no alpha channel)
- **Recommended**: 1600 x 2560 px (portrait) or 2560 x 1600 px (landscape)

**Why Include**:
- Demonstrates tablet optimization
- Shows responsive design
- Appeals to tablet users

**File naming**: `tablet_10_screenshot_1.png`, etc.

---

### 6. Promo Video

**Specifications**:
- **Length**: 30 seconds to 2 minutes
- **Format**: YouTube video (public or unlisted)
- **Aspect ratio**: 16:9 recommended

**Content Ideas**:
- Quick app walkthrough (30-60 seconds)
- Show key features
- Include captions (many watch without sound)
- Add background music (royalty-free)
- End with call-to-action

**Example Script**:
```
[0:00-0:05] "Introducing PureWords1611"
[0:05-0:15] "Daily Bible verses from the 1611 King James Version"
[0:15-0:25] "Save favorites, share with friends, customize your experience"
[0:25-0:30] "Download today - 100% free, no ads"
```

**Tools for Creating**:
- Screen recording on Android device
- Video editing: DaVinci Resolve (free), iMovie, Premiere Pro
- Royalty-free music: YouTube Audio Library, Incompetech

---

## 🎯 Creating Your Assets

### Option 1: DIY with Design Tools

**Recommended Tools**:
- **Figma** (free, web-based): Best for app screenshots and graphics
- **Canva** (free with limits): Templates and easy design
- **GIMP** (free, desktop): Photoshop alternative
- **Inkscape** (free, desktop): Vector graphics
- **Adobe Photoshop/Illustrator** (paid): Professional tools

**Templates**:
- Google Play Store Asset Templates (search online)
- Figma Play Store Template (community resources)
- Android Asset Studio (for icons)

### Option 2: Hire a Designer

**Where to Find**:
- Fiverr ($5-$50 for basic packages)
- Upwork (hourly or project-based)
- 99designs (design contests)
- Freelancer.com

**Budget Estimate**:
- Basic package (icon + feature graphic): $20-$50
- Complete package (all required assets): $100-$300
- Premium package (all assets + promo video): $300-$1000

### Option 3: Use Screenshot Tools

For screenshots specifically:

**Android Studio**:
1. Run app on emulator
2. Take screenshot: Camera icon in emulator
3. Saved automatically to desktop

**Physical Device**:
1. Enable Developer Options
2. Connect via ADB
3. Use `adb shell screencap` command
4. Or use device screenshot (Power + Volume Down)

**Screenshot Enhancement**:
- Remove personal info from status bar
- Use Android Studio's "Frame Screenshot" tool
- Add subtle device frames (optional)
- Ensure consistent styling across all screenshots

---

## ✅ Asset Preparation Checklist

### Before You Start
- [ ] Reviewed all specification requirements
- [ ] Chosen design tools/approach
- [ ] Gathered brand assets (colors, fonts, logo)
- [ ] App is in a presentable state for screenshots

### App Icon
- [ ] Designed at 512x512px
- [ ] Saved as 32-bit PNG with alpha
- [ ] Tested at various sizes (looks good when small)
- [ ] Verified file size under 1024KB
- [ ] Follows design guidelines
- [ ] Saved as: `app_icon_512.png`

### Feature Graphic
- [ ] Designed at 1024x500px
- [ ] Saved as JPEG or 24-bit PNG (no alpha)
- [ ] Eye-catching and on-brand
- [ ] Includes app name or clear branding
- [ ] Verified file size under 1024KB
- [ ] Saved as: `feature_graphic_1024x500.png`

### Phone Screenshots
- [ ] Captured at least 2 screenshots
- [ ] Captured up to 8 screenshots (recommended: 4-6)
- [ ] All same orientation (portrait recommended)
- [ ] High quality (1080x1920px or similar)
- [ ] Show different features/screens
- [ ] No personal/sensitive information visible
- [ ] Status bar looks clean
- [ ] Saved with descriptive names

### Tablet Screenshots (Optional)
- [ ] 7-inch tablet screenshots captured
- [ ] 10-inch tablet screenshots captured
- [ ] Show responsive design
- [ ] High quality

### Promo Video (Optional)
- [ ] Video created (30-120 seconds)
- [ ] Uploaded to YouTube
- [ ] Set to Public or Unlisted
- [ ] Video is engaging and clear
- [ ] Has captions (accessibility)

### Organization
- [ ] All assets in organized folder
- [ ] Clear file naming convention
- [ ] Backup copies made
- [ ] Ready to upload to Play Console

---

## 📂 Recommended Folder Structure

```
assets/
├── play_store/
│   ├── icon/
│   │   └── app_icon_512.png
│   ├── feature_graphic/
│   │   └── feature_graphic_1024x500.png
│   ├── screenshots/
│   │   ├── phone/
│   │   │   ├── phone_1_daily_verse.png
│   │   │   ├── phone_2_history.png
│   │   │   ├── phone_3_favorites.png
│   │   │   └── phone_4_share.png
│   │   ├── tablet_7/
│   │   │   └── (optional tablet screenshots)
│   │   └── tablet_10/
│   │       └── (optional tablet screenshots)
│   └── promo_video/
│       └── video_link.txt
└── source_files/
    └── (Your .psd, .fig, .ai source files)
```

---

## 🎨 Design Resources

### Color Palette Suggestions for PureWords1611

**Traditional/Classic Theme**:
- Primary: #8B4513 (Saddle Brown - old Bible leather)
- Accent: #D4AF37 (Gold - valuable)
- Background: #F5F5DC (Beige/Cream - aged paper)
- Text: #2C1810 (Dark Brown)

**Modern/Clean Theme**:
- Primary: #5C4033 (Coffee Brown)
- Accent: #E8C468 (Soft Gold)
- Background: #FFFFFF (White)
- Text: #333333 (Dark Gray)

### Typography Suggestions

**For Verse Text** (Serif):
- Crimson Text
- Literata
- Georgia
- Playfair Display

**For UI** (Sans-serif):
- Roboto (Android default)
- Open Sans
- Lato
- Inter

### Icon Resources

- [Material Icons](https://fonts.google.com/icons) (Free)
- [Font Awesome](https://fontawesome.com/) (Free/Pro)
- [Flaticon](https://www.flaticon.com/) (Free with attribution)
- [The Noun Project](https://thenounproject.com/) (Free/Paid)

---

## 🚫 Common Mistakes to Avoid

1. ❌ **Wrong dimensions** → Assets will be rejected
2. ❌ **Wrong format** (e.g., JPEG instead of PNG for icon) → Rejected
3. ❌ **Including alpha channel where not allowed** → Rejected
4. ❌ **File too large** → Upload will fail
5. ❌ **Low quality/pixelated images** → Poor first impression
6. ❌ **Inconsistent branding** → Looks unprofessional
7. ❌ **Too much text** → Hard to read, cluttered
8. ❌ **Misleading screenshots** → Violates policies
9. ❌ **Personal information in screenshots** → Privacy concern
10. ❌ **Using copyrighted images** → Legal issues

---

## 📝 Asset Quality Checklist

Before uploading, verify each asset:

- [ ] Correct dimensions (exactly as specified)
- [ ] Correct format (PNG/JPEG as required)
- [ ] Correct color mode (32-bit with alpha or 24-bit no alpha)
- [ ] File size within limits
- [ ] High resolution and sharp (not blurry)
- [ ] No spelling errors in text
- [ ] Brand consistent
- [ ] Professional looking
- [ ] Loads/displays correctly
- [ ] No copyright violations

---

## 🔗 Helpful Links

- [Google Play Asset Requirements](https://support.google.com/googleplay/android-developer/answer/9866151)
- [Material Design Icons](https://material.io/resources/icons/)
- [Android Asset Studio](http://romannurik.github.io/AndroidAssetStudio/)
- [Figma Play Store Templates](https://www.figma.com/@googleplay)

---

**Need Help?**

If you're stuck on creating assets:
1. Check design tools mentioned above
2. Consider hiring a designer (budget: $50-$200 for basics)
3. Use templates from Figma or Canva
4. Refer to successful apps in your category for inspiration

**Questions?** Open an issue on GitHub or refer to full documentation.

---

*Last Updated: [Date]*  
*Document Version: 1.0*
