package com.keyman.engine.pattern

/**
 * Represents a single pattern matching rule in KeyMagic style.
 * 
 * Pattern rules define transformations from input sequences to output sequences.
 * They support variables, context matching, and output generation.
 * 
 * Example patterns:
 * - Simple: `'a' => U1031` (a → ေ)
 * - Context: `consonant + 'a' => $1 + U1031` (က + a → ကေ)
 * - Reorder: `U1031 + consonant => $2 + $1` (ေက → ကေ)
 * 
 * @property lhs Left-hand side pattern to match
 * @property rhs Right-hand side output pattern
 * @property priority Higher priority rules are matched first (default: 0)
 * 
 * @author Myanmar Keyboard Implementation Team
 * @since 3.0.0
 */
data class PatternRule(
    val lhs: String,
    val rhs: String,
    val priority: Int = 0
) {
    /**
     * Checks if this pattern matches the given context.
     * 
     * @param context Current buffer context
     * @return MatchResult containing matched parts, or null if no match
     */
    fun matches(context: MatchContext): MatchResult? {
        // Handle different pattern types
        
        // Pattern type 1: Buffer-based patterns (anything with special chars)
        // - Contains [  ] for character classes
        // - Contains ( ) for capture groups  
        // - Contains + for sequences
        // - Contains U followed by hex for Unicode codes
        if (lhs.contains("[") || lhs.contains("(") || lhs.contains("+") || lhs.contains("U")) {
            return matchBufferPattern(context)
        }
        
        // Pattern type 2: Simple literal (e.g., "'a'")
        val pattern = lhs.trim().removeSurrounding("'", "'")
        
        // Check if last key matches this pattern
        if (context.lastKey == pattern) {
            return MatchResult(
                matched = pattern,
                capturedGroups = listOf(pattern)
            )
        }
        
        return null
    }
    
    /**
     * Matches buffer-based patterns like "U1031 + consonant" or "[က-အ](U102F)U103B".
     */
    private fun matchBufferPattern(context: MatchContext): MatchResult? {
        val buffer = context.buffer
        
        // Pattern 1: U1031 + consonant (pre-base vowel reordering)
        if (lhs.contains("U1031") && lhs.contains("consonant")) {
            if (buffer.length >= 2) {
                val preBase = buffer[buffer.length - 2]
                val consonant = buffer[buffer.length - 1]
                
                if (preBase == '\u1031' && isConsonant(consonant)) {
                    // CRITICAL FIX: Only swap if U1031 is NOT already bound to a previous consonant
                    // If buffer is "...ConstU1031Const", then U1031 belongs to previous Const.
                    // Don't let new Const steal it.
                    var alreadyBound = false
                    if (buffer.length >= 3) {
                        val prevPrev = buffer[buffer.length - 3]
                        // If previous char was Consonant or Medial (U103B..U103E), U1031 is bound to it
                        if (isConsonant(prevPrev) || (prevPrev >= '\u103B' && prevPrev <= '\u103E')) {
                            alreadyBound = true
                        }
                    }
                    
                    if (!alreadyBound) {
                        return MatchResult(
                            matched = buffer.takeLast(2),
                            capturedGroups = listOf(preBase.toString(), consonant.toString())
                        )
                    }
                }
            }
        }
        
        // Pattern 2: Consonant + Vowel + Medial (smart reordering)
        // Example: ([က-အ])(\u102F)\u103B matches က + ု + ှ
        val medialAfterVowelPattern = Regex("\\[([^\\]]+)\\]\\(U([0-9A-Fa-f]{4})\\)U([0-9A-Fa-f]{4})")
        val match = medialAfterVowelPattern.find(lhs)
        if (match != null) {
            val consonantRange = match.groupValues[1]  // e.g., "က-အ"
            val vowelCode = match.groupValues[2].toInt(16)  // e.g., 102F
            val medialCode = match.groupValues[3].toInt(16)  // e.g., 103B
            
            if (buffer.length >= 3) {
                val cons = buffer[buffer.length - 3]
                val vowel = buffer[buffer.length - 2]
                val medial = buffer[buffer.length - 1]
                
                if (isInRange(cons, consonantRange) && 
                    vowel.code == vowelCode && 
                    medial.code == medialCode) {
                    return MatchResult(
                        matched = buffer.takeLast(3),
                        capturedGroups = listOf(cons.toString(), vowel.toString(), medial.toString())
                    )
                }
            }
        }
        
        // Pattern 3: Double character prevention (e.g., "(\u1037)\u1037")
        val doubleCharPattern = Regex("\\(U([0-9A-Fa-f]{4})\\)U([0-9A-Fa-f]{4})")
        val doubleMatch = doubleCharPattern.find(lhs)
        if (doubleMatch != null) {
            val char1Code = doubleMatch.groupValues[1].toInt(16)
            val char2Code = doubleMatch.groupValues[2].toInt(16)
            
            if (char1Code == char2Code && buffer.length >= 2) {
                val prev = buffer[buffer.length - 2]
                val curr = buffer[buffer.length - 1]
                
                if (prev.code == char1Code && curr.code == char2Code) {
                    return MatchResult(
                        matched = buffer.takeLast(2),
                        capturedGroups = listOf(prev.toString())
                    )
                }
            }
        }
        
        // Pattern 4: Character class + Unicode (e.g., "[ခ,ဂ,င,ဒ,ပ,ဝ]U102C")
        val charClassUnicodePattern = Regex("\\[([^\\]]+)\\]U([0-9A-Fa-f]{4})")
        val charClassMatch = charClassUnicodePattern.find(lhs)
        if (charClassMatch != null) {
            val charRange = charClassMatch.groupValues[1]  // e.g., "ခ,ဂ,င,ဒ,ပ,ဝ"
            val unicodeCode = charClassMatch.groupValues[2].toInt(16)  // e.g., 102C
            
            if (buffer.length >= 2) {
                val prevChar = buffer[buffer.length - 2]
                val currChar = buffer[buffer.length - 1]
                
                if (isInRange(prevChar, charRange) && currChar.code == unicodeCode) {
                    return MatchResult(
                        matched = buffer.takeLast(2),
                        capturedGroups = listOf(prevChar.toString(), currChar.toString())
                    )
                }
            }
        }
        
        return null
    }
    
    /**
     * Checks if character is in the given range (e.g., "က-အ").
     */
    private fun isInRange(char: Char, range: String): Boolean {
        if (range.contains("-")) {
            val parts = range.split("-")
            if (parts.size == 2) {
                val start = parts[0].firstOrNull()?.code ?: return false
                val end = parts[1].firstOrNull()?.code ?: return false
                return char.code in start..end
            }
        }
        // Also support comma-separated: "ခ,ဂ,င"
        return range.split(",").any { it.trim().firstOrNull() == char }
    }
    
    /**
     * Checks if character is a Myanmar consonant.
     */
    private fun isConsonant(char: Char): Boolean {
        return char in '\u1000'..'\u1021'
    }
    
    /**
     * Generates output based on matched context.
     * 
     * @param matchResult Result from matches()
     * @return Generated output string
     */
    fun generateOutput(matchResult: MatchResult): String {
        var output = rhs.trim()
        
        // Replace capture group references ($1, $2)
        // Capture groups contain actual characters, not Unicode codes
        for (i in matchResult.capturedGroups.indices) {
            val ref = "$${i + 1}"
            if (output.contains(ref)) {
                output = output.replace(ref, matchResult.capturedGroups[i])
            }
        }
        
        // If output still has Unicode codes after substitution, convert them
        // This handles cases where RHS has literal Unicode codes like U1031
        if (output.contains("U")) {
            // Replace any remaining Unicode codes
            val regex = Regex("U([0-9A-Fa-f]{4})")
            output = regex.replace(output) { matchResult ->
                val hexCode = matchResult.groupValues[1]
                val charCode = hexCode.toInt(16)
                charCode.toChar().toString()
            }
        }
        
        // Return processed output (remove quotes if present)
        return output.removeSurrounding("'", "'")
    }
}

/**
 * Context for pattern matching, containing buffer state.
 */
data class MatchContext(
    val buffer: String,
    val lastKey: String,
    val cursorPosition: Int = buffer.length
)

/**
 * Result of a pattern match.
 */
data class MatchResult(
    val matched: String,
    val capturedGroups: List<String> = emptyList()
)
