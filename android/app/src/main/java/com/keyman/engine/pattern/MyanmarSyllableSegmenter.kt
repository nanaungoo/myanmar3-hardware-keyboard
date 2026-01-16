package com.keyman.engine.pattern

import android.view.inputmethod.InputConnection

class MyanmarSyllableSegmenter {

    private val pendingMarks = mutableListOf<Char>()
    private val currentSyllable = StringBuilder()

    fun handleInput(c: Char, ic: InputConnection) {
        val code = c.code
        
        when {
            // 1. Floating Marks (E/Ra) Check
            // IF we are at the start of a syllable (No Anchor Consonant yet), buffer keys.
            // "Floating State": Store in pending, do NOT output.
            (code == 0x1031 || code == 0x103C) && !hasAnchor() -> {
                 if (!pendingMarks.contains(c)) pendingMarks.add(c)
            }

            // 2. Consonant Anchor Logic
            // A new Consonant becomes the Anchor.
            isMyanmarConsonant(code) && !isStacking() -> {
                commitFullSyllable(ic) // Finish previous
                
                currentSyllable.append(c)
                
                // Attach Pending Marks (E/Ra typed BEFORE this consonant)
                // Deduplication logic: If syllable already has E? (Unlikely on fresh consonant).
                pendingMarks.forEach { 
                    currentSyllable.append(it)
                }
                pendingMarks.clear()
            }

            // 3. Word Break
            code == ' '.code || isPunctuation(code) || code == '\n'.code -> {
                commitFullSyllable(ic)
                ic.commitText(c.toString(), 1)
                // Explicitly clear pending on Word Break to prevent 'jumping' over spaces
                pendingMarks.clear()
            }

            else -> {
                // Post-Typing Support / Standard Append
                // E/Ra typed AFTER anchor -> append directly.
                // Deduplication: Check if builder already contains char?
                if (!containsChar(currentSyllable, c)) {
                    currentSyllable.append(c)
                }
            }
        }

        // Visual Feedback
        updateComposingText(ic)
    }

    private fun updateComposingText(ic: InputConnection) {
        // If currentSyllable is empty (only pending marks), show NOTHING.
        // "do NOT output anything"
        if (currentSyllable.isEmpty()) return
        
        val sorted = sortSyllable(currentSyllable.toString())
        ic.setComposingText(sorted, 1)
    }

    fun commitFullSyllable(ic: InputConnection) {
        if (currentSyllable.isNotEmpty()) {
            ic.commitText(sortSyllable(currentSyllable.toString()), 1)
            ic.finishComposingText()
            currentSyllable.setLength(0)
        }
    }
    
    fun handleBackspace(ic: InputConnection): Boolean {
        if (currentSyllable.isNotEmpty()) {
            currentSyllable.deleteCharAt(currentSyllable.length - 1)
            if (currentSyllable.isEmpty()) {
                ic.finishComposingText()
            } else {
                updateComposingText(ic)
            }
            return true
        }
        if (pendingMarks.isNotEmpty()) {
            pendingMarks.removeAt(pendingMarks.lastIndex)
            return true
        }
        return false
    }
    
    fun clear(ic: InputConnection?) {
        if (ic != null) commitFullSyllable(ic)
        pendingMarks.clear()
    }

    private fun sortSyllable(input: String): String {
        return input.toCharArray()
            .sortedBy { getWeight(it) }
            .joinToString("")
            .replace("\u1027\u103A", "\u1009\u103A") // Asin Fix
    }

    private fun getWeight(c: Char): Int = when (c.code) {
        in 0x1000..0x1021 -> 10 // Consonants
        in 0x1023..0x102A -> 10 // Independent Vowels
        0x103C -> 20            // Medial Ra (Ra-yit)
        in 0x103B..0x103E -> 21 // Other Medials
        in 0x102D..0x1030 -> 30 // Vowels
        0x102B, 0x102C -> 30    // AA
        0x1031 -> 40            // Pre-base E
        0x103A, 0x1036 -> 50    // Asat, Anusvara
        0x1037, 0x1038 -> 60    // Tones
        0x1039 -> 10            // Virama (Stacking) bind to Consonant level
        else -> 100
    }
    
    private fun isStacking(): Boolean {
        if (currentSyllable.isEmpty()) return false
        val lastChar = currentSyllable[currentSyllable.length - 1]
        return lastChar.code == 0x1039 // Virama
    }
    
    private fun hasAnchor(): Boolean {
        return containsConsonant(currentSyllable)
    }
    
    private fun containsConsonant(seq: CharSequence): Boolean {
        return seq.any { isMyanmarConsonant(it.code) }
    }
    
    private fun containsChar(seq: StringBuilder, c: Char): Boolean {
        return seq.contains(c)
    }

    private fun isMyanmarConsonant(code: Int): Boolean {
         return (code in 0x1000..0x1021) || 
                (code in 0x1023..0x102A) ||
                code == 0x103F || 
                code == 0x104E
    }
    
    private fun isPunctuation(code: Int): Boolean {
        return code == 0x104A || code == 0x104B || 
               (code < 0x1000 && code != 0x0020)
    }
}
