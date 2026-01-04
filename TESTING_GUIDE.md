# 🧪 Testing Guide - Outfit Matcher App

## Overview
This guide will help you test all features of the Outfit Matcher application, including the new **user registration and login** functionality.

---

## ✅ Feature 1: User Registration & Login with Custom Credentials

### Test Case 1: Register a New User

**Steps:**
1. Launch the app (opens on Login screen)
2. Click **"Register"** link at the bottom
3. Fill in the registration form:
   - **Full Name:** `John Doe`
   - **Email:** `john@example.com`
   - **Username:** `johndoe`
   - **Password:** `john123`
   - **Confirm Password:** `john123`
4. Click **"Register"** button

**Expected Result:**
- ✅ "Registration successful!" toast message appears
- ✅ Automatically redirects to Login screen
- ✅ User credentials are saved locally

---

### Test Case 2: Login with Registered Credentials

**Steps:**
1. On Login screen, enter:
   - **Username:** `johndoe`
   - **Password:** `john123`
2. Click **"Login"** button

**Expected Result:**
- ✅ "Login successful!" toast message appears
- ✅ Navigates to Home screen
- ✅ Welcome message shows: "Welcome back, John Doe! Ready to style your outfit?"

---

### Test Case 3: Login with Admin Credentials (Still Works!)

**Steps:**
1. On Login screen, enter:
   - **Username:** `admin`
   - **Password:** `admin123`
2. Click **"Login"** button

**Expected Result:**
- ✅ "Login successful!" toast message appears
- ✅ Navigates to Home screen
- ✅ Shows default welcome message (no personalization for admin)

---

## ✅ Feature 2: Registration Validation

### Test Case 4: Empty Field Validation

**Steps:**
1. Go to Registration screen
2. Leave one or more fields empty
3. Click **"Register"** button

**Expected Result:**
- ✅ Error message appears under empty fields
- ✅ "Please enter [field name]" error displayed
- ✅ Registration does not proceed

---

### Test Case 5: Invalid Email Format

**Steps:**
1. Go to Registration screen
2. Enter invalid email: `notanemail`
3. Fill other fields correctly
4. Click **"Register"** button

**Expected Result:**
- ✅ Error message: "Please enter a valid email"
- ✅ Registration does not proceed

---

### Test Case 6: Password Too Short

**Steps:**
1. Go to Registration screen
2. Enter password: `12345` (less than 6 characters)
3. Fill other fields correctly
4. Click **"Register"** button

**Expected Result:**
- ✅ Error message: "Password must be at least 6 characters"
- ✅ Registration does not proceed

---

### Test Case 7: Password Mismatch

**Steps:**
1. Go to Registration screen
2. Enter password: `john123`
3. Enter confirm password: `john456`
4. Fill other fields correctly
5. Click **"Register"** button

**Expected Result:**
- ✅ Error message: "Passwords do not match"
- ✅ Registration does not proceed

---

## ✅ Feature 3: Login Validation

### Test Case 8: Invalid Login Credentials

**Steps:**
1. On Login screen, enter:
   - **Username:** `wronguser`
   - **Password:** `wrongpass`
2. Click **"Login"** button

**Expected Result:**
- ✅ Error toast: "Invalid username or password"
- ✅ Stays on Login screen

---

### Test Case 9: Empty Login Fields

**Steps:**
1. On Login screen, leave username or password empty
2. Click **"Login"** button

**Expected Result:**
- ✅ Error message under empty field(s)
- ✅ "Please enter username" or "Please enter password"
- ✅ Login does not proceed

---

## ✅ Feature 4: Forgot Password Flow

### Test Case 10: Forgot Password

**Steps:**
1. On Login screen, click **"Forgot Password?"**
2. Enter email: `john@example.com`
3. Click **"Send Reset Link"** button

**Expected Result:**
- ✅ Toast: "Reset link sent to your email!"
- ✅ Navigates to Reset Password screen

---

### Test Case 11: Reset Password

**Steps:**
1. On Reset Password screen, enter:
   - **New Password:** `newpass123`
   - **Confirm New Password:** `newpass123`
2. Click **"Reset Password"** button

**Expected Result:**
- ✅ Toast: "Password reset successful!"
- ✅ Navigates back to Login screen

---

## ✅ Feature 5: Home Screen Features

### Test Case 12: Personalized Welcome Message

**Steps:**
1. Register with name: `Jane Smith`
2. Login with those credentials
3. Observe Home screen

**Expected Result:**
- ✅ Welcome message shows: "Welcome back, Jane Smith! Ready to style your outfit?"

