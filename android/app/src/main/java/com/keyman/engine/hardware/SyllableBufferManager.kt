package com.keyman.engine.hardware

/**
 * Manages syllable composition and Unicode reordering for Myanmar script.
 * 
 * Myanmar script requires characters to be stored in a specific Unicode order,
 * which differs from the typing order for pre-base marks. This manager:
 * 
 * 1. Buffers characters as they are typed
 * 2. Detects syllable boundaries
 * 3. Reorders characters to correct Unicode storage order
 * 4. Emits properly-ordered complete syllables
 * 
 * Key reordering rules:
 * - Pre-base marks (ေ ြ) must be stored AFTER consonant but render BEFORE
 * - Medials follow specific order: ya, ra, wa, ha
 * - Virama triggers consonant stacking
 * 
 * @author Myanmar Keyboard Implementation Team
 * @since 2.1.0
 */
class SyllableBufferManager {
    
    // ========================================================================
    // Properties
    // ========================================================================
    
    private val buffer = StringBuilder()
    private val combiningMarkHandler = CombiningMarkHandler()
    private var expectingStackedConsonant = false
    
    // ========================================================================
    // Buffer Actions
    // ========================================================================
    
    /**
     * Represents the action to take after adding a character.
     */
    sealed class BufferAction {
        /** Continue composing - don't emit yet */
        object Continue : BufferAction()
        
        /** Emit the completed syllable and start new buffer */
        data class Emit(val syllable: String) : BufferAction()
        
        /** Emit completed syllable and start new one with this character */
        data class EmitAndStart(val completed: String, val newStart: String) : BufferAction()
    }
    
    // ========================================================================
    // Public API
    // ========================================================================
    
    /**
     * Adds a character to the buffer and determines the next action.
     * 
     * @param char The character to add
     * @return BufferAction indicating what to do next
     */
    fun addCharacter(char: String): BufferAction {
        // Handle space and punctuation - finalize current syllable
        if (char == " " || isPunctuation(char)) {
            val completed = finalizeSyllable()
            buffer.clear()
            return if (completed.isNotEmpty()) {
                BufferAction.EmitAndStart(completed, char)
            } else {
                buffer.append(char)
                BufferAction.Continue
            }
        }
        
        // LOOK-AHEAD BUFFER: If user types pre-base vowel FIRST (Zawgyi habit)
        // Hold it until consonant arrives, then reorder properly
        if (buffer.isEmpty() && isPreBaseMark(char)) {
            // Buffer is empty and user typed vowel first
            // Add to buffer and show as composing (waiting for consonant)
            buffer.append(char)
            return BufferAction.Continue
        }
        
        // If buffer has pre-base vowel and user types consonant
        // This triggers the reordering
        if (buffer.isNotEmpty() && 
            combiningMarkHandler.isConsonant(char) &&
            buffer.toString().all { isPreBaseMark(it.toString()) || it == ' ' }) {
            // Buffer contains only pre-base marks, now consonant arrived
            // Add consonant and let reorder handle it
            buffer.append(char)
            return BufferAction.Continue
        }
        
        // Check if this starts a new syllable
        if (isNewSyllableStart(char)) {
            val completed = finalizeSyllable()
            buffer.clear()
            buffer.append(char)  // Add new character to buffer
            
            return if (completed.isNotEmpty()) {
                // Don't send newStart - character is already in buffer
                BufferAction.Emit(completed)
            } else {
                BufferAction.Continue
            }
        }
        
        // Handle virama - expect stacked consonant next
        if (char == "\u1039") {  // VIRAMA
            expectingStackedConsonant = true
        } else if (expectingStackedConsonant && combiningMarkHandler.isConsonant(char)) {
            expectingStackedConsonant = false
        }
        
        // Add to buffer
        buffer.append(char)
        return BufferAction.Continue
    }
    
    /**
     * Finalizes the current syllable with proper Unicode reordering.
     * 
     * @return The properly-ordered syllable
     */
    fun finalizeSyllable(): String {
        if (buffer.isEmpty()) return ""
        
        val syllable = buffer.toString()
        return reorderSyllable(syllable)
    }
    
    /**
     * Gets the current buffer content (for composing text display).
     * 
     * @return Current buffer reordered for display
     */
    fun getCurrentBuffer(): String {
        if (buffer.isEmpty()) return ""
        return reorderSyllable(buffer.toString())
    }
    
    /**
     * Clears the buffer (e.g., after backspace deletes entire syllable).
     */
    fun clear() {
        buffer.clear()
        expectingStackedConsonant = false
    }
    
    /**
     * Handles backspace by removing last character from buffer.
     * 
     * @return BufferAction indicating what to do
     */
    fun handleBackspace(): BufferAction {
        if (buffer.isEmpty()) {
            return BufferAction.Continue
        }
        
        buffer.deleteCharAt(buffer.length - 1)
        
        // Reset virama expectation if we deleted the virama
        if (buffer.isEmpty() || !buffer.toString().contains("\u1039")) {
            expectingStackedConsonant = false
        }
        
        return BufferAction.Continue
    }
    
    // ========================================================================
    // Syllable Boundary Detection
    // ========================================================================
    
    /**
     * Determines if a character starts a new syllable.
     * 
     * @param char The character to check
     * @return true if this character starts a new syllable
     */
    private fun isNewSyllableStart(char: String): Boolean {
        if (buffer.isEmpty()) return false
        if (expectingStackedConsonant) return false
        
        val currentSyllable = buffer.toString()
        
        return when {
            // NGA-THUT EXCEPTION: င + ် is final consonant, NOT new syllable
            combiningMarkHandler.isConsonant(char) && 
            char == "\u1004" && // င
            hasConsonant(currentSyllable) -> false  // Don't break - might be final င်
            
            // New consonant after complete syllable
            combiningMarkHandler.isConsonant(char) && 
            hasConsonant(currentSyllable) -> true
            
            // Independent vowel always starts new syllable
            combiningMarkHandler.isIndependentVowel(char) -> true
            
            // Consonant after independent vowel
            combiningMarkHandler.isConsonant(char) &&
            currentSyllable.any { combiningMarkHandler.isIndependentVowel(it.toString()) } -> true
            
            else -> false
        }
    }
    
