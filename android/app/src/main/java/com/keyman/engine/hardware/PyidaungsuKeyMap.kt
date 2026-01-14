package com.keyman.engine.hardware

import android.view.KeyEvent

/**
 * Pyidaungsu MM keyboard layout mapping - Most popular Myanmar keyboard.
 * 
 * This is the standard Myanmar Unicode keyboard layout used across Myanmar.
 * Based on the KeyMagic Pyidaungsu MM layout, it's the most widely adopted
 * keyboard layout in Myanmar for Unicode typing.
 * 
 * Character Organization:
 * - Consonants distributed across all rows
 * - Vowel marks on home row and shifted keys
 * - Medials (ျ ြ ွ ှ) easily accessible
 * - Tone marks (့ း ံ) on home row shifted
 * 
 * Source: KeyMagic Pyidaungsu MM.km2
 * 
 * @author Myanmar Keyboard Implementation Team
 * @since 2.0.0
 */
class PyidaungsuKeyMap {
    
    // ========================================================================
    // Top Row Mappings (QWERTYUIOP)
    // ========================================================================
    
    private val topRowNormal = mapOf(
        KeyEvent.KEYCODE_Q to "\u1006",  // ဆ - SA
        KeyEvent.KEYCODE_W to "\u1010",  // တ - TA
        KeyEvent.KEYCODE_E to "\u1014",  // န - NA
        KeyEvent.KEYCODE_R to "\u1019",  // မ - MA
        KeyEvent.KEYCODE_T to "\u1021",  // အ - A
        KeyEvent.KEYCODE_Y to "\u1015",  // ပ - PA
        KeyEvent.KEYCODE_U to "\u1000",  // က - KA
        KeyEvent.KEYCODE_I to "\u1004",  // င - NGA
        KeyEvent.KEYCODE_O to "\u101E",  // သ - SA
        KeyEvent.KEYCODE_P to "\u1005",  // စ - CA
        KeyEvent.KEYCODE_LEFT_BRACKET to "\u101F",   // ဟ - HA
        KeyEvent.KEYCODE_RIGHT_BRACKET to "\u1037"   // ့ - DOT BELOW
    )
    
    private val topRowShift = mapOf(
        KeyEvent.KEYCODE_Q to "\u1008",  // ဈ - JHA
        KeyEvent.KEYCODE_W to "\u1011",  // ထ - TTHA
        KeyEvent.KEYCODE_E to "\u100F",  // ဏ - NNA
        KeyEvent.KEYCODE_R to "\u101A",  // ရ - RA
        KeyEvent.KEYCODE_T to "\u1027",  // ဧ - E (independent vowel)
        KeyEvent.KEYCODE_Y to "\u00B0",  // ° - DEGREE SIGN
        KeyEvent.KEYCODE_U to "\u102F",  // ု - VOWEL SIGN U (combining)
        KeyEvent.KEYCODE_I to "\u102E",  // ီ - VOWEL SIGN II (combining)
        KeyEvent.KEYCODE_O to "\u102D\u102F",  // ို - VOWEL SIGN I + U (combining)
        KeyEvent.KEYCODE_P to "\u100F\u103A",  // ဏ် - NNA + ASAT
        KeyEvent.KEYCODE_LEFT_BRACKET to "\u104D",   // ၍ - AFOREMENTIONED
        KeyEvent.KEYCODE_RIGHT_BRACKET to "\u101A\u103A"   // ရ် - RA + ASAT
    )
    
    // ========================================================================
    // Home Row Mappings (ASDFGHJKL;)
    // ========================================================================
    
    private val homeRowNormal = mapOf(
        KeyEvent.KEYCODE_A to "\u1031",  // ေ - VOWEL SIGN E (pre-base combining)
        KeyEvent.KEYCODE_S to "\u103C",  // ျ - MEDIAL RA (combining)
        KeyEvent.KEYCODE_D to "\u102D",  // ိ - VOWEL SIGN I (combining)
        KeyEvent.KEYCODE_F to "\u103A",  // ် - ASAT/VIRAMA (combining)
        KeyEvent.KEYCODE_G to "\u102B",  // ါ - VOWEL SIGN TALL AA (combining)
        KeyEvent.KEYCODE_H to "\u1037",  // ့ - DOT BELOW (combining)
        KeyEvent.KEYCODE_J to "\u103C",  // ြ - MEDIAL RA (pre-base)
        KeyEvent.KEYCODE_K to "\u102F",  // ု - VOWEL SIGN U (combining)
        KeyEvent.KEYCODE_L to "\u1038",  // း - VISARGA (combining)
        KeyEvent.KEYCODE_SEMICOLON to "\u1038",  // း - VISARGA (combining)
        KeyEvent.KEYCODE_APOSTROPHE to "\u1012"   // ဒ - DA
    )
    
