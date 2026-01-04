# Outfit Matcher (AI Fashion Stylist)

## Project Overview
An Android application that helps users match their outfits by uploading clothes and getting AI-powered style suggestions based on color and style analysis.

## Features List

### Authentication Features
1. **User Login**
   - Hardcoded credential validation
   - Username and password authentication
   - Navigation to home screen on success

2. **User Registration**
   - New user account creation
   - Input validation (email, password strength)
   - Automatic login after registration

3. **Forgot Password**
   - Password recovery flow
   - Email verification
   - Redirect to reset password

4. **Reset Password**
   - Update user password
   - Password confirmation
   - Security validation

### Core Outfit Matcher Features
5. **Home/Landing Screen**
   - Upload clothes images
   - View wardrobe collection
   - Get outfit suggestions
   - Color and style matching

6. **Wardrobe Management**
   - Add clothing items
   - Categorize by type (tops, bottoms, shoes, accessories)
   - Upload from gallery or camera

7. **Outfit Suggestions**
   - AI-powered color matching
   - Style compatibility analysis
   - Mix and match recommendations

## Libraries, Packages & APIs

### Core Android Libraries
- `androidx.appcompat` - AppCompat support
- `androidx.core:core-ktx` - Kotlin extensions
- `androidx.constraintlayout` - UI layout management
- `com.google.android.material` - Material Design components
- `androidx.activity` - Activity management

### Image Processing (Future Implementation)
- `com.github.bumptech.glide:glide` - Image loading and caching
- `androidx.activity:activity-ktx` - Activity result APIs
- TensorFlow Lite - On-device ML for color/style analysis

### Storage & Data
- SharedPreferences - Local user data storage
- Room Database - Wardrobe item persistence (optional)

### APIs (Future Integration)
- Color API - Color palette extraction and matching
- Fashion/Style API - Style recommendations
- Cloud storage - Image backup

## UI Design Approach
- **Layout**: ConstraintLayout for all screens
- **Design Pattern**: Material Design 3
- **Color Scheme**: Fashion-focused with modern aesthetics
- **Navigation**: Intent-based navigation between activities

## Validation Controls
1. Email format validation
2. Password strength requirements (minimum 6 characters)
3. Empty field validation
4. Password confirmation matching
5. Toast messages for user feedback

## Navigation Flow
```
LoginActivity (Main Entry)
├── → RegistrationActivity → LoginActivity
├── → ForgotPasswordActivity → ResetPasswordActivity → LoginActivity
└── → HomeActivity (After successful login)
    └── Outfit Matcher Features
```

## Hardcoded Credentials (For Testing)
- Username: `admin`
- Password: `admin123`

## GitHub Repository
Project will be uploaded to GitHub before midterm exams with complete source code and documentation.


