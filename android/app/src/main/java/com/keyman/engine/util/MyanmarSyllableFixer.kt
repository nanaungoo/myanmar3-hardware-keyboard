package com.keyman.engine.util

/**
 * Myanmar Syllable Fixer using Priority Sorting.
 * Enforces Canonical Ordering by assigning priority values to characters.
 */
object MyanmarSyllableFixer {

    /**
     * Normalizes the text by sorting characters based on priority.
     * 
     * @param input The raw input string
     * @return The normalized string
     */
    /**
     * Priority Map:
     * Consonants: 10
     * Medials: 20 (Ya 21, Ra 22, Wa 23, Ha 24)
     * Vowels: 30
     * Pre-base E: 40
     * Asat/Anusvara: 50
     * Tones: 60
     */
    private fun getPriority(c: Char): Int {
        val code = c.code
        return when (code) {
             in 0x1000..0x1021 -> 10
             in 0x1023..0x102A -> 10 
             
             // Medials with granular order
             0x103B -> 21 // Ya
             0x103C -> 22 // Ra
             0x103D -> 23 // Wa
             0x103E -> 24 // Ha
             
             // Vowels
             in 0x102B..0x1030 -> 30
             0x1032 -> 30
             
             // Pre-base E
             0x1031 -> 40
             
             // Asat, Anusvara
             0x103A, 0x1036 -> 50
             
             // Tones
             0x1037, 0x1038 -> 60
             
             0x1039 -> 10
             
             else -> 100
        }
    }
    
    fun normalizeMyanmarText(input: String): String {
        if (input.isEmpty()) return ""

        // 1. Convert to Mutable List, remove duplicates
        // Note: distinct() removes duplicates of the same char
        val buffer = input.toMutableList().distinct().toMutableList()

        // 2. Sort based on Priority
        buffer.sortWith(Comparator { a, b ->
            val pA = getPriority(a)
            val pB = getPriority(b)
            pA - pB
        })

        // 3. Asin Fix
        var result = buffer.joinToString("")
        if (result.contains("\u1025\u103A")) { 
            result = result.replace("\u1025\u103A", "\u1009\u103A")
        }

        return result
    }
}
