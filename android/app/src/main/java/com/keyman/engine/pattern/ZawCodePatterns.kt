package com.keyman.engine.pattern

/**
 * ZawCode pattern rules for Myanmar keyboard.
 * 
 * Defines all transformation patterns for ZawCode layout,
 * including basic mappings, reordering rules, and context-aware transformations.
 * 
 * @author Myanmar Keyboard Implementation Team
 * @since 3.0.0
 */
object ZawCodePatterns {
    
    /**
     * Gets all ZawCode pattern rules, sorted by priority.
     * Higher priority rules are checked first.
     */
    fun getRules(): List<PatternRule> {
        val rules = mutableListOf<PatternRule>()
        
        // Priority 100: Context-aware reordering rules
        rules.addAll(getReorderingRules())
        
        // Priority 50: Compound vowel rules
        rules.addAll(getCompoundVowelRules())
        
        // Priority 10: Basic character mappings
        rules.addAll(getBasicMappings())
        
        return rules
    }
    
    /**
     * Reordering rules for pre-base vowels and consonants.
     * Priority: 100
     */
    private fun getReorderingRules(): List<PatternRule> {
        return listOf(
            // Pre-base vowel reordering: ေ + consonant -> consonant + ေ
            // $1 = ေ (pre-base vowel), $2 = consonant
            PatternRule(
                lhs = "U1031 + consonant",
                rhs = "$2$1",  // Swap: consonant + pre-base vowel
                priority = 100
            )
        )
    }
    
    /**
     * Compound vowel formation rules.
     * Priority: 50
     */
    private fun getCompoundVowelRules(): List<PatternRule> {
        return listOf(
            // ော formation: consonant + ေ + ာ
            PatternRule(
                lhs = "consonant + U1031 + 'm'",
                rhs = "$1 + U1031 + U102C",
                priority = 50
            )
        )
    }
    
    /**
     * Basic key-to-character mappings.
     * Priority: 10
     */
    private fun getBasicMappings(): List<PatternRule> {
        return listOf(
            // Vowels
            PatternRule("'a'", "U1031", 10),  // ေ
            PatternRule("'d'", "U102D", 10),  // ိ
            PatternRule("'D'", "U102E", 10),  // ီ (Shift+D)
            PatternRule("'k'", "U102F", 10),  // ု
            PatternRule("'l'", "U1030", 10),  // ူ
            PatternRule("'m'", "U102C", 10),  // ာ
            PatternRule("'g'", "U102B", 10),  // ါ
            
            // Consonants - Top row
            PatternRule("'q'", "U1006", 10),  // ဆ
            PatternRule("'w'", "U1010", 10),  // တ
            PatternRule("'e'", "U1014", 10),  // န
            PatternRule("'r'", "U1019", 10),  // မ
            PatternRule("'t'", "U1021", 10),  // အ
            PatternRule("'y'", "U1015", 10),  // ပ
            PatternRule("'u'", "U1000", 10),  // က
            PatternRule("'i'", "U1004", 10),  // င
            PatternRule("'o'", "U101E", 10),  // သ
            PatternRule("'p'", "U1005", 10),  // စ
            
            // Consonants - Home row
            
            // Consonants - Bottom row
            PatternRule("'z'", "U1016", 10),  // ဖ
            PatternRule("'x'", "U1011", 10),  // ထ
            PatternRule("'c'", "U1001", 10),  // ခ
            PatternRule("'v'", "U101C", 10),  // လ
            PatternRule("'b'", "U1018", 10),  // ဘ
            PatternRule("'n'", "U100A", 10),  // ည
            
            // Tone marks
            PatternRule("'f'", "U103A", 10),  // ် (asat)
            PatternRule("'h'", "U1037", 10),  // ့ (dot below)
            PatternRule("';'", "U1038", 10),  // း (visarga)
            
            // Medials
            PatternRule("'s'", "U103B", 10),  // ှ (medial ya)
            PatternRule("'j'", "U103C", 10),  // ြ (medial ra)
        )
    }
}
