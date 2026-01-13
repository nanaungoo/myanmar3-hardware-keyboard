package com.keyman.engine.hardware

/**
 * Handles Myanmar combining marks, diacritics, and proper Unicode sequencing.
 * 
 * Myanmar script uses a complex system of combining marks that attach to base
 * consonants to modify their pronunciation. This handler identifies and processes
 * these marks according to Unicode standards.
 * 
 * Key Concepts:
 * - **Post-base marks**: Stored after the base consonant, may render above/below
 * - **Pre-base marks**: Stored after base but render visually before (e.g., ေ)
 * - **Virama**: U+1039, used for consonant stacking and medial formation
 * - **Proper storage order**: Ensures consistent text processing and searching
 * 
 * Unicode Reference: Myanmar block U+1000–U+109F
 * 
 * @author Myanmar Keyboard Implementation Team
 * @since 1.0.0
 */
class CombiningMarkHandler {
    
    // ========================================================================
    // Combining Mark Categories
    // ========================================================================
    
    /**
     * Post-base combining marks that appear after the base consonant in storage.
     * These marks may render above, below, or around the consonant.
     */
    private val postBaseCombiningMarks = setOf(
        "\u102B",  // ါ - VOWEL SIGN TALL AA
        "\u102C",  // ာ - VOWEL SIGN AA
        "\u102D",  // ိ - VOWEL SIGN I
        "\u102E",  // ီ - VOWEL SIGN II
        "\u102F",  // ု - VOWEL SIGN U
        "\u1030",  // ူ - VOWEL SIGN UU
        "\u1032",  // ဲ - VOWEL SIGN AI
        "\u1036",  // ံ - SIGN ANUSVARA (nasal sound)
        "\u1037",  // ့ - SIGN DOT BELOW (killer)
        "\u1038",  // း - SIGN VISARGA (aspiration)
        "\u103A",  // ့ - SIGN ASAT (visible killer)
        "\u103B",  // ှ - CONSONANT SIGN MEDIAL YA
        "\u103C",  // ျ - CONSONANT SIGN MEDIAL RA
        "\u103D",  // ွ - CONSONANT SIGN MEDIAL WA
        "\u103E",  // ှ - CONSONANT SIGN MEDIAL HA
        "\u1039"   // ် - SIGN VIRAMA (invisible)
    )
    
    /**
     * Pre-base combining marks that render visually before the base consonant
     * but are stored after it in Unicode.
     * 
     * These marks require special handling during rendering to ensure proper
     * visual display order.
     */
    private val preBaseCombiningMarks = setOf(
        "\u1031",  // ေ - VOWEL SIGN E (visual-order exception)
        "\u103C"   // ြ - Can act as pre-base medial RA in certain contexts
    )
    
    /**
     * The virama character used for consonant stacking and medial formation.
     * When placed between two consonants, creates a consonant cluster.
     * 
     * Example: ပ + ် + ဘ = ပ္ဘ (stacked consonants)
     */
    private val virama = "\u1039"
    
    /**
     * The asat character, a visible killer diacritic.
     * Suppresses the inherent vowel of a consonant.
     */
    private val asat = "\u103A"
    
    // ========================================================================
    // Character Classification
    // ========================================================================
    
    /**
     * Myanmar consonants (base characters that can receive combining marks).
     * Range: U+1000 to U+1021
     */
    private val consonantRange = '\u1000'..'\u1021'
    
    /**
     * Independent vowels that don't combine with consonants.
     * Range: U+1023 to U+1027, U+1029, U+102A
     */
    private val independentVowels = setOf(
        "\u1023", "\u1024", "\u1025", "\u1026", 
        "\u1027", "\u1029", "\u102A"
    )
    
    // ========================================================================
    // Public API Methods
    // ========================================================================
    
    /**
     * Checks if a character is a combining mark (diacritic).
     * 
     * Combining marks must be attached to a base character and cannot
     * stand alone in valid Myanmar text.
     * 
     * @param char The character to check (as a String for multi-byte support)
     * @return true if the character is a combining mark
     * 
     * @example
     * ```kotlin
     * val handler = CombiningMarkHandler()
     * handler.isCombiningMark("ိ")  // true
     * handler.isCombiningMark("က")  // false
     * ```
     */
    fun isCombiningMark(char: String): Boolean {
        return postBaseCombiningMarks.contains(char) || 
               preBaseCombiningMarks.contains(char) ||
               char == virama ||
               char == asat
    }
    
    /**
     * Checks if a character is a pre-base combining mark.
     * 
     * Pre-base marks are stored after the base consonant but rendered
     * visually before it, requiring special handling.
     * 
     * @param char The character to check
     * @return true if the character is a pre-base combining mark
     */
    fun isPreBaseMark(char: String): Boolean {
        return preBaseCombiningMarks.contains(char)
    }
    
    /**
     * Checks if a character is a Myanmar consonant (base character).
     * 
     * @param char The character to check
     * @return true if the character is a Myanmar consonant
     */
    fun isConsonant(char: String): Boolean {
        return char.length == 1 && char[0] in consonantRange
    }
    
    /**
     * Checks if a character is an independent vowel.
     * 
     * Independent vowels stand alone and don't combine with consonants.
     * 
     * @param char The character to check
     * @return true if the character is an independent vowel
     */
    fun isIndependentVowel(char: String): Boolean {
        return independentVowels.contains(char)
    }
    
