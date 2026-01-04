# 👔 Outfit Matcher - AI Fashion Stylist

An Android application that helps users create perfect outfit combinations using AI-powered color and style matching. Upload your clothes, build your digital wardrobe, and get intelligent outfit suggestions!

## 📱 Project Overview

**Outfit Matcher** is a fashion technology app that uses artificial intelligence to analyze your clothing items and suggest matching outfits based on color harmony and style compatibility. Perfect for anyone who wants to make better fashion choices and maximize their wardrobe potential.

## ✨ Features

### Authentication System
- ✅ **User Login** - Secure login with validation
- ✅ **User Registration** - Create new account with email validation
- ✅ **Forgot Password** - Password recovery flow
- ✅ **Reset Password** - Update password securely

### Core Outfit Matcher Features (Ready for Implementation)
- 📸 **Upload Clothes** - Add clothing items from camera or gallery
- 👗 **My Wardrobe** - Manage your digital wardrobe
- ✨ **AI Outfit Suggestions** - Get intelligent outfit recommendations
- 🎨 **Color Matching** - AI-powered color harmony analysis
- 👔 **Style Compatibility** - Smart style matching algorithm

## 🛠️ Technologies & Libraries

### Core Android Components
- **Language:** Kotlin
- **Min SDK:** 24 (Android 7.0)
- **Target SDK:** 36
- **UI Framework:** XML with ConstraintLayout
- **Architecture:** Activity-based navigation

### Libraries Used
```gradle
// AndroidX Core
androidx.core:core-ktx
androidx.appcompat:appcompat
androidx.constraintlayout:constraintlayout
androidx.activity:activity

// Material Design
com.google.android.material:material

// View Binding (enabled)
buildFeatures {
    viewBinding true
}
```

### Future Libraries (For Full Implementation)
- **Image Processing:** Glide or Picasso
- **ML/AI:** TensorFlow Lite
- **Database:** Room Database
- **Image Picker:** Activity Result APIs
- **Color Analysis:** Custom Color API or third-party

## 📐 Architecture & Design

### Design Pattern
- **UI Layout:** ConstraintLayout for responsive design
- **Navigation:** Intent-based activity transitions
- **Validation:** Client-side form validation
- **Data Binding:** ViewBinding for type-safe view access

### Navigation Flow
```
LoginActivity (Launcher)
├── → RegistrationActivity → Back to Login
├── → ForgotPasswordActivity → ResetPasswordActivity → Login
└── → HomeActivity (After successful login)
    ├── Upload Clothes Feature
    ├── My Wardrobe Feature
    └── AI Outfit Suggestions
```

## 🔐 Test Credentials

### Option 1: Register Your Own User
1. Click "Register" on login screen
2. Create your account with any username/password
3. Login with your registered credentials
4. Your name will appear in the welcome message!

### Option 2: Use Admin Account
For quick testing, use these hardcoded credentials:

- **Username:** `admin`
- **Password:** `admin123`

**Note:** The app supports BOTH registered users AND the admin account!

## 🚀 Getting Started

### Prerequisites
- Android Studio Arctic Fox or later
- JDK 11 or later
- Android SDK 24+
- Gradle 7.0+

### Installation Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/outfit-matcher.git
   cd outfit-matcher
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an Existing Project"
   - Navigate to the project directory
   - Wait for Gradle sync to complete

3. **Build the project**
   ```bash
   ./gradlew build
   ```

4. **Run on device/emulator**
   - Connect your Android device or start an emulator
   - Click the "Run" button in Android Studio
   - Or use: `./gradlew installDebug`

## 📱 App Screens

### 1. Login Screen
- Username and password input
- Form validation
- Links to registration and forgot password
- Hardcoded credential authentication

### 2. Registration Screen
- Full name, email, username, password fields
- Email format validation
- Password strength validation (min 6 characters)
- Password confirmation matching
- Auto-redirect to login after successful registration

