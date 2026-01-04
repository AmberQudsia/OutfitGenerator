# Pexels API Setup (FREE & INSTANT!)

## ✅ Great News: Instant Access, No Approval Needed!

Pexels API is **100% free** and you get your API key **instantly** - no waiting for approval!

## Why Pexels Instead of Unsplash?

- ✅ **Instant access** - Get API key immediately (no review/approval)
- ✅ **200 requests/hour** (better than Unsplash's 50!)
- ✅ **Same high-quality images**
- ✅ **No credit card required**

## Quick Setup (2 minutes)

### Step 1: Get Your Free API Key (Instant!)

1. **Go to Pexels API:**
   - Visit: https://www.pexels.com/api/
   - Click "Get Started" or "Sign Up"

2. **Create Account:**
   - Sign up with email (or use Google/GitHub)
   - Verify your email if needed

3. **Get Your API Key:**
   - Once logged in, you'll see your **API Key** immediately
   - It looks like: `abc123xyz456...` (long string)
   - **Copy it right away!**

### Step 2: Add Key to Your App

1. **Open the file:**
   - `app/src/main/java/com/student/outfitgenerator/api/ApiClient.kt`

2. **Find this line:**
   ```kotlin
   private const val PEXELS_API_KEY = "YOUR_PEXELS_API_KEY_HERE"
   ```

3. **Replace with your key:**
   ```kotlin
   private const val PEXELS_API_KEY = "abc123xyz456..." // Your actual key
   ```

4. **Save and rebuild!**

## That's It! 🎉

Your app will now use Pexels to find beautiful outfit images based on:
- Occasion (wedding, casual, business, etc.)
- Climate (winter, summer, rainy)
- Preferred colors

## Free Tier Details

- ✅ **200 requests per hour** (plenty for testing!)
- ✅ **No credit card required**
- ✅ **No payment ever needed**
- ✅ **Instant activation** (no waiting!)

## How It Works

1. User enters: Occasion, climate, colors
2. App creates search query: "wedding outfit formal blue"
3. Pexels searches: Finds matching fashion photos
4. App displays: Beautiful outfit image!

## Example Search Queries

The app automatically creates queries like:
- "wedding outfit formal dress winter blue"
- "casual outfit fashion summer"
- "business professional outfit"
- "dinner outfit elegant fashion"

## Troubleshooting

### "Image not found"
- Check if you added your Pexels API Key
- Check internet connection
- Try a different search query (occasion/climate)

### "Check Pexels key setup"
- Make sure you copied the **full API Key**
- Make sure there are no extra spaces
- Rebuild the app after adding the key

### Rate Limit (unlikely)
- Free tier: 200 requests/hour
- If you hit the limit, wait 1 hour and try again
- Much better than Unsplash's 50/hour limit!

## Need Help?

- Pexels API Docs: https://www.pexels.com/api/documentation/
- Check Logcat for "Pexels API" messages
- Make sure your key starts with a letter/number (not "YOUR_")

## Security Note

For production apps, store the API key securely:
- Use `BuildConfig` or `local.properties`
- Don't commit keys to Git
- For now, keeping it in code is fine for learning!

## Comparison: Pexels vs Unsplash

| Feature | Pexels | Unsplash |
|---------|--------|----------|
| Approval Time | Instant | Under review |
| Free Requests/Hour | 200 | 50 |
| Credit Card | Not needed | Not needed |
| Image Quality | High | High |
| Setup Time | 2 minutes | Wait for approval |

**Winner: Pexels!** (Especially when Unsplash is under review)

