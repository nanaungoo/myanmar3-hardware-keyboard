package com.keyman.engine.pattern

/**
 * Core pattern matching engine for Myanmar keyboard input.
 * 
 * Processes input using KeyMagic-style pattern rules to transform
 * typed sequences into properly formatted Myanmar Unicode.
 * 
 * Key features:
 * - Pattern-based transformation
 * - Context-aware matching
 * - Priority-based rule selection
 * - Buffer management
 * 
 * @author Myanmar Keyboard Implementation Team
 * @since 3.0.0
 */
class PatternMatcher {
    
    private val rules = mutableListOf<PatternRule>()
    private val buffer = StringBuilder()
    
    /**
     * Adds a pattern rule to the matcher.
     * Rules are sorted by priority (highest first).
     */
    fun addRule(rule: PatternRule) {
        rules.add(rule)
        rules.sortByDescending { it.priority }
    }
    
    /**
     * Adds multiple pattern rules.
     */
    fun addRules(ruleList: List<PatternRule>) {
        rules.addAll(ruleList)
        rules.sortByDescending { it.priority }
    }
    
    /**
     * Processes a new character input.
     * 
     * Algorithm:
     * 1. Add character to buffer
     * 2. Build match context with full buffer
     * 3. Try rules in priority order (context-aware patterns first)
     * 4. Apply first matching rule and transform buffer
     * 5. Return transformed buffer
     * 
     * @param char Input character
     * @return Transformed output string
     */
    fun processInput(char: String): String {
        // LOOKAHEAD STRATEGY:
        // Advanced patterns (Priority > 10) expect Unicode characters, not raw keys.
        // So we tentatively map the input key to see what it would be (e.g., 'h' -> '့').
        
        // 1. Find basic mapping (Priority 10) for this char
        var mappedChar = char
        for (rule in rules) {
            if (rule.priority <= 10 && rule.lhs == "'$char'") {
                // Found basic mapping - convert RHS to char
                mappedChar = convertRhsToChar(rule.rhs)
                break
            }
        }
        
        // 2. Try Advanced Rules (Priority > 10) with mapped char
        val tempBufferStr = buffer.toString() + mappedChar
        val context = MatchContext(tempBufferStr, char)
        
        for (rule in rules) {
            if (rule.priority <= 10) continue // Skip basic rules in this phase
            
            val matchResult = rule.matches(context)
            if (matchResult != null) {
                val output = rule.generateOutput(matchResult)
                
                // Advanced Rule Matched!
                // Update buffer: remove matched portion from tempBuffer, append output
                val matchedText = matchResult.matched
                if (tempBufferStr.endsWith(matchedText)) {
                    val prefix = tempBufferStr.substring(0, tempBufferStr.length - matchedText.length)
                    buffer.clear()
                    buffer.append(prefix + output)
                    return buffer.toString()
                }
            }
        }
        
        // 3. Fallback: Standard Simple Matching
        // If no advanced rule matched, just append raw char and let basic rules handle it
        buffer.append(char)
        val basicContext = MatchContext(buffer.toString(), char)
        
        for (rule in rules) {
            // Only strictly needed for basic rules now, but safe to check all
            val matchResult = rule.matches(basicContext)
            
            if (matchResult != null) {
                val output = rule.generateOutput(matchResult)
                
                // Replace matched portion
                val matchedText = matchResult.matched
                val bufferText = buffer.toString()
                
                if (bufferText.endsWith(matchedText)) {
                    val newBuffer = bufferText.substring(0, bufferText.length - matchedText.length) + output
                    buffer.clear()
                    buffer.append(newBuffer)
                    return buffer.toString()
                }
            }
        }
        
        return buffer.toString()
    }
    
    // Helper to convert RHS (e.g., "U1037") to string
    private fun convertRhsToChar(rhs: String): String {
        val output = rhs.trim()
        if (output.startsWith("U") && output.length == 5) { // Simple Uxxxx
            try {
                val hexCode = output.substring(1)
                val charCode = hexCode.toInt(16)
                return charCode.toChar().toString()
            } catch (e: Exception) { /* ignore */ }
        }
        return output.removeSurrounding("'", "'")
    }
    
    /**
     * Gets current buffer content.
     */
    fun getBuffer(): String = buffer.toString()
    
    /**
     * Clears the buffer.
     */
    fun clear() {
        buffer.clear()
    }
    
    /**
     * Handles backspace by removing last character.
     */
    fun handleBackspace(): String {
        if (buffer.isNotEmpty()) {
            buffer.deleteCharAt(buffer.length - 1)
        }
        return buffer.toString()
    }
}