---

### Test Case 13: Upload Clothes Card

**Steps:**
1. On Home screen, click **"Upload Clothes"** card

**Expected Result:**
- ✅ Toast: "Upload Clothes feature - Coming soon!"

---

### Test Case 14: My Wardrobe Card

**Steps:**
1. On Home screen, click **"My Wardrobe"** card

**Expected Result:**
- ✅ Toast: "My Wardrobe feature - Coming soon!"

---

### Test Case 15: Get Suggestions Card

**Steps:**
1. On Home screen, click **"Get Outfit Suggestions"** card

**Expected Result:**
- ✅ Toast: "AI Outfit Suggestions - Coming soon!"

---

### Test Case 16: Logout Functionality

**Steps:**
1. On Home screen, click **"Logout"** button

**Expected Result:**
- ✅ Session cleared
- ✅ Navigates back to Login screen
- ✅ Cannot go back to Home using back button

---

### Test Case 17: Back Button on Home Screen

**Steps:**
1. On Home screen, press device back button

**Expected Result:**
- ✅ Toast: "Use logout button to exit"
- ✅ Stays on Home screen (prevents accidental logout)

---

## ✅ Feature 6: Navigation Flow

### Test Case 18: Back to Login from Registration

**Steps:**
1. On Login screen, click **"Register"**
2. On Registration screen, click **"Login"** link at bottom

**Expected Result:**
- ✅ Returns to Login screen

---

### Test Case 19: Back to Login from Forgot Password

**Steps:**
1. On Login screen, click **"Forgot Password?"**
2. On Forgot Password screen, click **"Back to Login"**

**Expected Result:**
- ✅ Returns to Login screen

---

## 🔄 Complete User Journey Test

### Full Registration to Logout Flow

**Steps:**
1. Launch app
2. Click **"Register"**
3. Register with:
   - Full Name: `Sarah Connor`
   - Email: `sarah@example.com`
   - Username: `sarah`
   - Password: `sarah123`
4. After redirect, login with `sarah` / `sarah123`
5. Verify Home screen shows: "Welcome back, Sarah Connor!"
6. Click each feature card (Upload, Wardrobe, Suggestions)
7. Click **"Logout"**
8. Try logging in again with same credentials

**Expected Result:**
- ✅ All steps work smoothly
- ✅ No crashes or errors
- ✅ User can login again after logout

---

## 📱 Storage Verification

### How User Data is Stored

The app uses **SharedPreferences** for simple local storage:

**Stored Data:**
- Full Name
- Email
- Username
- Password (Note: In production, passwords should be encrypted!)
- Login session status

**Storage Location:**
- `UserPrefs` SharedPreferences file
- Persists across app restarts
- Cleared on logout (session only)

---

## 🐛 Known Limitations (Current Version)

1. **Single User:** Only one user can be registered at a time (last registration overwrites previous)
2. **Password Storage:** Passwords stored in plain text (for educational purposes only)
3. **No Backend:** All data stored locally on device
4. **No Password Recovery:** Forgot Password is simulated, no actual email sent

---

## 💡 Tips for Testing

1. **Clear App Data** between tests to reset credentials:
   - Settings → Apps → Outfit Matcher → Storage → Clear Data

2. **Test on Different Devices/Emulators** to ensure compatibility

3. **Test Rotation** - Ensure data persists when rotating device

4. **Test with Different Input Lengths** - Try very long names, emails, etc.

5. **Test Special Characters** - Try passwords with symbols, numbers

---

## ✅ Quick Test Checklist

Use this checklist for rapid testing:

- [ ] Register new user with valid data
- [ ] Login with registered credentials
- [ ] Login with admin credentials (admin/admin123)
- [ ] Test all validation errors
- [ ] Test Forgot Password flow
- [ ] Test Reset Password flow
- [ ] Verify personalized welcome message
- [ ] Test all Home screen cards
- [ ] Test logout functionality
- [ ] Test navigation between all screens
- [ ] Verify back button behavior
- [ ] Test app after closing and reopening

---

## 🎯 Success Criteria

**All tests pass if:**
- ✅ User can register with valid credentials
- ✅ User can login with registered credentials
- ✅ User can login with admin credentials
- ✅ All validations work correctly
- ✅ Navigation works smoothly
- ✅ Welcome message shows user's name
- ✅ Logout clears session properly
- ✅ No crashes or unexpected behavior

---

**Happy Testing! 🧪✨**

For issues or bugs, document them and check the code in:
- `LoginActivity.kt`
- `RegistrationActivity.kt`
- `HomeActivity.kt`





