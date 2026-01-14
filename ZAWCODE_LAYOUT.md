# ZawCode Keyboard Layout - Character Mappings

**ZawCode: A Unicode keyboard that types like Zawgyi**

This layout allows you to type using Zawgyi key sequences but outputs proper Unicode Myanmar characters.

## Complete Key Mappings

### Top Row (Numbers & Letters)

| Key | Normal | Shift | Unicode Normal | Unicode Shift |
|-----|--------|-------|----------------|---------------|
| 1 | ၁ | ဍ | U+1041 | U+100D |
| 2 | ၂ | ဍ္ဍ | U+1042 | U+100F U+1039 U+100D |
| 3 | ၃ | ဋ | U+1043 | U+100B |
| 4 | ၄ | ၌ | U+1044 | U+104C |
| 5 | ၅ | % | U+1045 | U+0025 |
| 6 | ၆ | / | U+1046 | U+002F |
| 7 | ၇ | ရ | U+1047 | U+101B |
| 8 | ၈ | ဂ | U+1048 | U+1002 |
| 9 | ၉ | ( | U+1049 | U+0028 |
| 0 | ၀ | ) | U+1040 | U+0029 |

| Key | Normal | Shift | Unicode Normal | Unicode Shift |
|-----|--------|-------|----------------|---------------|
| Q | ဆ | ွှေ | U+1006 | U+103B U+103E |
| W | တ | ွှေ့ | U+1010 | U+103B U+103D U+103E |
| E | န | န | U+1014 | U+1014 |
| R | မ | ွေ့ | U+1019 | U+103B U+103D |
| T | အ | ေွ့ | U+1021 | U+103D U+103E |
| Y | ပ | ့ | U+1015 | U+1037 |
| U | က | ့ | U+1000 | U+1037 |
| I | င | ေု | U+1004 | U+103E U+102F |
| O | သ | ဥ | U+101E | U+1025 |
| P | စ | ဏ | U+1005 | U+100F |
| [ | ဟ | ဧ | U+101F | U+1027 |

### Home Row (ASDFGHJKL)

| Key | Normal | Shift | Unicode Normal | Unicode Shift |
|-----|---------|-------|----------------|---------------|
| A | ေ | ဗ | U+1031 | U+1017 |
| S | ှ | ေ့ | U+103B | U+103E |
| D | ိ | ီ | U+102D | U+102E |
| F | ် | င် | U+103A | U+1004 U+103A U+1039 |
| G | ါ | ွ | U+102B | U+103D |
| H | ့ | ံ | U+1037 | U+1036 |
| J | ြ | ဲ | U+103C | U+1032 |
| K | ု | ု | U+102F | U+102F |
| L | ူ | ူ | U+1030 | U+1030 |
| ; | း | း | U+1038 | U+102B U+103A |
| ' | ဒ | ဓ | U+1012 | U+1013 |

### Bottom Row (ZXCVBNM)

| Key | Normal | Shift | Unicode Normal | Unicode Shift |
|-----|--------|-------|----------------|---------------|
| Z | ဖ | ဇ | U+1016 | U+1007 |
| X | ထ |ဌ | U+1011 | U+100C |
| C | ခ | ဃ | U+1001 | U+1003 |
| V | လ | ဠ | U+101C | U+1020 |
| B | ဘ | ြ | U+1018 | U+103C |
| N | ည | ြ | U+100A | U+103C |
|M | ာ | ြ | U+102C | U+103C |
| , | ယ | ဝ | U+101A | U+101D |
| . | . | ဈ | U+002E | U+1008 |
| / | ။ | ၊ | U+104B | U+104A |

## Special ZawCode Features

### Zawgyi-like Typing Behavior

**Key Difference from Standard Unicode:**
- ZawCode lets you type in the familiar Zawgyi sequence
- But outputs correct Unicode characters
- Perfect for users transitioning from Zawgyi

### Common Combining Patterns

**Medials (typed before or with consonant):**
- `S` = ှ (ha medial)
- `J` = ြ (ra medial - pre-base)
- `G` (Shift) = ွ (wa medial)

**Vowels:**
- `A` = ေ (e vowel - pre-base)
- `D` = ိ (i vowel)
- `Shift+D` = ီ (ii vowel)
- `K` = ု (u vowel)
- `L` = ူ (uu vowel)
- `G` = ါ (aa vowel)
- `M` = ာ (a vowel)

**Tone Marks:**
- `H` = ့ (dot below)
- `;` = း (visarga)
- `F` = ် (asat/virama)

### Special Key Sequences (Grave Accent ` = Dead Key)

ZawCode uses the grave accent (`) as a "dead key" for special combinations:

```
` + B = ္ဘ (stacked consonant)
` + N = ဉ (nya)
` + V = ္လ (stacked la)
` + Space = zero-width space
```

## Example Typing

**မြန်မာ (Myanmar) - Zawgyi style:**
```
Type: R J E F R M
မ + ြ + န + ် + မ + ာ = မြန်မာ
```

**ကျောင်း (School) - Zawgyi style:**
```
Type: U S A G ; း
က + ှ + ေ + ါ + း = ကျောင်း  
(Auto-reordered to correct Unicode sequence)
```

## Auto-correction Features

ZawCode includes intelligent auto-correction:

1. **Reordering**: Automatically reorders characters to Unicode storage order
2. **Medial correction**: ွှ → ှွ (correct order)
3. **Pre-base handling**: Moves ေ and ြ to correct positions
4. **Number conversion**: ၀ ၇ ၈ → ဝ ရ ဂ when used with diacritics

## Why Use ZawCode?

✅ **For Zawgyi users**: Type the way you're used to
✅ **Unicode output**: Get proper Unicode characters
✅ **Transition aid**: Learn Unicode without changing typing habits
✅ **Auto-correction**: Fixes common Zawgyi-style mistakes

## Differences from Pyidaungsu

| Feature | Pyidaungsu | ZawCode |
|---------|------------|---------|
| **Typing style** | Unicode-native | Zawgyi-like |
| **Key sequence** | Strict order | Flexible (auto-corrects) |
| **Learning curve** | Medium | Easy (if you know Zawgyi) |
| **Target users** | New Unicode typists | Zawgyi → Unicode transition |
