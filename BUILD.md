# Myanmar3 Hardware Keyboard - Build Instructions

## Prerequisites

Before building, ensure you have:

1. **JDK 17 or higher** installed
   ```bash
   java -version
   ```

2. **Android SDK** (if building outside Android Studio)
   ```bash
   export ANDROID_HOME=/path/to/android/sdk
   ```

3. **Gradle** (wrapper included, but can install globally)
   ```bash
   gradle -version
   ```

## Build Steps

### 1. Navigate to Android project

```bash
cd /home/nanaungoo/Antigravity/garkeyboard/android
```

### 2. Make Gradle wrapper executable (Linux/Mac)

```bash
chmod +x gradlew
```

### 3. Build debug APK

```bash
./gradlew assembleDebug
```

**Output**: `app/build/outputs/apk/debug/app-debug.apk`

### 4. Build release APK (unsigned)

```bash
./gradlew assembleRelease
```

**Output**: `app/build/outputs/apk/release/app-release-unsigned.apk`

## Running Tests

### Unit Tests

```bash
./gradlew test
```

View results: `app/build/reports/tests/testDebugUnitTest/index.html`

### Instrumented Tests (requires connected device)

```bash
./gradlew connectedAndroidTest
```

## Installation

### Via ADB

```bash
# Install debug build
adb install app/build/outputs/apk/debug/app-debug.apk

# Install with replacement
adb install -r app/build/outputs/apk/debug/app-debug.apk

# Uninstall
adb uninstall com.keyman.myanmar
```

### Via Android Studio

1. Open `android/` folder in Android Studio
2. Wait for Gradle sync to complete
3. Click **Run** > **Run 'app'**
4. Select target device
5. App will build and install automatically

## Troubleshooting

### Build Errors

**Error: SDK location not found**
```bash
# Create local.properties
echo "sdk.dir=/path/to/Android/Sdk" > local.properties
```

**Error: Gradle sync failed**
```bash
# Clear Gradle cache
./gradlew clean
rm -rf .gradle
./gradlew assembleDebug
```

**Error: Java version mismatch**
```bash
# Check Java version
java -version
# Should be JDK 17+
```

### Test Failures

```bash
# Run tests with detailed output
./gradlew test --info

# Run specific test class
./gradlew test --tests Myanmar3KeyMapTest
```

## Build Variants

### Debug
- Debugging enabled
- No code optimization
- Includes debug symbols
- Larger APK size

```bash
./gradlew assembleDebug
```

### Release
- Debugging disabled
- Code optimization (ProGuard)
- No debug symbols
- Smaller APK size

```bash
./gradlew assembleRelease
```

## Signing Release APK

To sign for Play Store distribution:

1. **Create keystore**:
```bash
keytool -genkey -v -keystore myanmar3-release.jks \
  -keyalg RSA -keysize 2048 -validity 10000 \
  -alias myanmar3-key
```

2. **Add to build.gradle**:
```gradle
android {
    signingConfigs {
        release {
            storeFile file("myanmar3-release.jks")
            storePassword "your-password"
            keyAlias "myanmar3-key"
            keyPassword "your-password"
        }
    }
    buildTypes {
        release {
            signingConfig signingConfigs.release
        }
    }
}
```

3. **Build signed APK**:
```bash
./gradlew assembleRelease
```

## Continuous Integration

### GitHub Actions Example

```yaml
name: Android CI

on: [push, pull_request]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Set up JDK 17
        uses: actions/setup-java@v3
        with:
          java-version: '17'
      - name: Build with Gradle
        run: |
          cd android
          chmod +x gradlew
          ./gradlew assembleDebug test
      - name: Upload APK
        uses: actions/upload-artifact@v3
        with:
          name: app-debug
          path: android/app/build/outputs/apk/debug/app-debug.apk
```

## Next Steps After Building

1. ✅ Build successful → Install on device
2. ✅ APK installed → Enable in system settings
3. ✅ Keyboard enabled → Connect hardware keyboard
4. ✅ Test typing Myanmar characters
5. ✅ Run through test cases in README.md

## Support

If you encounter issues:
- Check logs: `./gradlew assembleDebug --stacktrace`
- Clean build: `./gradlew clean`
- Invalidate caches (Android Studio): File > Invalidate Caches
