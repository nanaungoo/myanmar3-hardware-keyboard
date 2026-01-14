# Pyidaungsu MM Keyboard Layout - Character Mappings

Based on KeyMagic Pyid

aungsu MM layout and Myanmar typing standards.

## Complete Key Mappings

### Top Row (Numbers & Top Letter Row)

| Key | Normal | Shift | Unicode Normal | Unicode Shift |
|-----|--------|-------|----------------|---------------|
| 1 | ၁ | ! | U+1041 | U+0021 |
| 2 | ၂ | @ | U+1042 | U+0040 |
| 3 | ၃ | # |U+1043 | U+0023 |
| 4 | ၄ | $ | U+1044 | U+0024 |
| 5 | ၅ | % | U+1045 | U+0025 |
| 6 | ၆ | ^ | U+1046 | U+005E |
| 7 | ၇ | & | U+1047 | U+0026 |
| 8 | ၈ | * | U+1048 | U+002A |
| 9 | ၉ | ( | U+1049 | U+0028 |
| 0 | ၀ | ) | U+1040 | U+0029 |
| - | - | _ | U+002D | U+005F |
| = | = | + | U+003D | U+002B |

| Key | Normal | Shift |Unicode Normal | Unicode Shift |
|-----|--------|-------|----------------|---------------|
| Q | ဆ | ဈ | U+1006 | U+1008 |
| W | တ | ထ | U+1010 | U+1011 |
| E | န | ဏ | U+1014 | U+100F |
| R | မ | ရ | U+1019 | U101A |
| T | အ | ဧ | U+1021 | U+1027 |
| Y | ပ | ° | U+1015 | U+00B0 |
| U | က | ု | U+1000 | U+102F |
| I | င | ီ | U+1004 | U+102E |
| O | သ | ို | U+101E | U+102D U+102F |
| P | စ | ဏ် | U+1005 | U+100F U+103A |
| [ | ဟ | ၍ | U+101F | U+104D |
| ] | ့ | ရ် | U+1037 | U+101A U+103A |

### Home Row (ASDFGHJKL)

| Key | Normal | Shift | Unicode Normal | Unicode Shift |
|-----|--------|-------|----------------|---------------|
| A | ေ | ဗ | U+1031 | U+1017 |
| S | ျ | ှ | U+103C | U+103B |
| D | ိ | ီ | U+102D | U+102E |
| F | ် | င် | U+103A | U+1004 U+103A |
| G | ါ | ွ | U+102B | U+103D |
| H | ့ | ံ | U+1037 | U+1036 |
| J | ြ | ဲ | U+103C | U+1032 |
| K | ု | ူ | U+102F | U+1030 |
| L | း | း | U+1038 | U+1038 |
| ; | း | ဂ | U+1038 | U+1002 |

### Bottom Row (ZXCVBNM)

| Key | Normal | Shift | Unicode Normal | Unicode Shift |
|-----|--------|-------|----------------|---------------|
| Z | ဖ | ဇ | U+1016 | U+1007 |
| X | ထ | ဌ | U+1011 | U+100C |
| C | ခ | ဃ | U+1001 | U+1003 |
| V | လ | ဠ | U+101C | U+1020 |
| B | ဘ | ဿ | U+1018 | U+103F |
| N | ည | ဉ | U+100A | U+1009 |
| M | ာ | ံ | U+102C | U+1036 |
| , | ၊ | ဪ | U+104A | U+102A |
| . | ။ | ၎ | U+104B | U+104E |
| / | / | ? | U+002F | U+003F |

## Special Notes

### Pre-base Marks
- Key `A` produces ေ (U+1031) which is a pre-base vowel
- This mark appears BEFORE the consonant visually but is typed AFTER

### Combining Marks (Type after consonant)
- `S` = ျ (ya medial)
- `D` = ိ (i vowel)
- `F` = ် (asat/virama)
- `G` = ါ (aa vowel)
- `H` = ့ (dot below)
- `J` = ြ (ra medial)
- `K` = ု (u vowel)

### Common Typing Patterns

**Example: ကျောင်း (School)**
```
Type: U S A G F L
Result: က ျ ေ ါ ် း = ကျောင်း
```

**Example: မြန်မာ (Myanmar)**
```
Type: R J E F R M
Result: မ ြ န ် မ ာ = မြန်မာ
```

## Tips

1. Type consonant first, then combining marks
2. The ေ vowel (A key) appears before consonant visually but type it AFTER
3. Common medials: S (ျ), J (ြ)
4. Common vowels: D (ိ), Shift+I (ီ), K (ု), Shift+K (ူ), G (ါ), M (ာ)
5. End with tone marks: H (့), L/; (း), F (်)
