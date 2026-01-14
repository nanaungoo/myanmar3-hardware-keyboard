package com.keyman.engine.pattern

/**
 * Parses KeyMagic-style pattern syntax into executable pattern rules.
 * 
 * Supports:
 * - Literals: `'a'`, `"text"`
 * - Unicode codes: `U1000`, `U+1000`
 * - Variables: `$vowels`
 * - Any match: `*`
 * - References: `$1`, `$2`
 * - Operators: `+` for concatenation
 * 
 * Example syntax:
 * ```
 * 'a' => U1031
 * consonant + 'a' => $1 + U1031
 * U1031 + consonant => $2 + $1
 * ```
 * 
 * @author Myanmar Keyboard Implementation Team
 * @since 3.0.0
 */
class PatternParser {
    
    private val variables = mutableMapOf<String, String>()
    
    /**
     * Defines a variable for use in patterns.
     * 
     * @param name Variable name (without $)
     * @param value Variable value
     */
    fun defineVariable(name: String, value: String) {
        variables[name] = value
    }
    
    /**
     * Parses a pattern rule string.
     * 
     * @param ruleString Pattern in format "lhs => rhs"
     * @param priority Priority level (default: 0)
     * @return Parsed PatternRule
     */
    fun parseRule(ruleString: String, priority: Int = 0): PatternRule {
        val parts = ruleString.split("=>").map { it.trim() }
        require(parts.size == 2) { "Invalid rule format: $ruleString" }
        
        val lhs = parts[0]
        val rhs = parts[1]
        
        return PatternRule(lhs, rhs, priority)
    }
    
    /**
     * Parses multiple rules from a list of strings.
     */
    fun parseRules(ruleStrings: List<String>): List<PatternRule> {
        return ruleStrings.mapIndexed { index, rule ->
            parseRule(rule, priority = ruleStrings.size - index)
        }
    }
    
    /**
     * Resolves a pattern element to its actual value.
     * 
     * Handles:
     * - Literals: 'a' -> a
     * - Unicode: U1000 -> \u1000
     * - Variables: $vowels -> aeiou
     * 
     * @param element Pattern element
     * @return Resolved value
     */
    fun resolveElement(element: String): String {
        return when {
            // String literal
            element.startsWith("'") && element.endsWith("'") -> 
                element.substring(1, element.length - 1)
            
            // Unicode code
            element.startsWith("U") -> {
                val code = element.substring(1).replace("+", "")
                code.toInt(16).toChar().toString()
            }
            
            // Variable reference
            element.startsWith("$") && !element[1].isDigit() -> {
                val varName = element.substring(1)
                variables[varName] ?: element
            }
            
            // Reference ($1, $2)
            element.startsWith("$") && element[1].isDigit() -> element
            
            // Keyword (consonant, vowel, etc.)
            else -> element
        }
    }
}
