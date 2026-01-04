# Login Screen Design Documentation

## Overview
The login screen has been redesigned with a modern, sleek dark theme featuring neon accents and a custom outfit icon.

## Design Features

### 🎨 Visual Elements

1. **Background**
   - Dark blue gradient (#0B4F6C → #01394C)
   - Professional and modern appearance
   - Provides excellent contrast for UI elements

2. **Logo**
   - Custom vector drawable with clothes hanger icon
   - Features shirt and pants outline in neon cyan (#00E5B4)
   - Dark circular background for depth
   - Size: 220dp x 220dp

3. **Input Fields**
   - Semi-transparent white background (30% opacity)
   - Rounded corners (12dp radius)
   - White text with hint color at 50% opacity
   - Height: 60dp for easy touch interaction

4. **Login Button**
   - Bright neon cyan (#00E5B4) background
   - Dark blue text for contrast
   - Fully rounded (30dp corner radius)
   - Bold, uppercase text with letter spacing
   - Elevated (8dp) for depth

5. **Links**
   - "Forgot Password?" in semi-transparent white
   - "Register" link in neon cyan to match brand color
   - Positioned at the bottom for easy access

### 🎭 Animations

The login screen includes several smooth animations:

1. **Logo Animation**
   - Fades in from 0 to 1 opacity
   - Scales from 0.5x to 1x
   - Duration: 600ms

2. **Input Fields**
   - Slide up from 50px below
   - Fade in simultaneously
   - Staggered timing (200ms, 300ms)

3. **Login Button**
   - Scale animation from 0.9x to 1x
   - Fade in effect
   - Delay: 400ms

4. **Button Press**
   - Scales to 0.95x when pressed
   - Bounces back to normal size
   - Provides haptic-like feedback

5. **Error Shake**
   - Shakes horizontally on invalid credentials
   - Quick 3-step animation (-10px, +10px, 0px)

### 🎨 Color Palette

```xml
<!-- Primary Login Colors -->
<color name="neon_cyan">#00E5B4</color>        <!-- Buttons, links, accents -->
<color name="dark_blue">#0B4F6C</color>         <!-- Background start -->
<color name="dark_blue_dark">#01394C</color>    <!-- Background end -->
<color name="white">#FFFFFFFF</color>           <!-- Text and inputs -->

<!-- Transparencies -->
- Input backgrounds: 30% white (#30FFFFFF)
- Input borders: 25% white (#40FFFFFF)
- Hint text: 50% white (#80FFFFFF)
- Links: 50% white (#80FFFFFF)
```

## Files Created/Modified

### New Files
1. `login_background.xml` - Dark blue gradient background
2. `login_input_bg.xml` - Semi-transparent input field background
3. `login_button_bg.xml` - Neon cyan button background
4. `ic_outfit_logo.xml` - Custom outfit icon (hanger, shirt, pants)

### Modified Files
1. `activity_login.xml` - Complete redesign of login layout
2. `LoginActivity.kt` - Updated to work with new layout and animations
3. `colors.xml` - Added neon cyan and dark blue colors

## Customization

### Change Colors
Edit the following files:
- `res/drawable/login_background.xml` - Background gradient
- `res/drawable/login_button_bg.xml` - Button color
- `res/drawable/ic_outfit_logo.xml` - Logo stroke color

### Adjust Logo
The logo is a vector drawable, so you can:
- Change colors by editing `android:strokeColor`
- Modify size by changing width/height in layout
- Replace with custom logo by changing `android:src`

### Modify Animations
Animation timings can be adjusted in `LoginActivity.kt`:
- Look for `.setDuration()` and `.setStartDelay()` calls
- Default durations: 500-600ms
- Default delays: 0-400ms

### Change Button Style
Edit `login_button_bg.xml`:
- Adjust `android:radius` for more/less rounding
- Change `android:color` for different button color

## User Experience

✅ **Smooth Animations** - All elements animate in gracefully
✅ **Visual Feedback** - Button press and error animations
✅ **Easy Input** - Large touch targets (60dp height)
✅ **Clear Hierarchy** - Logo → Inputs → Action button
✅ **Accessible** - High contrast text and clear labels
✅ **Modern Design** - Neon accents and dark theme

## Testing

**Test Credentials:**
- Username: `admin`
- Password: `admin123`

Or create a new account via the Register link.

## Integration

The login screen automatically:
1. Validates input fields
2. Checks credentials (admin or registered users)
3. Saves login session
4. Navigates to HomeActivity on success
5. Shows error message with shake animation on failure


