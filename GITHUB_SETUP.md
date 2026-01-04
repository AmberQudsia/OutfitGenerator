# 📦 GitHub Setup Guide

This guide will help you upload your Outfit Matcher project to GitHub.

## 🚀 Quick Start

### Step 1: Create a GitHub Repository

1. Go to [GitHub](https://github.com) and sign in
2. Click the "+" icon in the top right corner
3. Select "New repository"
4. Fill in the details:
   - **Repository name:** `outfit-matcher`
   - **Description:** "AI-powered fashion stylist app for Android"
   - **Visibility:** Public or Private (your choice)
   - **DO NOT** initialize with README, .gitignore, or license (we already have these)
5. Click "Create repository"

### Step 2: Initialize Git in Your Project

Open Terminal and navigate to your project directory:

```bash
cd /Users/macbook/AndroidStudioProjects/OutfitGenerator
```

Initialize git repository:

```bash
# Initialize git
git init

# Add all files
git add .

# Commit the files
git commit -m "Initial commit: Outfit Matcher app with authentication flow"
```

### Step 3: Connect to GitHub and Push

Replace `YOUR_USERNAME` with your actual GitHub username:

```bash
# Add remote repository
git remote add origin https://github.com/YOUR_USERNAME/outfit-matcher.git

# Push to GitHub
git branch -M main
git push -u origin main
```

### Step 4: Verify Upload

1. Go to your GitHub repository page
2. Refresh the page
3. You should see all your project files uploaded

## 📝 Common Git Commands

### After Making Changes

```bash
# Check status of changes
git status

# Add all changes
git add .

# Commit changes with a message
git commit -m "Description of changes"

# Push to GitHub
git push
```

### Branching (Optional)

```bash
# Create a new branch
git checkout -b feature-name

# Switch between branches
git checkout main
git checkout feature-name

# Merge branch into main
git checkout main
git merge feature-name
```

## 🔐 Authentication

### Option 1: Personal Access Token (Recommended)

1. Go to GitHub Settings → Developer settings → Personal access tokens → Tokens (classic)
2. Generate new token with `repo` scope
3. Use the token as your password when pushing

### Option 2: SSH Key

1. Generate SSH key: `ssh-keygen -t ed25519 -C "your_email@example.com"`
2. Add to GitHub: Settings → SSH and GPG keys → New SSH key
3. Use SSH URL instead: `git@github.com:YOUR_USERNAME/outfit-matcher.git`

## 📋 What's Already Set Up

✅ `.gitignore` - Excludes build files, IDE files, and system files
✅ `README.md` - Complete project documentation
✅ `PROJECT_DOCUMENTATION.md` - Detailed features and requirements
✅ All source code organized and documented

## ⚠️ Before Pushing

Make sure you:
- [x] Remove any sensitive data (API keys, passwords)
- [x] Check that local.properties is in .gitignore
- [x] Verify .gitignore is working
- [x] Test build works: `./gradlew build`

## 🎯 Project URL Format

After uploading, your project will be available at:
```
https://github.com/YOUR_USERNAME/outfit-matcher
```

Share this URL with your instructor for submission!

## 🆘 Troubleshooting

### Problem: Permission denied
**Solution:** Check your authentication (use personal access token or SSH key)

### Problem: Remote already exists
**Solution:** Remove and re-add
```bash
git remote remove origin
git remote add origin https://github.com/YOUR_USERNAME/outfit-matcher.git
```

### Problem: Merge conflicts
**Solution:** Pull first, then push
```bash
git pull origin main --allow-unrelated-histories
git push origin main
```

## 📱 Adding Collaborators (Optional)

1. Go to repository Settings → Collaborators
2. Click "Add people"
3. Enter GitHub username or email
4. They can now clone and contribute

## ✅ Submission Checklist

Before submitting to your instructor:

- [ ] All code is pushed to GitHub
- [ ] README.md is visible on repository homepage
- [ ] Project builds without errors
- [ ] Repository is public (if required)
- [ ] Repository URL is submitted to instructor
- [ ] All documentation files are included

## 🎓 For Academic Submission

Include this information in your submission:

```
Project Name: Outfit Matcher (AI Fashion Stylist)
GitHub URL: https://github.com/YOUR_USERNAME/outfit-matcher
Student Name: [Your Name]
Course: Mobile Application Development
Submission Type: Individual Project - Before Midterm
```

---

**Good luck with your submission! 🎓**


