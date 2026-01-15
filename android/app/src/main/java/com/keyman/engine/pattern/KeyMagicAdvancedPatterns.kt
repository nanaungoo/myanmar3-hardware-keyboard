package com.keyman.engine.pattern

/**
 * KeyMagic Advanced Patterns - 6 Major Improvements
 * 
 * Implements comprehensive Myanmar Unicode handling patterns
 * based on KeyMagic best practices.
 */
object KeyMagicAdvancedPatterns {
    
    /**
     * Gets all advanced pattern rules for Myanmar Unicode.
     * Priority order: prevention > reordering > basic
     */
    fun getRules(): List<PatternRule> {
        val rules = mutableListOf<PatternRule>()
        
        // Priority 200: Double Character Prevention (highest)
        rules.addAll(getDoubleCharPreventionRules())
        
        // Priority 150: Smart Reordering (Medial after Vowel)
        rules.addAll(getSmartReorderingRules())
        
        // Priority 140: Tall AA Auto-selection
        rules.addAll(getTallAASelectionRules())
        
        // Priority 130: Kinzi Logic
        rules.addAll(getKinziRules())
        
        // Priority 120: Dot Below Normalization
        rules.addAll(getDotBelowNormalizationRules())
        
        // Priority 110: Smart Stacking (context-aware f key)
        rules.addAll(getSmartStackingRules())
        
        return rules
    }
    
    /**
     * Improvement #2: Double Diacritic Prevention
     * Priority: 200
     */
    private fun getDoubleCharPreventionRules(): List<PatternRule> {
        return listOf(
            // Prevent double Dot-Below
            PatternRule(
                lhs = "(U1037)U1037",
                rhs = "$1",
                priority = 200
            ),
            // Prevent double Anusvara
            PatternRule(
                lhs = "(U103D)U103D",
                rhs = "$1",
                priority = 200
            ),
            // Prevent double AA/Tall-AA
            PatternRule(
                lhs = "([U102B,U102C])[U102B,U102C]",
                rhs = "$1",
                priority = 200
            )
        )
    }
    
    /**
     * Improvement #1: Smart Reordering (Medial after Vowel)
     * Priority: 150
     */
    private fun getSmartReorderingRules(): List<PatternRule> {
        return listOf(
            // 1. Consonant + E + Medial -> Consonant + Medial + E
            // Logic: ([က-အ])ေ([ျြွှ]) > $1$3ေ
            // Covers Medial Ya (U103B), Ra (U103C), Wa (U103D), Ha (U103E)
            PatternRule(
                lhs = "[က-အ](U1031)([U103B-U103E])",
                rhs = "$1$3$2",
                priority = 150
            ),
            
            // 2. Consonant + Vowel + Medial -> Consonant + Medial + Vowel
            // Logic: ([က-အ])([ိီုူ])([ျြွှ]) > $1$3$2
            // Vowels: I (U102D), II (U102E), U (U102F), UU (U1030)
            // Medials: U103B-U103E
            PatternRule(
                lhs = "[က-အ]([U102D-U1030])([U103B-U103E])",
                rhs = "$1$3$2",
                priority = 150
            ),
            
            // 3. Ha + Wa -> Wa + Ha (Medial ordering preference)
            // Logic: ွှ > ွှ (U103E + U103D -> U103D + U103E)
            PatternRule(
                lhs = "(U103E)U103D",
                rhs = "$2$1",
                priority = 150
            ),
            
            // 4. E + Medial + Vowel -> Medial + E + Vowel
            // Logic: ေ([ျြွှ])([ိီုူ]) > $1ေ$2
            PatternRule(
                lhs = "(U1031)([U103B-U103E])([U102D-U1030])",
                rhs = "$2$1$3",
                priority = 150
            ),
            
            // 5. Asat + Vowel -> Vowel + Asat
            // Logic: ်([ိီုူ]) > $2်
            PatternRule(
                lhs = "(U103A)([U102D-U1030])",
                rhs = "$2$1",
                priority = 150
            ),

            // 6. Dot Below + Vowel Reordering (already partially covered in normalization, generalizing here)
            // Logic: ([က-အ])့([ိီုူေ]) > $1$3့
            PatternRule(
                lhs = "[က-အ](U1037)([U102D-U1031])",
                rhs = "$1$3$2",
                priority = 150
            )
        )
    }

    /**
     * Improvement #3: Automatic Tall AA Selection
     * Priority: 140
     */
    private fun getTallAASelectionRules(): List<PatternRule> {
        return listOf(
            // Tall-AA consonants without medial
            PatternRule(
                lhs = "[ခ,ဂ,င,ဒ,ပ,ဝ]U102C",
                rhs = "$1U102B",  // Replace AA with Tall-AA
                priority = 140
            ),
            // Tall-AA with medial in between
            PatternRule(
                lhs = "[ခ,ဂ,င,ဒ,ပ,ဝ]([U103B-U103E]+)U102C",
                rhs = "$1$2U102B",
                priority = 140
            )
        )
    }
    
    /**
     * Improvement #4: Kinzi Logic
     * Priority: 130
     */
    private fun getKinziRules(): List<PatternRule> {
        return listOf(
            // Nga + Asat + Virama + Consonant → Consonant + Kinzi
            PatternRule(
                lhs = "(U1004U103A)U1039([က-ပ,ဗ-အ])",
                rhs = "$3$1U1039",
                priority = 130
            )
        )
    }
    
    /**
     * Improvement #6: Dot Below Normalization
     * Priority: 120
     */
    private fun getDotBelowNormalizationRules(): List<PatternRule> {
        return listOf(
            // Dot Below before Vowel → Vowel before Dot Below
            PatternRule(
                lhs = "(U1037)([U102B,U102C,U1031,U102F,U1030])",
                rhs = "$2$1",
                priority = 120
            )
        )
    }
    
    /**
     * Improvement #5: Smart Stacking (context-aware f key)
     * Priority: 110
     */
    private fun getSmartStackingRules(): List<PatternRule> {
        return listOf(
            // Consonant + f → Add Virama for stacking
            PatternRule(
                lhs = "[က-ဏ,တ-ပ,ဗ-သ]'f'",
                rhs = "$1U1039",
                priority = 110
            )
        )
    }
}
