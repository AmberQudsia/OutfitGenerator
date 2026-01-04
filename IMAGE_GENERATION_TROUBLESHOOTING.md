# Image Generation Troubleshooting Guide

## Current Status
- ✅ Text generation is working (GPT-4 API)
- ⚠️ Image generation needs to be working (DALL-E API)

## How Image Generation Works

1. User enters data → Occasion, climate, colors, etc.
2. App calls DALL-E API → Generates outfit image
3. Image displays → Shows AI-generated outfit visualization

## Common Issues & Solutions

### Issue 1: API Key Problems
**Symptoms:** "Image generation unavailable" message

**Solution:**
- Verify your API key in `ApiClient.kt` is correct
- Make sure API key has access to DALL-E models
- Check OpenAI dashboard for API usage limits

### Issue 2: API Rate Limits
**Symptoms:** Intermittent failures

**Solution:**
- Check your OpenAI account usage limits
- DALL-E 3 has rate limits (requests per minute)
- Upgrade plan if needed

### Issue 3: Prompt Too Long
**Symptoms:** API returns error about prompt length

**Solution:**
- DALL-E 3 has a 4000 character prompt limit
- Current prompts are within limit, but if you add more, reduce length

### Issue 4: Model Availability
**Symptoms:** "Model not found" error

**Solution:**
- Ensure `dall-e-3` model is available in your API plan
- Some plans may only have `dall-e-2` (edit model in `ImageGenerator.kt`)

### Issue 5: Network Issues
**Symptoms:** Timeout or connection errors

**Solution:**
- Check internet connection
- API timeout is set to 30 seconds
- Try again if network is slow

## Debugging Steps

1. **Check Logcat** - Look for "DALL-E" messages:
   - "DALL-E Image Generated Successfully" = Working
   - "DALL-E API Error" = Check error message
   - "DALL-E API Failed" = Check HTTP status code

2. **Test API Key** - Try calling DALL-E API directly:
   ```bash
   curl https://api.openai.com/v1/images/generations \
     -H "Authorization: Bearer YOUR_API_KEY" \
     -H "Content-Type: application/json" \
     -d '{
       "model": "dall-e-3",
       "prompt": "A professional outfit",
       "n": 1,
       "size": "1024x1024"
     }'
   ```

3. **Check Response** - The app now shows:
   - Success toast: "✨ Outfit image generated!"
   - Error toast: "Image generation failed. Check API key and try again."
   - Error icon displayed if generation fails

## Current Implementation

- **Model**: `dall-e-3`
- **Size**: `1024x1024`
- **Quality**: `standard`
- **Style**: `natural`
- **Timeout**: 30 seconds

## Expected Behavior

1. User submits form
2. Loading spinner appears
3. API call made to DALL-E
4. If successful: Image loads and displays
5. If failed: Error icon shown with message

## Next Steps

If image generation still doesn't work:
1. Check Logcat for specific error messages
2. Verify API key has DALL-E access
3. Test with simpler prompt first
4. Check OpenAI account billing/limits

