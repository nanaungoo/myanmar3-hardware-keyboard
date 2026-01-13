# Myanmar3 Hardware Keyboard for Keyman Android

A custom hardware keyboard implementation for typing Myanmar (Burmese) Unicode characters using standard QWERTY physical keyboards on Android devices.

## Features

- 🎯 **Complete Myanmar3 Layout**: Full QWERTY to Myanmar Unicode character mapping
- ⌨️ **Hardware Keyboard Support**: Works with Bluetooth and USB keyboards
- 🔤 **Combining Marks**: Proper handling of Myanmar diacritics and combining characters
- 🔄 **Easy Switching**: Use `Ctrl+Tab` to switch between Myanmar3 and standard layouts
- 🎨 **Modular Design**: Clean, well-documented Kotlin code following best practices

## Project Structure

```
garkeyboard/
└── android/
    └── app/
        ├── build.gradle                    # Gradle build configuration
        └── src/
            ├── main/
            │   ├── AndroidManifest.xml     # App manifest with IME service
            │   ├── java/com/keyman/engine/
            │   │   ├── KeyboardService.kt           # Main IME service
            │   │   └── hardware/
            │   │       ├── HardwareKeyboardMapper.kt     # Orchestrator
            │   │       ├── Myanmar3KeyMap.kt             # Character mappings
            │   │       └── CombiningMarkHandler.kt       # Diacritic handling
            │   └── res/
            │       ├── values/
            │       │   └── strings.xml     # String resources
            │       └── xml/
            │           └── method.xml      # IME configuration
            └── test/
                └── java/                   # Unit tests
```

## Architecture

The implementation follows a modular, layered architecture:

1. **KeyboardService**: Android `InputMethodService` that intercepts hardware key events
2. **HardwareKeyboardMapper**: Orchestrates the mapping process
3. **Myanmar3KeyMap**: Contains all QWERTY → Myanmar character mappings
4. **CombiningMarkHandler**: Manages combining marks and diacritics

```
Hardware Key Event
    ↓
KeyboardService (onKeyDown/onKeyUp)
    ↓
HardwareKeyboardMapper (mapKey)
    ↓
Myanmar3KeyMap + CombiningMarkHandler
    ↓
Unicode Output → Text Input
```

## Requirements

- **Android SDK**: API 21+ (Android 5.0 Lollipop or higher)
- **Kotlin**: 1.8+
- **Gradle**: 8.0+
- **Physical Keyboard**: Bluetooth or USB QWERTY keyboard

## Installation

### Option 1: Build from Source

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd garkeyboard
   ```

2. Open in Android Studio:
   ```bash
   studio android/
   ```

3. Build and install:
   ```bash
   cd android
   ./gradlew assembleDebug
   adb install app/build/outputs/apk/debug/app-debug.apk
   ```

### Option 2: Pre-built APK

Download the latest APK from the [Releases](releases) page and install on your device.

## Setup

1. **Install the app** on your Android device

2. **Enable the keyboard**:
   - Go to **Settings > System > Languages & input**
   - Tap **Virtual keyboard**
   - Tap **Manage keyboards**
   - Enable **Myanmar3 Hardware Keyboard**

3. **Connect your hardware keyboard** (Bluetooth or USB)

4. **Activate Myanmar3**:
   - The keyboard will auto-activate when a hardware keyboard is connected
   - Or use `Ctrl+Tab` to switch to Myanmar3 mode

## Usage

### Basic Typing

Once activated, type Myanmar characters using your QWERTY keyboard:

| Key | Normal | Shift | Key | Normal | Shift |
|-----|--------|-------|-----|--------|-------|
| Q | ဈ | ဆ | A | ဗ | ေ |
| W | ဝ | တ | S | ှ | ျ |
| E | ဣ | န | D | ီ | ိ |
| K | ဒ | ု | L | ဓ | ူ |

[See full mapping in visual_reference.md](../brain/visual_reference.md)

### Keyboard Switching

- **Ctrl + Tab**: Switch between Myanmar3 and standard layout
- The current layout persists across sessions

### Combining Marks

The keyboard properly handles Myanmar combining marks:

- **Vowel signs**: Type consonant first, then vowel mark (e.g., `Shift+U` then `D` = ကီ)
- **Medials**: Combine with consonants (e.g., `Shift+I` then `S` = ငှ)
- **Virama**: Use `F` for consonant stacking (e.g., `Shift+Y` `F` `Shift+B` = ပ္ဘ)

### Test Strings

Try typing these Myanmar phrases:

- **Hello**: မင်္ဂလာပါ
- **Myanmar**: မြန်မာ
- **Thank you**: ကျေးဇူးတင်ပါတယ်

## Development

### Code Style

The code follows Kotlin best practices:

- **Naming**: `PascalCase` for classes, `camelCase` for functions
- **Documentation**: KDoc comments for all public APIs
- **Null Safety**: Extensive use of Kotlin's null safety features
- **Modular Design**: Clear separation of concerns

### Testing

Run unit tests:
```bash
./gradlew test
```

Run instrumented tests:
```bash
./gradlew connectedAndroidTest
```

### Building

Build debug APK:
```bash
./gradlew assembleDebug
```

Build release APK:
```bash
./gradlew assembleRelease
```

## Troubleshooting

### Keyboard not working?

1. Check if Myanmar3 is enabled in system settings
2. Ensure hardware keyboard is connected
3. Try toggling with `Ctrl+Tab`
4. Check logs: `adb logcat | grep Keyman`

### Characters not displaying correctly?

1. Ensure your app/system font supports Myanmar Unicode
2. Verify Unicode block U+1000–U+109F is available
3. Test with a known Unicode-compatible app (e.g., Chrome browser)

### Auto-activation not working?

1. Go to keyboard settings
2. Enable "Auto-activate Myanmar3" preference
3. Reconnect hardware keyboard

## Contributing

Contributions are welcome! Please:

1. Fork the repository
2. Create a feature branch
3. Follow the existing code style
4. Add tests for new functionality
5. Submit a pull request

## License

[Specify your license here - e.g., MIT, Apache 2.0, GPL]

## Credits

- **Myanmar3 Layout**: Based on standard Myanmar3 keyboard layout by Myanmar NLP
- **Keyman**: Inspired by [Keyman open-source project](https://keyman.com)
- **Unicode**: Myanmar Unicode block (U+1000–U+109F)

## Resources

- [Implementation Plan](../brain/implementation_plan.md)
- [Visual Reference Guide](../brain/visual_reference.md)
- [Myanmar Unicode Standard](https://www.unicode.org/charts/PDF/U1000.pdf)
- [Keyman Documentation](https://help.keyman.com)

## Support

For issues, questions, or suggestions:
- Open an issue on GitHub
- Contact: [your-email@example.com]

---

**Made with ❤️ for the Myanmar community**
