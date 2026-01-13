package com.keyman.engine.hardware

import android.view.KeyEvent

/**
 * Complete Myanmar3 keyboard layout mapping.
 * Based on standard Myanmar3 (Visual Order) layout.
 * 
 * This class contains the comprehensive mapping from QWERTY physical keys
 * to Myanmar Unicode characters. It supports both normal and shifted states
 * for all letter keys, providing access to the full Myanmar character set.
 * 
 * The Myanmar3 layout is designed for ease of learning and typing efficiency,
 * placing the most commonly used Myanmar characters on easily accessible keys.
 * 
 * Character Organization:
 * - Top row (QWERTYUIOP): Mix of consonants and independent vowels
 * - Home row (ASDFGHJKL;): Consonants and combining vowel marks
 * - Bottom row (ZXCVBNM,.): Additional consonants and punctuation
 * 
 * @author Myanmar Keyboard Implementation Team
 * @since 1.0.0
 */
class Myanmar3KeyMap {
    
    // ========================================================================
    // Top Row Mappings (QWERTYUIOP)
    // ========================================================================
    
    /**
     * Normal (unshifted) state for top row keys.
     * Contains various Myanmar consonants and independent vowels.
     */
    private val topRowNormal = mapOf(
        KeyEvent.KEYCODE_Q to "\u1008",  // ဈ - CHA
        KeyEvent.KEYCODE_W to "\u101D",  // ဝ - WA
        KeyEvent.KEYCODE_E to "\u1023",  // ဣ - I (independent vowel)
        KeyEvent.KEYCODE_R to "\u104E",  // ၎ - AFOREMENTIONED
        KeyEvent.KEYCODE_T to "\u1024",  // ဤ - II (independent vowel)
        KeyEvent.KEYCODE_Y to "\u104C",  // ၌ - LOCATIVE
        KeyEvent.KEYCODE_U to "\u1025",  // ဥ - U (independent vowel)
        KeyEvent.KEYCODE_I to "\u104D",  // ၍ - COMPLETED
        KeyEvent.KEYCODE_O to "\u103F",  // ဿ - GREAT SA
        KeyEvent.KEYCODE_P to "\u100F",  // ဏ - NNA
        KeyEvent.KEYCODE_LEFT_BRACKET to "\u1027",   // ဧ - E (independent vowel)
        KeyEvent.KEYCODE_RIGHT_BRACKET to "\u102A"   // ဪ - O (independent vowel)
    )
    
    /**
     * Shifted state for top row keys.
     * Contains frequently used Myanmar consonants.
     */
    private val topRowShift = mapOf(
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
        KeyEvent.KEYCODE_RIGHT_BRACKET to "\u1029"   // ဩ - AWW
    )
    
    // ========================================================================
    // Home Row Mappings (ASDFGHJKL;)
    // ========================================================================
    
    /**
     * Normal (unshifted) state for home row keys.
     * Contains consonants and important combining marks.
     */
    private val homeRowNormal = mapOf(
        KeyEvent.KEYCODE_A to "\u1017",  // ဗ - BA
        KeyEvent.KEYCODE_S to "\u103B",  // ှ - MEDIAL YA (combining)
        KeyEvent.KEYCODE_D to "\u102E",  // ီ - VOWEL SIGN II (combining)
        KeyEvent.KEYCODE_F to "\u1039",  // ် - VIRAMA/ASAT (combining)
        KeyEvent.KEYCODE_G to "\u103D",  // ွ - MEDIAL WA (combining)
        KeyEvent.KEYCODE_H to "\u1036",  // ံ - ANUSVARA (combining)
        KeyEvent.KEYCODE_J to "\u1032",  // ဲ - VOWEL SIGN AI (combining)
        KeyEvent.KEYCODE_K to "\u1012",  // ဒ - DA
        KeyEvent.KEYCODE_L to "\u1013",  // ဓ - DHA
        KeyEvent.KEYCODE_SEMICOLON to "\u1002",  // ဂ - GA
        KeyEvent.KEYCODE_APOSTROPHE to "\""      // " - Latin quotation mark
    )
    
    /**
     * Shifted state for home row keys.
     * Contains vowel signs and combining marks.
     */
    private val homeRowShift = mapOf(
        KeyEvent.KEYCODE_A to "\u1031",  // ေ - VOWEL SIGN E (pre-base combining)
        KeyEvent.KEYCODE_S to "\u103C",  // ျ - MEDIAL RA (combining)
        KeyEvent.KEYCODE_D to "\u102D",  // ိ - VOWEL SIGN I (combining)
        KeyEvent.KEYCODE_F to "\u1037",  // ့ - DOT BELOW (combining)
        KeyEvent.KEYCODE_G to "\u102B",  // ါ - VOWEL SIGN TALL AA (combining)
        KeyEvent.KEYCODE_H to "\u1037",  // ့ - DOT BELOW (combining)
        KeyEvent.KEYCODE_J to "\u103C",  // ြ - MEDIAL RA (pre-base when combined)
        KeyEvent.KEYCODE_K to "\u102F",  // ု - VOWEL SIGN U (combining)
        KeyEvent.KEYCODE_L to "\u1030",  // ူ - VOWEL SIGN UU (combining)
        KeyEvent.KEYCODE_SEMICOLON to "\u1038",  // း - VISARGA (combining)
        KeyEvent.KEYCODE_APOSTROPHE to "'"       // ' - Latin apostrophe
    )
    
