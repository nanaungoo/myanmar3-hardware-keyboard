# Myanmar Keyboard Layout Options

This app provides two keyboard layouts for hardware keyboard support:

---

## 1. 🎯 ZawCode Layout (PRIMARY)

**Myanmar Unicode typing with Zawgyi-familiar key sequences**

**Characteristics:**
- Zawgyi-like typing for Unicode output
- Helps users transition from Zawgyi to Unicode
- Familiar key positions for experienced Myanmar typists
- Full Unicode compliance
- **Status**: ✅ Primary layout (default)

**Key Features:**
- Familiar typing sequences from Zawgyi
- Outputs proper Unicode characters
- Handles combining marks properly
- Auto-correction supported at system level

**Pros:**
- ✅ Unicode standard (future-proof)
- ✅ Familiar to Zawgyi users
- ✅ Easy transition to Unicode
- ✅ Widely supported

**Cons:**
- ❌ Requires learning if coming from other layouts
- ❌ Different from traditional typewriter

**Best for:**
- Users familiar with Zawgyi typing
- Anyone transitioning to Unicode
- General Myanmar typing needs
- Professional and casual use

**Detailed mapping:** See [ZAWCODE_LAYOUT.md](ZAWCODE_LAYOUT.md)

---

## 2. 🇬🇧 English Layout (SECONDARY)

**Standard English keyboard (passthrough)**

**Characteristics:**
- Standard QWERTY English typing
- System default behavior
- No custom character mappings
- **Status**: ✅ Secondary layout

**Key Features:**
- Standard English keyboard behavior
- All keys work as normal
- No Myanmar character output
- Fast switching via Ctrl+Space or Ctrl+Tab

**Best for:**
- Typing English text
- Entering URLs, emails
- Programming, technical input
- Mixed language documents

---

## 🔄 Layout Switching

**How to switch between layouts:**

- **Ctrl + Space** - Toggle between ZawCode and English
- **Ctrl + Tab** - Toggle between ZawCode and English

The currently selected layout persists across app restarts.

---

## 📊 Comparison Table

| Feature | ZawCode | English |
|---------|---------|---------|
| **Purpose** | Myanmar Unicode typing | Standard English typing |
| **Default** | ✅ Yes | ❌ No |
| **Unicode** | ✅ Yes | N/A |
| **Learning Curve** | Medium | None |
| **Typing Speed** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐⭐ |
| **Best for** | Myanmar text | English text |

---

## 🎯 Usage Recommendations

### When to use ZawCode:
- Writing Myanmar language content
- Typing in Myanmar messaging apps
- Creating Myanmar documents
- Social media posts in Myanmar

### When to use English:
- Writing English text
- Entering URLs and email addresses
- Programming or technical work
- Mixed language documents

### Quick Switching:
Switch seamlessly between layouts using `Ctrl+Space` or `Ctrl+Tab` for efficient bilingual typing.

---

## 📝 Implementation Details

### ZawCode Implementation:
- Based on KeyMagic ZawCode layout specification
- Full QWERTY to Myanmar Unicode mapping
- Handles all Myanmar3 characters
- Proper combining mark support
- Optimized for hardware keyboards

### English Implementation:
- Passthrough to Android system default
- No custom key mappings
- Standard keyboard behavior
- Zero overhead

---

## 📚 References

- [ZawCode Layout Details](ZAWCODE_LAYOUT.md)
- [KeyMagic Project](https://github.com/thantthet/keymagic)
- [Myanmar Unicode Standard](https://www.unicode.org/charts/ PDF/U1000.pdf)
- [Project README](README.md)
