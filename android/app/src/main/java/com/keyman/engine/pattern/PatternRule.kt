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
        
        // Pattern type 1: Buffer-based pattern (e.g., "U1031 + consonant")
        if (lhs.contains("+")) {
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
     * Matches buffer-based patterns like "U1031 + consonant".
     */
    private fun matchBufferPattern(context: MatchContext): MatchResult? {
        val buffer = context.buffer
        
        // Parse pattern: "U1031 + consonant" -> check if buffer ends with ေ + consonant
        if (lhs.contains("U1031") && lhs.contains("consonant")) {
            // Check if buffer is: ေ + any consonant
            if (buffer.length >= 2) {
                val preBase = buffer[buffer.length - 2]
                val consonant = buffer[buffer.length - 1]
                
                if (preBase == '\u1031' && isConsonant(consonant)) {
                    return MatchResult(
                        matched = buffer.takeLast(2),
                        capturedGroups = listOf(preBase.toString(), consonant.toString())
                    )
                }
            }
        }
        
        return null
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