    // ========================================================================
    // Bottom Row Mappings (ZXCVBNM,.)
    // ========================================================================
    
    /**
     * Normal (unshifted) state for bottom row keys.
     * Contains additional consonants and punctuation.
     */
    private val bottomRowNormal = mapOf(
        KeyEvent.KEYCODE_Z to "\u1007",  // ဇ - JA
        KeyEvent.KEYCODE_X to "\u100C",  // ဌ - TTHA
        KeyEvent.KEYCODE_C to "\u1003",  // ဃ - GHA
        KeyEvent.KEYCODE_V to "\u1020",  // ဠ - LLA
        KeyEvent.KEYCODE_B to "\u101A",  // ယ - YA
        KeyEvent.KEYCODE_N to "\u1009",  // ဉ - NYA
        KeyEvent.KEYCODE_M to "\u1026",  // ဦ - UU (independent vowel)
        KeyEvent.KEYCODE_COMMA to "\u104A",   // ၊ - LITTLE SECTION (Myanmar comma)
        KeyEvent.KEYCODE_PERIOD to "\u104B",  // ။ - SECTION (Myanmar period)
        KeyEvent.KEYCODE_SLASH to "?"         // ? - Latin question mark
    )
    
    /**
     * Shifted state for bottom row keys.
     * Contains more consonants and combining marks.
     */
    private val bottomRowShift = mapOf(
        KeyEvent.KEYCODE_Z to "\u1016",  // ဖ - PHA
        KeyEvent.KEYCODE_X to "\u1011",  // ထ - TTHA
        KeyEvent.KEYCODE_C to "\u1001",  // ခ - KHA
        KeyEvent.KEYCODE_V to "\u101C",  // လ - LA
        KeyEvent.KEYCODE_B to "\u1018",  // ဘ - BHA
        KeyEvent.KEYCODE_N to "\u100A",  // ည - NNYA
        KeyEvent.KEYCODE_M to "\u102C",  // ာ - VOWEL SIGN AA (combining)
        KeyEvent.KEYCODE_COMMA to "<",   // < - Latin less than
        KeyEvent.KEYCODE_PERIOD to ">",  // > - Latin greater than
        KeyEvent.KEYCODE_SLASH to "/"    // / - Latin slash
    )
    
    // ========================================================================
    // Public API Methods
    // ========================================================================
    
    /**
     * Gets the Myanmar character for a given keycode and shift state.
     * 
     * This method looks up the character across all three rows (top, home, bottom)
     * based on the provided keycode and modifier state.
     * 
     * @param keyCode Android keycode (e.g., KeyEvent.KEYCODE_Q)
     * @param isShifted Whether the Shift key is pressed
     * @return The Myanmar Unicode character or null if no mapping exists
     * 
     * @example
     * ```kotlin
     * val keyMap = Myanmar3KeyMap()
     * keyMap.getCharacter(KeyEvent.KEYCODE_Q, false) // Returns "ဈ"
     * keyMap.getCharacter(KeyEvent.KEYCODE_Q, true)  // Returns "ဆ"
     * ```
     */
    fun getCharacter(keyCode: Int, isShifted: Boolean): String? {
        return if (isShifted) {
            topRowShift[keyCode] 
                ?: homeRowShift[keyCode] 
                ?: bottomRowShift[keyCode]
        } else {
            topRowNormal[keyCode] 
                ?: homeRowNormal[keyCode] 
                ?: bottomRowNormal[keyCode]
        }
    }
    
    /**
     * Checks if a keycode has any mapping (normal or shifted).
     * 
     * This is useful for determining if the IME should handle a key event
     * or let it pass through to the application.
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
     * Useful for debugging and validation.
     * 
     * @return List of Android keycodes with Myanmar3 mappings
     */
    fun getAllMappedKeyCodes(): List<Int> {
        val allKeys = mutableSetOf<Int>()
        allKeys.addAll(topRowNormal.keys)
        allKeys.addAll(topRowShift.keys)
        allKeys.addAll(homeRowNormal.keys)
        allKeys.addAll(homeRowShift.keys)
        allKeys.addAll(bottomRowNormal.keys)
        allKeys.addAll(bottomRowShift.keys)
        return allKeys.sorted()
    }
    
    /**
     * Gets statistics about the key mappings.
     * Useful for validation and documentation.
     * 
     * @return Map of statistics (total_mappings, unique_keycodes, etc.)
     */
    fun getStatistics(): Map<String, Int> {
        val totalNormal = topRowNormal.size + homeRowNormal.size + bottomRowNormal.size
        val totalShift = topRowShift.size + homeRowShift.size + bottomRowShift.size
        
        return mapOf(
            "total_normal_mappings" to totalNormal,
            "total_shift_mappings" to totalShift,
            "total_mappings" to (totalNormal + totalShift),
            "unique_keycodes" to getAllMappedKeyCodes().size
        )
    }
}
