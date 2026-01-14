package com.keyman.engine.hardware

import android.view.KeyEvent

/**
 * ZawCode keyboard layout mapping - Zawgyi-like typing for Unicode output.
 * 
 * ZawCode allows users to type using familiar Zawgyi key sequences
 * but outputs proper Myanmar Unicode characters. This is ideal for users
 * transitioning from Zawgyi to Unicode.
 * 
 * Key Features:
 * - Zawgyi-familiar typing sequences
 * - Unicode-compliant output
 * - Helps bridge Zawgyi → Unicode transition
 * - Auto-correction handled by system
 * 
 * Note: This is a simplified version. Full ZawCode includes complex
 * reordering rules that are best handled at the IME level.
 * 
 * Source: KeyMagic ZawCode.kms
 * 
 * @author Myanmar Keyboard Implementation Team
 * @since 2.0.0
 */
class ZawCodeKeyMap {
    
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
        KeyEvent.KEYCODE_LEFT_BRACKET to "\u101F"    // ဟ - HA
    )
    
    private val topRowShift = mapOf(
        KeyEvent.KEYCODE_Q to "\u103B\u103E",  // ွှေ - combined medials
        KeyEvent.KEYCODE_W to "\u103B\u103D\u103E",  // ွှေ့ - combined medials
        KeyEvent.KEYCODE_E to "\u1014",  // န - NA (same as normal)
        KeyEvent.KEYCODE_R to "\u103B\u103D",  // ွေ့ - combined medials
        KeyEvent.KEYCODE_T to "\u103D\u103E",  // ေွ့ - combined medials
        KeyEvent.KEYCODE_Y to "\u1037",  // ့ - DOT BELOW
        KeyEvent.KEYCODE_U to "\u1037",  // ့ - DOT BELOW
        KeyEvent.KEYCODE_I to "\u103E\u102F",  // ေု - E + U vowel
        KeyEvent.KEYCODE_O to "\u1025",  // ဥ - U (independent vowel)
        KeyEvent.KEYCODE_P to "\u100F",  // ဏ - NNA
        KeyEvent.KEYCODE_LEFT_BRACKET to "\u1027"    // ဧ - E (independent vowel)
    )
    
    // ========================================================================
    // Home Row Mappings (ASDFGHJKL;')
    // ========================================================================
    
    private val homeRowNormal = mapOf(
        KeyEvent.KEYCODE_A to "\u1031",  // ေ - VOWEL SIGN E (pre-base)
        KeyEvent.KEYCODE_S to "\u103B",  // ှ - MEDIAL YA
        KeyEvent.KEYCODE_D to "\u102D",  // ိ - VOWEL SIGN I
        KeyEvent.KEYCODE_F to "\u103A",  // ် - ASAT
        KeyEvent.KEYCODE_G to "\u102B",  // ါ - VOWEL SIGN TALL AA
        KeyEvent.KEYCODE_H to "\u1037",  // ့ - DOT BELOW
        KeyEvent.KEYCODE_J to "\u103C",  // ြ - MEDIAL RA (pre-base)
        KeyEvent.KEYCODE_K to "\u102F",  // ု - VOWEL SIGN U
        KeyEvent.KEYCODE_L to "\u1030",  // ူ - VOWEL SIGN UU
        KeyEvent.KEYCODE_SEMICOLON to "\u1038",  // း - VISARGA
        KeyEvent.KEYCODE_APOSTROPHE to "\u1012"   // ဒ - DA
    )
    
    private val homeRowShift = mapOf(
        KeyEvent.KEYCODE_A to "\u1017",  // ဗ - BA
        KeyEvent.KEYCODE_S to "\u103E",  // ေ့ - MEDIAL HA
        KeyEvent.KEYCODE_D to "\u102E",  // ီ - VOWEL SIGN II
        KeyEvent.KEYCODE_F to "\u1004\u103A\u1039",  // င်္ - kinzi
        KeyEvent.KEYCODE_G to "\u103D",  // ွ - MEDIAL WA
        KeyEvent.KEYCODE_H to "\u1036",  // ံ - ANUSVARA
        KeyEvent.KEYCODE_J to "\u1032",  // ဲ - VOWEL SIGN AI
        KeyEvent.KEYCODE_K to "\u102F",  // ု - VOWEL SIGN U (same as normal)
        KeyEvent.KEYCODE_L to "\u1030",  // ူ - VOWEL SIGN UU (same as normal)
        KeyEvent.KEYCODE_SEMICOLON to "\u102B\u103A",  // ါ် - AA + ASAT
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
        KeyEvent.KEYCODE_M to "\u102C",  // ာ - VOWEL SIGN AA
        KeyEvent.KEYCODE_COMMA to "\u101A",   // ယ - YA
        KeyEvent.KEYCODE_PERIOD to ".",       // . - Latin period
        KeyEvent.KEYCODE_SLASH to "\u104B"    // ။ - Myanmar period
    )
    
    private val bottomRowShift = mapOf(
        KeyEvent.KEYCODE_Z to "\u1007",  // ဇ - JA
        KeyEvent.KEYCODE_X to "\u100C",  // ဌ - TTHA
        KeyEvent.KEYCODE_C to "\u1003",  // ဃ - GHA
        KeyEvent.KEYCODE_V to "\u1020",  // ဠ - LLA
        KeyEvent.KEYCODE_B to "\u103C",  // ြ - MEDIAL RA
        KeyEvent.KEYCODE_N to "\u103C",  // ြ - MEDIAL RA
        KeyEvent.KEYCODE_M to "\u103C",  // ြ - MEDIAL RA
        KeyEvent.KEYCODE_COMMA to "\u101D",   // ဝ - WA
        KeyEvent.KEYCODE_PERIOD to "\u1008",  // ဈ - JHA
        KeyEvent.KEYCODE_SLASH to "\u104A"    // ၊ - Myanmar comma
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
        KeyEvent.KEYCODE_1 to "\u100D",  // ဍ - DDHA
        KeyEvent.KEYCODE_2 to "\u100F\u1039\u100D",  // ဍ္ဍ - DDHA cluster
        KeyEvent.KEYCODE_3 to "\u100B",  // ဋ - TTA
        KeyEvent.KEYCODE_4 to "\u104C",  // ၌ - LOCATIVE
        KeyEvent.KEYCODE_5 to "%",
        KeyEvent.KEYCODE_6 to "/",
        KeyEvent.KEYCODE_7 to "\u101B",  // ရ - RA
        KeyEvent.KEYCODE_8 to "\u1002",  // ဂ - GA
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
     * @return List of Android keycodes with ZawCode mappings
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