### 3. Forgot Password Screen
- Email input for password recovery
- Email validation
- Simulated reset link sending
- Navigation to reset password screen

### 4. Reset Password Screen
- New password input
- Password confirmation
- Validation and security checks
- Redirect to login after successful reset

### 5. Home Screen (Landing Page)
- Welcome message
- Three main feature cards:
  - **Upload Clothes** - Add new items to wardrobe
  - **My Wardrobe** - View and manage clothing items
  - **Get Outfit Suggestions** - AI-powered matching
- Key features list
- Logout functionality

## 🎨 UI Design

### Color Scheme
```xml
Primary: #FF6B9E (Pink)
Primary Dark: #E91E63 (Deep Pink)
Accent: #FFC107 (Amber)
Background: #F5F5F5 (Light Gray)
Text Primary: #212121 (Dark Gray)
Text Secondary: #757575 (Medium Gray)
Error: #F44336 (Red)
Success: #4CAF50 (Green)
```

### Design Principles
- **Simple & Clean** - Easy to navigate interface
- **Material Design 3** - Modern Android design guidelines
- **Responsive Layout** - ConstraintLayout for all screens
- **User Feedback** - Toast messages for all actions
- **Validation** - Real-time input validation with error messages

## 📋 Validation Controls

### Input Validation
- ✅ Empty field checks
- ✅ Email format validation (using Patterns.EMAIL_ADDRESS)
- ✅ Password length validation (minimum 6 characters)
- ✅ Password confirmation matching
- ✅ Real-time error display with TextInputLayout

### Navigation Controls
- ✅ Proper intent flags to prevent back stack issues
- ✅ Activity lifecycle management
- ✅ Back button disabled on home screen (must use logout)

## 🔄 Future Enhancements

### Phase 1 (Next Steps)
- [ ] Implement camera and gallery integration
- [ ] Add image storage (local or cloud)
- [ ] Create wardrobe database with Room
- [ ] Build clothing item detail screens

### Phase 2 (AI Integration)
- [ ] Integrate TensorFlow Lite for color analysis
- [ ] Implement style matching algorithm
- [ ] Create outfit suggestion engine
- [ ] Add outfit rating system

### Phase 3 (Advanced Features)
- [ ] Firebase authentication
- [ ] Cloud storage for images
- [ ] Social sharing features
- [ ] Outfit history and favorites
- [ ] Weather-based suggestions

## 📝 Project Structure

```
app/src/main/
├── java/com/student/outfitgenerator/
│   ├── LoginActivity.kt
│   ├── RegistrationActivity.kt
│   ├── ForgotPasswordActivity.kt
│   ├── ResetPasswordActivity.kt
│   ├── HomeActivity.kt
│   └── MainActivity.kt
├── res/
│   ├── layout/
│   │   ├── activity_login.xml
│   │   ├── activity_registration.xml
│   │   ├── activity_forgot_password.xml
│   │   ├── activity_reset_password.xml
│   │   ├── activity_home.xml
│   │   └── activity_main.xml
│   ├── values/
│   │   ├── colors.xml
│   │   ├── strings.xml
│   │   └── themes.xml
│   └── drawable/
└── AndroidManifest.xml
```

## 🐛 Known Issues
- None at this stage (basic authentication implementation)

## 📄 License
This project is created for educational purposes as part of a mobile development course.

## 👨‍💻 Development

### Before Midterm Requirements ✅
- [x] Specify list of features
- [x] List packages/libraries/APIs
- [x] Design UI using ConstraintLayout
- [x] Implement app validation controls
- [x] Set up navigation between screens
- [x] Upload project to GitHub

### Coding Standards
- Simple, clean code (no complex implementations)
- ConstraintLayout for all screens
- ViewBinding for view access
- Proper error handling and user feedback
- Commented code for clarity

## 📞 Support
For questions or issues, please create an issue in the GitHub repository.

## 🎓 Academic Project
This is an academic project for mobile application development course. Individual submission before midterm exams.

---

**Happy Styling! 👔✨**

