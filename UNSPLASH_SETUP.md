# Unsplash API Setup (FREE!)

## ✅ Good News: It's Completely Free!

Unsplash API is **100% free** for outfit images. No payment required!

## Quick Setup (5 minutes)

### Step 1: Get Your Free API Key

1. **Go to Unsplash Developers:**
   - Visit: https://unsplash.com/developers
   - Click "Register as a developer" (if you don't have an account)
   - Or sign in if you already have an account

2. **Create a New Application:**
   - Click "New Application"
   - Fill in:
     - **Application name:** "Outfit Generator" (or any name)
     - **Description:** "Personal outfit suggestion app"
   - Accept the terms
   - Click "Create Application"

3. **Copy Your Access Key:**
   - You'll see your **Access Key** (looks like: `abc123xyz456...`)
   - Copy it!

### Step 2: Add Key to Your App

1. **Open the file:**
   - `app/src/main/java/com/student/outfitgenerator/api/ApiClient.kt`

2. **Find this line:**
   ```kotlin
   private const val UNSPLASH_ACCESS_KEY = "YOUR_UNSPLASH_ACCESS_KEY_HERE"
   ```

3. **Replace with your key:**
   ```kotlin
   private const val UNSPLASH_ACCESS_KEY = "abc123xyz456..." // Your actual key
   ```

4. **Save and rebuild!**

## That's It! 🎉

Your app will now use Unsplash to find beautiful outfit images based on:
- Occasion (wedding, casual, business, etc.)
- Climate (winter, summer, rainy)
- Preferred colors

## Free Tier Limits

- ✅ **50 requests per hour** (plenty for testing!)
- ✅ **No credit card required**
- ✅ **No payment ever needed**

## How It Works

1. User enters: Occasion, climate, colors
2. App creates search query: "wedding outfit formal blue"
3. Unsplash searches: Finds matching fashion photos
4. App displays: Beautiful outfit image!

## Troubleshooting

### "Image not found"
- Check if you added your Unsplash Access Key
- Check internet connection
- Try a different search query (occasion/climate)

### "Check Unsplash key setup"
- Make sure you copied the **Access Key** (not Secret Key)
- Make sure there are no extra spaces
- Rebuild the app after adding the key

### Rate Limit (unlikely)
- Free tier: 50 requests/hour
- If you hit the limit, wait 1 hour and try again
- For production, consider upgrading (still free with limits)

## Example Search Queries

The app automatically creates queries like:
- "wedding outfit formal dress winter blue"
- "casual outfit fashion summer"
- "business professional outfit"
- "dinner outfit elegant fashion"

## Need Help?

- Unsplash Docs: https://unsplash.com/documentation
- Check Logcat for "Unsplash API" messages
- Make sure your key starts with a letter/number (not "YOUR_")

## Security Note

For production apps, store the API key securely:
- Use `BuildConfig` or `local.properties`
- Don't commit keys to Git
- For now, keeping it in code is fine for learning!

