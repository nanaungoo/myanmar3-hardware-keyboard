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
        buffer.append(char)
        
        val context = MatchContext(
            buffer = buffer.toString(),
            lastKey = char
        )
        
        // Try to find matching rule (high priority = context-aware)
        for (rule in rules) {
            val matchResult = rule.matches(context)
            if (matchResult != null) {
                val output = rule.generateOutput(matchResult)
                
                // CRITICAL: Replace matched portion with output
                val matchedText = matchResult.matched
                val bufferText = buffer.toString()
                
                if (bufferText.endsWith(matchedText)) {
                    // Remove matched portion and add output
                    val newBuffer = bufferText.substring(0, bufferText.length - matchedText.length) + output
                    buffer.clear()
                    buffer.append(newBuffer)
                } else {
                    // Pattern generated output without match - just use it
                    buffer.clear()
                    buffer.append(output)
                }
                
                return buffer.toString()
            }
        }
        
        // No rule matched - return buffer as-is
        return buffer.toString()
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
