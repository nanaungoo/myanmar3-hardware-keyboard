# How to Use Myanmar3 Hardware Keyboard

## Important: This is an IME, Not a System Keyboard Layout

Our Myanmar3 keyboard works differently from system keyboard layouts. It's an **Input Method (IME)** that intercepts hardware keyboard events, similar to how Gboard or SwiftKey work.

## Setup Instructions

### Step 1: Enable the IME
✅ **Already Done!** The keyboard is enabled and active.

### Step 2: Switch to Myanmar3 Subtype

When Myanmar3 Keyboard is active, you need to **switch to the Myanmar3 subtype**:

**Method 1: Using Keyboard Switcher**
1. Tap the keyboard icon in the navigation bar (or notification shade)
2. Select "Myanmar3 (Hardware)" from the list
   - NOT "English (US)"

**Method 2: Using garkeyboard** (if a hardware keyboard is connected)
1. While typing, press **Space bar + Shift** together
2. Or pull down notification shade and tap "Choose input method"
3. Select "Myanmar3 (Hardware)"

### Step 3: Verify It's Working

1. Open any text field (Notes, Messages, Chrome address bar, etc.)
2. **Make sure Myanmar3 Keyboard is the active  input method**
3. Start typing with your hardware keyboard:
   - Press `Shift+U` → Should type က (Myanmar character)
   - Press `Q` → Should type ဈ
   - Press `Shift+Q` → Should type ဆ

## How to Check Current Keyboard

Run this command to see which keyboard and subtype isactive:

```bash
adb shell dumpsys input_method | grep "mCurId"
```

Should show: `mCurId=com.keyman.myanmar/com.keyman.engine.KeyboardService`

## Switching Between Myanmar and English

### Option 1: Keyboard Switcher (Recommended)
- Tap keyboard icon → Choose "Myanmar3 (Hardware)" or "English (US)"

### Option 2: Add Another IME
1. Enable another keyboard (like Gboard or Samsung Keyboard)
2. Switch between keyboards using the keyboard icon
3. Use Myanmar3 when you need Myanmar characters
4. Use other keyboard for English

### Option 3: Use Ctrl+Tab (In Future)
Our keyboard supports `Ctrl+Tab` switching, but this requires the keyboard to handle the toggle internally.

## Why It's Not in "Physical Keyboard Layout" Settings

Android's "Physical keyboard layout" settings (Settings → System → Languages & input → Physical keyboard) is for **system keyboard layouts** that remap at the OS level.

Our implementation is an **IME-based solution**, which:
- ✅ Doesn't require root access
- ✅ Works on any Android device
- ✅ Can be distributed via APK
- ❌ Doesn't appear in "Physical keyboard layout" settings
- ℹ️ Requires switching to Myanmar3 as active IME

## Alternative: System Keyboard Layout (Advanced)

If you want it to appear in "Physical keyboard layout" settings, you would need to:

1. **Create .kcm files** (Key Character Map files)
2. **Implement KeyboardLayoutProvider** 
3. **Declare in AndroidManifest** with proper intent filters
4. Android will then show it as an option

However, this approach is more complex and has limitations in dynamic character handling.

## Troubleshooting

### Problem: Typing English characters instead of Myanmar

**Solution**: Switch to "Myanmar3 (Hardware)" subtype
- Tap keyboard icon → Select "Myanmar3 (Hardware)"

### Problem: Can't find keyboard switcher icon

**Solution**: 
1. Go to Settings → System → Languages & input → On-screen keyboard
2. Enable "Show input method selector"
3. Or pull down notification shade → "Choose input method"

### Problem: Want to switch quickly between Myanmar and English

**Solution**: 
- Method 1: Keep both "Myanmar3 (Hardware)" and "English (US)" subtypes
- Method 2: Enable a second IME (like Gboard) for English
- Then use keyboard switcher to toggle

## Quick Test

```bash
# See if keyboard is active
adb shell dumpsys input_method | grep "mCurId"

# Force switch to Myanmar3 subtype
adb shell ime set com.keyman.myanmar/com.keyman.engine.KeyboardService

# Check current subtype
adb shell dumpsys input_method | grep "mSubtypeName"
```

## Current Status

Based on logs, your setup is:
- ✅ Keyboard enabled  
- ✅ Keyboard active
- ℹ️ Need to verify correct subtype is selected

**Next  Step**: Open a text app and check which subtype is active using the keyboard switcher icon!