    /**
     * Checks if syllable contains a consonant.
     */
    private fun hasConsonant(syllable: String): Boolean {
        return syllable.any { combiningMarkHandler.isConsonant(it.toString()) }
    }
    
    /**
     * Checks if character is Myanmar punctuation.
     */
    private fun isPunctuation(char: String): Boolean {
        return char in setOf("\u104A", "\u104B", ".", ",", "!", "?", ";", ":")
    }
    
    // ========================================================================
    // Unicode Reordering Logic
    // ========================================================================
    
    /**
     * Reorders a syllable to correct Unicode storage order.
     * 
     * Correct order:
     * 1. Base consonant
     * 2. Virama and kinzi
     * 3. Medials (ya, ra, wa, ha in that order)
     * 4. Vowel signs
     * 5. Tone marks (anusvara, dot below, visarga)
     * 
     * Special case: Pre-base marks (ေ ြ) are stored AFTER consonant
     * but the font rendering engine displays them BEFORE
     * 
     * @param syllable The syllable to reorder
     * @return Properly-ordered syllable
     */
    private fun reorderSyllable(syllable: String): String {
        if (syllable.length <= 1) return syllable
        
        val chars = syllable.map { it.toString() }
        
        // Separate components
        val consonants = mutableListOf<String>()
        val preBaseMarks = mutableListOf<String>()
        val medials = mutableListOf<String>()
        val vowels = mutableListOf<String>()
        val toneMarks = mutableListOf<String>()
        val viramas = mutableListOf<String>()
        val others = mutableListOf<String>()
        
        for (char in chars) {
            when {
                combiningMarkHandler.isConsonant(char) -> consonants.add(char)
                isPreBaseMark(char) -> preBaseMarks.add(char)
                isMedial(char) -> medials.add(char)
                isVowelSign(char) -> vowels.add(char)
                isToneMark(char) -> toneMarks.add(char)
                char == "\u1039" -> viramas.add(char)  // VIRAMA
                else -> others.add(char)
            }
        }
        
        // Build in correct Unicode storage order
        return buildString {
            // 1. Base consonant
            if (consonants.isNotEmpty()) {
                append(consonants[0])
            }
            
            // 2. Virama and stacked consonants
            var stackedConsonantCount = 0
            for (i in viramas.indices) {
                append(viramas[i])
                if (i + 1 < consonants.size) {
                    append(consonants[i + 1])
                    stackedConsonantCount++
                }
            }
            
            // 3. Medials in order: ra, ya, wa, ha
            append(sortMedials(medials).joinToString(""))
            
            // 4. Vowel signs (pre-base BEFORE other vowels for ော combination)
            append(preBaseMarks.joinToString(""))  // ေ FIRST
            append(vowels.joinToString(""))         // ာ SECOND
            
            // 5. Final consonants (like င in မောင်)
            // MUST come BEFORE tone marks so င် is in correct order
            val finalConsonantStartIndex = 1 + stackedConsonantCount
            for (i in finalConsonantStartIndex until consonants.size) {
                append(consonants[i])
            }
            
            // 6. Tone marks (including ် for final consonants)
            append(toneMarks.joinToString(""))
            
            // 6. Others
            append(others.joinToString(""))
        }
    }
    
    /**
     * Sorts medials in correct Unicode order.
     */
    private fun sortMedials(medials: List<String>): List<String> {
        val order = mapOf(
            "\u103C" to 1,  // MEDIAL RA (ြ)
            "\u103B" to 2,  // MEDIAL YA (ျ)
            "\u103D" to 3,  // MEDIAL WA (ွ)
            "\u103E" to 4   // MEDIAL HA (ှ)
        )
        
        return medials.sortedBy { order[it] ?: 999 }
    }
    
    // ========================================================================
    // Character Classification Helpers
    // ========================================================================
    
    private fun isPreBaseMark(char: String): Boolean {
        return char in setOf(
            "\u1031",  // ေ - VOWEL SIGN E (pre-base)
            "\u103C"   // ြ - MEDIAL RA (can be pre-base)
        )
    }
    
    private fun isMedial(char: String): Boolean {
        return char in setOf(
            "\u103B",  // ျ - MEDIAL YA
            "\u103C",  // ြ - MEDIAL RA
            "\u103D",  // ွ - MEDIAL WA
            "\u103E"   // ှ - MEDIAL HA
        )
    }
    
    private fun isVowelSign(char: String): Boolean {
        return char in setOf(
            "\u102B",  // ါ - VOWEL SIGN TALL AA
            "\u102C",  // ာ - VOWEL SIGN AA
            "\u102D",  // ိ - VOWEL SIGN I
            "\u102E",  // ီ - VOWEL SIGN II
            "\u102F",  // ု - VOWEL SIGN U
            "\u1030",  // ူ - VOWEL SIGN UU
            "\u1032"   // ဲ - VOWEL SIGN AI
        )
    }
    
    private fun isToneMark(char: String): Boolean {
        return char in setOf(
            "\u1036",  // ံ - SIGN ANUSVARA
            "\u1037",  // ့ - SIGN DOT BELOW
            "\u1038",  // း - SIGN VISARGA
            "\u103A"   // ် - SIGN ASAT
        )
    }
}
