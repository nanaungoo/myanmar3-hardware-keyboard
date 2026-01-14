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
        // Simple implementation for now - exact string match
        val pattern = lhs.replace("'", "").trim()
        
        // Check if buffer ends with this pattern
        if (context.buffer.endsWith(pattern)) {
            return MatchResult(
                matched = pattern,
                capturedGroups = listOf(pattern)
            )
        }
        
        return null
    }
    
    /**
     * Generates output based on matched context.
     * 
     * @param matchResult Result from matches()
     * @return Generated output string
     */
    fun generateOutput(matchResult: MatchResult): String {
        // Simple implementation - return RHS with quotes removed
        return rhs.replace("'", "").replace("U", "\\u").trim()
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