    private val homeRowShift = mapOf(
        KeyEvent.KEYCODE_A to "\u1017",  // ဗ - BA
        KeyEvent.KEYCODE_S to "\u103B",  // ှ - MEDIAL YA (combining)
        KeyEvent.KEYCODE_D to "\u102E",  // ီ - VOWEL SIGN II (combining)
        KeyEvent.KEYCODE_F to "\u1004\u103A",  // င် - NGA + ASAT (kinzi base)
        KeyEvent.KEYCODE_G to "\u103D",  // ွ - MEDIAL WA (combining)
        KeyEvent.KEYCODE_H to "\u1036",  // ံ - ANUSVARA (combining)
        KeyEvent.KEYCODE_J to "\u1032",  // ဲ - VOWEL SIGN AI (combining)
        KeyEvent.KEYCODE_K to "\u1030",  // ူ - VOWEL SIGN UU (combining)
        KeyEvent.KEYCODE_L to "\u1038",  // း - VISARGA (combining)
        KeyEvent.KEYCODE_SEMICOLON to "\u1002",  // ဂ - GA
        KeyEvent.KEYCODE_APOSTROPHE to "\u1013"   // ဓ - DHA
    )
    
    // ========================================================================
    // Bottom Row Mappings (ZXCVBNM,.)
    // ========================================================================
    
    private val bottomRowNormal = mapOf(
        KeyEvent.KEYCODE_Z to "\u1016",  // ဖ - PHA
        KeyEvent.KEYCODE_X to "\u1011",  // ထ - TTHA
        KeyEvent.KEYCODE_C to "\u1001",  // ခ - KHA
        KeyEvent.KEYCODE_V to "\u101C",  // လ - LA
        KeyEvent.KEYCODE_B to "\u1018",  // ဘ - BHA
        KeyEvent.KEYCODE_N to "\u100A",  // ည - NNYA
        KeyEvent.KEYCODE_M to "\u102C",  // ာ - VOWEL SIGN AA (combining)
        KeyEvent.KEYCODE_COMMA to "\u104A",   // ၊ - LITTLE SECTION (Myanmar comma)
        KeyEvent.KEYCODE_PERIOD to "\u104B",  // ။ - SECTION (Myanmar period)
        KeyEvent.KEYCODE_SLASH to "/"         // / - Latin slash
    )
    
    private val bottomRowShift = mapOf(
        KeyEvent.KEYCODE_Z to "\u1007",  // ဇ - JA
        KeyEvent.KEYCODE_X to "\u100C",  // ဌ - TTHA
        KeyEvent.KEYCODE_C to "\u1003",  // ဃ - GHA
        KeyEvent.KEYCODE_V to "\u1020",  // ဠ - LLA
        KeyEvent.KEYCODE_B to "\u103F",  // ဿ - GREAT SA
        KeyEvent.KEYCODE_N to "\u1009",  // ဉ - NYA
        KeyEvent.KEYCODE_M to "\u1036",  // ံ - ANUSVARA (combining)
        KeyEvent.KEYCODE_COMMA to "\u102A",   // ဪ - AWW (independent vowel)
        KeyEvent.KEYCODE_PERIOD to "\u104E",  // ၎ - AFOREMENTIONED
        KeyEvent.KEYCODE_SLASH to "?"         // ? - Latin question mark
    )
    
    // ========================================================================
    // Number Row Mappings (1234567890)
    // ========================================================================
    