    /**
     * Processes a combining mark with proper sequencing logic.
     * 
     * For hardware keyboard input, combining marks are typically emitted
     * directly. The operating system's text rendering engine handles the
     * actual visual positioning and composition.
     * 
     * Future enhancements could include:
     * - Context-aware validation
     * - Automatic reordering for proper storage order
     * - Prevention of invalid mark combinations
     * 
     * @param mark The combining mark to process
     * @return The processed combining mark (currently unchanged)
     */
    fun process(mark: String): String {
        // For hardware keyboard, emit marks directly
        // The rendering engine handles visual positioning
        return mark
    }
    
    /**
     * Validates if a combining mark can be applied to a base character.
     * 
     * This prevents invalid sequences like:
     * - Combining marks on non-consonants
     * - Invalid stacking of certain marks
     * 
     * @param baseChar The base character
     * @param mark The combining mark to apply
     * @return true if the combination is valid
     * 
     * @example
     * ```kotlin
     * val handler = CombiningMarkHandler()
     * handler.canApplyCombiningMark("က", "ိ")  // true (valid)
     * handler.canApplyCombiningMark("1", "ိ")  // false (invalid)
     * ```
     */
    fun canApplyCombiningMark(baseChar: String, mark: String): Boolean {
        // Check if base is a valid Myanmar consonant
        val isValidBase = isConsonant(baseChar) || isIndependentVowel(baseChar)
        
        if (!isValidBase) {
            return false
        }
        
        // Check if mark is actually a combining mark
        if (!isCombiningMark(mark)) {
            return false
        }
        
        // Virama can follow consonants but not independent vowels
        if (mark == virama && isIndependentVowel(baseChar)) {
            return false
        }
        
        // All other combining marks are generally valid
        return true
    }
    
    /**
     * Gets the storage order priority for a combining mark.
     * 
     * In Myanmar script, when multiple combining marks are applied,
     * they must be stored in a specific order for proper rendering.
     * 
     * Lower numbers = stored first
     * 
     * Approximate order:
     * 1. Virama/Kinzi
     * 2. Medials (ya, ra, wa, ha)
     * 3. Main vowel signs
     * 4. Tone marks and anusvara
     * 
     * @param mark The combining mark
     * @return Priority value (1-10), or 0 if not a combining mark
     */
    fun getStorageOrderPriority(mark: String): Int {
        return when (mark) {
            // Virama and Asat (highest priority)
            "\u1039", "\u103A" -> 1
            
            // Medials
            "\u103B", "\u103C", "\u103D", "\u103E" -> 2
            
            // Vowel signs
            "\u102B", "\u102C", "\u102D", "\u102E", 
            "\u102F", "\u1030", "\u1031", "\u1032" -> 3
            
            // Tone marks and anusvara
            "\u1036", "\u1037", "\u1038" -> 4
            
            else -> 0
        }
    }
    
    /**
     * Validates a complete Myanmar syllable for proper combining mark usage.
     * 
     * This can be used for input validation or text correction.
     * 
     * @param syllable The Myanmar syllable to validate
     * @return true if the syllable follows proper combining mark rules
     */
    fun isValidSyllable(syllable: String): Boolean {
        if (syllable.isEmpty()) return false
        
        // First character should be a base (consonant or independent vowel)
        val firstChar = syllable[0].toString()
        if (!isConsonant(firstChar) && !isIndependentVowel(firstChar)) {
            return false
        }
        
        // All subsequent characters should be combining marks
        // (or consonants if following virama)
        var expectingConsonant = false
        
        for (i in 1 until syllable.length) {
            val char = syllable[i].toString()
            
            if (expectingConsonant) {
                if (!isConsonant(char)) return false
                expectingConsonant = false
            } else {
                if (char == virama) {
                    expectingConsonant = true
                } else if (!isCombiningMark(char)) {
                    return false
                }
            }
        }
        
        // Shouldn't end expecting a consonant
        return !expectingConsonant
    }
    
    /**
     * Returns a description of a combining mark for debugging.
     * 
     * @param mark The combining mark
     * @return Human-readable description
     */
    fun getMarkDescription(mark: String): String {
        return when (mark) {
            "\u102B" -> "VOWEL SIGN TALL AA (ါ)"
            "\u102C" -> "VOWEL SIGN AA (ာ)"
            "\u102D" -> "VOWEL SIGN I (ိ)"
            "\u102E" -> "VOWEL SIGN II (ီ)"
            "\u102F" -> "VOWEL SIGN U (ု)"
            "\u1030" -> "VOWEL SIGN UU (ူ)"
            "\u1031" -> "VOWEL SIGN E (ေ)"
            "\u1032" -> "VOWEL SIGN AI (ဲ)"
            "\u1036" -> "SIGN ANUSVARA (ံ)"
            "\u1037" -> "SIGN DOT BELOW (့)"
            "\u1038" -> "SIGN VISARGA (း)"
            "\u1039" -> "SIGN VIRAMA (်)"
            "\u103A" -> "SIGN ASAT (့)"
            "\u103B" -> "CONSONANT SIGN MEDIAL YA (ှ)"
            "\u103C" -> "CONSONANT SIGN MEDIAL RA (ျ/ြ)"
            "\u103D" -> "CONSONANT SIGN MEDIAL WA (ွ)"
            "\u103E" -> "CONSONANT SIGN MEDIAL HA (ှ)"
            else -> "Unknown mark"
        }
    }
}
