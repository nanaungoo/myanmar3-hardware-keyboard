package com.keyman.engine.util

/**
 * Utility class for normalizing Myanmar Unicode text.
 * 
 * Implements the "Golden Sequence" and other cleanup logic to ensure
 * perfectly ordered and rendered Myanmar text.
 */
object MyanmarTextNormalizer {

    /**
     * Fixes common Myanmar typing errors and enforces the "Golden Sequence".
     * 
     * The Golden Sequence:
     * 1. Consonant / Independent Vowel
     * 2. Medials (ျ, ြ, ွ, ှ)
     * 3. Vowels (Upper/Lower: ိ, ီ, ု, ူ)
     * 4. Pre-base Vowel (ေ)
     * 5. Anusvara / Asat (ံ, ်)
     * 6. Tones / Dot Below (့, း)
     * 
     * @param input The raw input text
     * @return Normalized Myanmar Unicode text
     */
    fun perfectMyanmarFixer(input: String): String {
        var text = input

        // STEP 1: Fix Pre-base E reordering (The 'awG' error)
        // Moves 'ေ' (U+1031) from before a consonant/medial to after it.
        // Matches: E + Consonant + Optional Medials -> Consonant + Medials + E
        text = text.replace("(\u1031)([\u1000-\u1021])([\u103B-\u103E]*)".toRegex(), "$2$3$1")
        
        // Fix: The 'Ra-yit' Bug (Ref: User Requirement)
        // If ြ (U+103C) is typed before a consonant, move it after.
        text = text.replace("(\u103C)([\u1000-\u1021])".toRegex(), "$2$1")
        
        // Fix: The 'Asin' Bug (Ref: User Requirement)
        // Convert Great Nya + Asat (ဥ်) -> Small Nya + Asat (စဉ်)
        // Correcting likely typo: U+1025 (U) + Asat -> U+1009 (Small Nya) + Asat
        text = text.replace("\u1025\u103A".toRegex(), "\u1009\u103A")
        // Also handle explicit Great Nya (U+100A) + Asat -> Small Nya + Asat if typically intended for 'Asin'
        // text = text.replace("\u100A\u103A".toRegex(), "\u1009\u103A") // Commented out unless explicitly requested, as ည် (U+100A + U+103A) is valid in other contexts.

        // STEP 2: Fix Medial Order (Ya -> Ra -> Wa -> Ha)
        // 103B (Ya), 103C (Ra), 103D (Wa), 103E (Ha)
        
        // If user types Ha-htoe (ှ) before Wa-swal (ွ), flip them.
        text = text.replace("\u103E\u103D".toRegex(), "\u103D\u103E")
        text = text.replace("\u103E\u103C".toRegex(), "\u103C\u103E")
        text = text.replace("\u103D\u103C".toRegex(), "\u103C\u103D")
        
        // Also ensure Ya/Ra come before Wa/Ha if mixed up
        text = text.replace("([\u103D\u103E])([\u103B\u103C])".toRegex(), "$2$1")

        // STEP 3: Vowel & Mark Ordering
        // Vowel U (ု/102F) must come before Anusvara (ံ/1036)
        text = text.replace("\u1036\u102F".toRegex(), "\u102F\u1036")
        // Vowel E (ေ/1031) must come before Tones (း/1038)
        text = text.replace("\u1038\u1031".toRegex(), "\u1031\u1038")
        
        // General: Medials should be before Vowels (Upper/Lower)
        // Medials: 103B-103E. Vowels: 102D, 102E, 102F, 1030
        text = text.replace("([\u102D-\u1030])([\u103B-\u103E])".toRegex(), "$2$1")

        // STEP 4: The "Jumping Dot" (Dot Below)
        // The Dot Below (့/1037) must always be the very last character in the syllable
        // (relative to vowels and other marks, except maybe Visarga which is usually last too, but user says "very last")
        // Checks if Dot Below is followed by Vowels (AA, Tall AA, I, II, U, UU, E, Anusvara, Visarga)
        text = text.replace("(\u1037)([\u102B-\u1036\u1038])".toRegex(), "$2$1")

        // STEP 5: Consonant Stacking (Kinzi)
        // If Nga-That-Asat-Virama is after a consonant, move it to the front (visually/logically for Kinzi)
        // text.replace("([\u1000-\u1021])(\u1004\u103A\u1039)".toRegex(), "$2$1")
        text = text.replace("([\u1000-\u1021])(\u1004\u103A\u1039)".toRegex(), "$2$1")

        // STEP 6: Remove Duplicates (Typo Prevention)
        // Removes duplicate marks (Tall AA, AA, Vowels, Medials, Tones, etc.)
        // Range 102B-103E covers most marks.
        text = text.replace("([\u102B-\u103E])\\1+".toRegex(), "$1")

        return text
    }
}