    private val numberRowNormal = mapOf(
        KeyEvent.KEYCODE_1 to "\u1041",  // ၁ - MYANMAR DIGIT ONE
        KeyEvent.KEYCODE_2 to "\u1042",  // ၂ - MYANMAR DIGIT TWO
        KeyEvent.KEYCODE_3 to "\u1043",  // ၃ - MYANMAR DIGIT THREE
        KeyEvent.KEYCODE_4 to "\u1044",  // ၄ - MYANMAR DIGIT FOUR
        KeyEvent.KEYCODE_5 to "\u1045",  // ၅ - MYANMAR DIGIT FIVE
        KeyEvent.KEYCODE_6 to "\u1046",  // ၆ - MYANMAR DIGIT SIX
        KeyEvent.KEYCODE_7 to "\u1047",  // ၇ - MYANMAR DIGIT SEVEN
        KeyEvent.KEYCODE_8 to "\u1048",  // ၈ - MYANMAR DIGIT EIGHT
        KeyEvent.KEYCODE_9 to "\u1049",  // ၉ - MYANMAR DIGIT NINE
        KeyEvent.KEYCODE_0 to "\u1040"   // ၀ - MYANMAR DIGIT ZERO
    )
    
    private val numberRowShift = mapOf(
        KeyEvent.KEYCODE_1 to "!",
        KeyEvent.KEYCODE_2 to "@",
        KeyEvent.KEYCODE_3 to "#",
        KeyEvent.KEYCODE_4 to "$",
        KeyEvent.KEYCODE_5 to "%",
        KeyEvent.KEYCODE_6 to "^",
        KeyEvent.KEYCODE_7 to "&",
        KeyEvent.KEYCODE_8 to "*",
        KeyEvent.KEYCODE_9 to "(",
        KeyEvent.KEYCODE_0 to ")"
    )
    
    // ========================================================================
    // Public API Methods
    // ========================================================================
    
    /**
     * Gets the Myanmar character for a given keycode and shift state.
     * 
     * @param keyCode Android keycode
     * @param isShifted Whether the Shift key is pressed
     * @return The Myanmar Unicode character or null if no mapping exists
     */
    fun getCharacter(keyCode: Int, isShifted: Boolean): String? {
        return if (isShifted) {
            topRowShift[keyCode]
                ?: homeRowShift[keyCode]
                ?: bottomRowShift[keyCode]
                ?: numberRowShift[keyCode]
        } else {
            topRowNormal[keyCode]
                ?: homeRowNormal[keyCode]
                ?: bottomRowNormal[keyCode]
                ?: numberRowNormal[keyCode]
        }
    }
    
    /**
     * Checks if a keycode has any mapping (normal or shifted).
     * 
     * @param keyCode Android keycode to check
     * @return true if the keycode has at least one mapping
     */
    fun hasMapping(keyCode: Int): Boolean {
        return getCharacter(keyCode, false) != null ||
               getCharacter(keyCode, true) != null
    }
    
    /**
     * Returns a list of all keycodes that have mappings.
     * 
     * @return List of Android keycodes with Pyidaungsu mappings
     */
    fun getAllMappedKeyCodes(): List<Int> {
        val allKeys = mutableSetOf<Int>()
        allKeys.addAll(topRowNormal.keys)
        allKeys.addAll(topRowShift.keys)
        allKeys.addAll(homeRowNormal.keys)
        allKeys.addAll(homeRowShift.keys)
        allKeys.addAll(bottomRowNormal.keys)
        allKeys.addAll(bottomRowShift.keys)
        allKeys.addAll(numberRowNormal.keys)
        allKeys.addAll(numberRowShift.keys)
        return allKeys.sorted()
    }
    
    /**
     * Gets statistics about the key mappings.
     * 
     * @return Map of statistics
     */
    fun getStatistics(): Map<String, Int> {
        val totalNormal = topRowNormal.size + homeRowNormal.size + 
                         bottomRowNormal.size + numberRowNormal.size
        val totalShift = topRowShift.size + homeRowShift.size + 
                        bottomRowShift.size + numberRowShift.size
        
        return mapOf(
            "total_normal_mappings" to totalNormal,
            "total_shift_mappings" to totalShift,
            "total_mappings" to (totalNormal + totalShift),
            "unique_keycodes" to getAllMappedKeyCodes().size
        )
    }
}
