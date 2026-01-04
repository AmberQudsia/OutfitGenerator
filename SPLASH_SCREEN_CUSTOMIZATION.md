# Splash Screen Customization Guide

## Overview
A beautiful splash screen has been implemented with a gradient background and animated progress bar at the bottom.

## Files Created
1. **SplashActivity.kt** - Main splash screen logic
2. **activity_splash.xml** - Splash screen layout
3. **splash_background.xml** - Background gradient/image
4. **splash_progress_drawable.xml** - Custom progress bar styling

## How to Use Your Own Background Image

### Option 1: Use Your Custom Image (Recommended if you have a background image)

1. **Add your background image** to the drawable folder:
   - Place your image file (e.g., `splash_bg.jpg`, `splash_bg.png`, or `splash_bg.webp`) in:
     ```
     app/src/main/res/drawable/
     ```

2. **Update splash_background.xml**:
   ```xml
   <?xml version="1.0" encoding="utf-8"?>
   <layer-list xmlns:android="http://schemas.android.com/apk/res/android">
       <!-- Your custom background image -->
       <item>
           <bitmap
               android:src="@drawable/splash_bg"
               android:gravity="fill" />
       </item>
       
       <!-- Optional: Add a semi-transparent overlay for better text visibility -->
       <item>
           <shape android:shape="rectangle">
               <solid android:color="#40000000" />
           </shape>
       </item>
   </layer-list>
   ```

### Option 2: Keep the Gradient Background (Current Default)
The current implementation uses a beautiful gradient from indigo → purple → pink.
This is already implemented and looks professional!

### Option 3: Combine Both (Image + Overlay)
You can layer your image with a gradient overlay:
```xml
<?xml version="1.0" encoding="utf-8"?>
<layer-list xmlns:android="http://schemas.android.com/apk/res/android">
    <!-- Background Image -->
    <item>
        <bitmap
            android:src="@drawable/splash_bg"
            android:gravity="fill" />
    </item>
    
    <!-- Gradient Overlay -->
    <item>
        <shape android:shape="rectangle">
            <gradient
                android:angle="135"
                android:startColor="#806366F1"
                android:endColor="#80EC4899"
                android:type="linear" />
        </shape>
    </item>
</layer-list>
```

## Customization Options

### Change Splash Duration
In `SplashActivity.kt`, modify:
```kotlin
private val splashDelay: Long = 3000 // Change this value (milliseconds)
```

### Change Text and Colors
In `activity_splash.xml`, you can modify:
- App name and tagline text
- Text colors
- Progress bar color
- Logo/icon

### Change Progress Bar Style
Edit `splash_progress_drawable.xml` to customize the progress bar appearance.

## Features

✅ **Smooth Animations**
- Animated progress bar (0-100%)
- Animated loading text with dots
- Fade transition to Login screen

✅ **Professional Design**
- Beautiful gradient background
- Centered logo and app name
- Progress bar at the bottom
- Text shadows for better visibility

✅ **User Experience**
- Back button disabled (prevents accidental exit)
- No action bar (fullscreen experience)
- Automatic navigation to Login screen

## Testing
1. Build and run the app
2. The splash screen will appear first
3. Progress bar will animate from left to right
4. After 3 seconds, it will navigate to the Login screen

## Notes
- The splash screen is set as the launcher activity in AndroidManifest.xml
- It automatically closes after navigating to prevent users from returning to it
- The NoActionBar theme ensures a fullscreen, immersive experience


