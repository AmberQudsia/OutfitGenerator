# How to Check Logs for Image Generation

## Method 1: Android Studio Logcat (Recommended)

### Step 1: Open Logcat
1. Open Android Studio
2. At the bottom of the screen, click on the **"Logcat"** tab
3. If you don't see it, go to: **View → Tool Windows → Logcat**

### Step 2: Filter Logs
1. In the Logcat search box, type: **DALL-E**
2. This will show only DALL-E related logs
3. You can also filter by: **OutfitGenerator** (your app name)

### Step 3: Run the App
1. Run your app on device/emulator
2. Navigate to AI Outfit Suggestions
3. Fill in the form and submit
4. Watch the Logcat for messages

### What to Look For:

**Success Messages:**
```
DALL-E Image Generated Successfully: https://...
```

**Error Messages:**
```
DALL-E API Error: [error message]
DALL-E API Failed: [HTTP code] - [message]
DALL-E API Exception: [exception details]
```

## Method 2: Command Line (adb logcat)

### Step 1: Connect Device
```bash
# List connected devices
adb devices
```

### Step 2: Filter Logs
```bash
# Filter for DALL-E logs
adb logcat | grep -i "DALL-E"

# Filter for your app package
adb logcat | grep "com.student.outfitgenerator"

# Show all logs with timestamps
adb logcat -v time | grep -i "DALL-E"
```

### Step 3: Save Logs to File
```bash
# Save all logs
adb logcat > logcat_output.txt

# Save filtered logs
adb logcat | grep -i "DALL-E" > dalle_logs.txt
```

## Method 3: Android Studio Filter Preset

### Create a Custom Filter:
1. In Logcat, click the filter dropdown (top right)
2. Select **"Edit Filter Configuration"**
3. Click **"+"** to add new filter
4. Name it: **"DALL-E Debug"**
5. Set:
   - **Tag:** `DALL-E` or leave blank
   - **Package Name:** `com.student.outfitgenerator`
6. Click **OK**
7. Select this filter from dropdown

## What Logs to Look For

### 1. Image Generation Start
Look for when the API call begins:
```
DALL-E API: Generating image...
```

### 2. API Response
**Success:**
```
DALL-E Image Generated Successfully: https://oaidalleapiprodscus.blob.core.windows.net/...
```

**Failure:**
```
DALL-E API Failed: 401 - Unauthorized
DALL-E API Error: Invalid API key
```

### 3. Common Error Types

**401 Unauthorized:**
- API key is wrong or expired
- Solution: Check API key in `ApiClient.kt`

**429 Too Many Requests:**
- Rate limit exceeded
- Solution: Wait a bit or upgrade plan

**400 Bad Request:**
- Invalid prompt or parameters
- Solution: Check prompt length/format

**500 Internal Server Error:**
- OpenAI server issue
- Solution: Try again later

**Network Errors:**
- No internet connection
- Timeout issues
- Solution: Check internet connection

## Example Log Output

### Successful Image Generation:
```
I/System.out: DALL-E API: Generating outfit image...
I/System.out: DALL-E Image Generated Successfully: https://oaidalleapiprodscus.blob.core.windows.net/private/...
D/Glide: Loaded image from URL
```

### Failed Image Generation:
```
E/System.err: DALL-E API Failed: 401 - Unauthorized
E/System.err: Error Body: {"error":{"message":"Invalid API key","type":"invalid_request_error"}}
E/System.err: DALL-E API Error: Invalid API key (Type: invalid_request_error, Code: null)
```

## Quick Debug Commands

### Check if API calls are being made:
```bash
adb logcat | grep -E "(DALL-E|Retrofit|OkHttp)"
```

### Check for network errors:
```bash
adb logcat | grep -E "(Network|IOException|SocketTimeout)"
```

### Check for all errors:
```bash
adb logcat *:E
```

## Real-Time Monitoring

### In Android Studio:
1. Run app in debug mode
2. Set breakpoint in `ImageGenerator.kt` at line 36 (API call)
3. Step through to see exact response
4. Check variables for error details

## Common Issues & Their Log Messages

| Issue | Log Message |
|-------|-------------|
| Wrong API Key | `401 - Unauthorized` or `Invalid API key` |
| Rate Limit | `429 - Too Many Requests` |
| No Internet | `SocketTimeoutException` or `UnknownHostException` |
| Model Unavailable | `Model not found` or `Invalid model` |
| Prompt Too Long | `Prompt too long` or `400 - Bad Request` |

## Tips

1. **Clear Logcat** before testing: Click the trash icon in Logcat
2. **Use filters** to reduce noise
3. **Save logs** if you need to share errors
4. **Check timestamp** to see when errors occur
5. **Look for stack traces** - they show exact error location

## Still Not Working?

If you don't see ANY DALL-E logs:
1. Make sure the app is actually calling the API
2. Check if `generateAndDisplayImage()` is being called
3. Verify the API key is set correctly
4. Check if internet permission is granted

