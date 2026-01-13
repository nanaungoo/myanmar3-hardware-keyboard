package com.keyman.engine.hardware

import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

/**
 * Unit tests for CombiningMarkHandler class.
 * 
 * These tests verify proper identification and handling of Myanmar
 * combining marks, diacritics, and character validation.
 */
class CombiningMarkHandlerTest {
    
    private lateinit var handler: CombiningMarkHandler
    
    @Before
    fun setup() {
        handler = CombiningMarkHandler()
    }
    
    // ========================================================================
    // Combining Mark Identification Tests
    // ========================================================================
    
    @Test
    fun testIsCombiningMark_VowelSigns() {
        assertTrue(handler.isCombiningMark("\u102D")) // ိ
        assertTrue(handler.isCombiningMark("\u102E")) // ီ
        assertTrue(handler.isCombiningMark("\u102F")) // ု
        assertTrue(handler.isCombiningMark("\u1030")) // ူ
        assertTrue(handler.isCombiningMark("\u102B")) // ါ
        assertTrue(handler.isCombiningMark("\u102C")) // ာ
        assertTrue(handler.isCombiningMark("\u1031")) // ေ
        assertTrue(handler.isCombiningMark("\u1032")) // ဲ
    }
    
    @Test
    fun testIsCombiningMark_Medials() {
        assertTrue(handler.isCombiningMark("\u103B")) // ှ
        assertTrue(handler.isCombiningMark("\u103C")) // ျ/ြ
        assertTrue(handler.isCombiningMark("\u103D")) // ွ
    }
    
    @Test
    fun testIsCombiningMark_ToneMarks() {
        assertTrue(handler.isCombiningMark("\u1036")) // ံ
        assertTrue(handler.isCombiningMark("\u1037")) // ့
        assertTrue(handler.isCombiningMark("\u1038")) // း
    }
    
    @Test
    fun testIsCombiningMark_SpecialCharacters() {
        assertTrue(handler.isCombiningMark("\u1039")) // ် virama
        assertTrue(handler.isCombiningMark("\u103A")) // ့ asat
    }
    
    @Test
    fun testIsCombiningMark_NonCombining() {
        assertFalse(handler.isCombiningMark("\u1000")) // က consonant
        assertFalse(handler.isCombiningMark("\u1015")) // ပ consonant
        assertFalse(handler.isCombiningMark("\u1023")) // ဣ independent vowel
        assertFalse(handler.isCombiningMark("a"))      // Latin
    }
    
    // ========================================================================
    // Pre-base Mark Tests
    // ========================================================================
    
    @Test
    fun testIsPreBaseMark() {
        assertTrue(handler.isPreBaseMark("\u1031"))  // ေ
        assertTrue(handler.isPreBaseMark("\u103C"))  // ြ (can be pre-base)
        
        assertFalse(handler.isPreBaseMark("\u102D")) // ိ (post-base)
        assertFalse(handler.isPreBaseMark("\u1000")) // က (not a mark)
    }
    
    // ========================================================================
    // Character Classification Tests
    // ========================================================================
    
    @Test
    fun testIsConsonant() {
        assertTrue(handler.isConsonant("\u1000"))  // က
        assertTrue(handler.isConsonant("\u1015"))  // ပ
        assertTrue(handler.isConsonant("\u1021"))  // အ
        
        assertFalse(handler.isConsonant("\u1023")) // ဣ (independent vowel)
        assertFalse(handler.isConsonant("\u102D")) // ိ (combining mark)
        assertFalse(handler.isConsonant("a"))      // Latin
    }
    
    @Test
    fun testIsIndependentVowel() {
        assertTrue(handler.isIndependentVowel("\u1023"))  // ဣ
        assertTrue(handler.isIndependentVowel("\u1024"))  // ဤ
        assertTrue(handler.isIndependentVowel("\u1025"))  // ဥ
        assertTrue(handler.isIndependentVowel("\u1026"))  // ဦ
        
        assertFalse(handler.isIndependentVowel("\u1000")) // က (consonant)
        assertFalse(handler.isIndependentVowel("\u102D")) // ိ (combining mark)
    }
    
    // ========================================================================
    // Validation Tests
    // ========================================================================
    
    @Test
    fun testCanApplyCombiningMark_ValidCombinations() {
        // Consonant + vowel mark
        assertTrue(handler.canApplyCombiningMark("\u1000", "\u102D")) // က + ိ
        assertTrue(handler.canApplyCombiningMark("\u1015", "\u1030")) // ပ + ူ
        
        // Consonant + medial
        assertTrue(handler.canApplyCombiningMark("\u1000", "\u103C")) // က + ျ
        
        // Consonant + virama
        assertTrue(handler.canApplyCombiningMark("\u1000", "\u1039")) // က + ်
        
        // Consonant + tone mark
        assertTrue(handler.canApplyCombiningMark("\u1015", "\u1038")) // ပ + း
    }
    
    @Test
    fun testCanApplyCombiningMark_InvalidCombinations() {
        // Non-consonant base
        assertFalse(handler.canApplyCombiningMark("a", "\u102D")) // Latin + mark
        assertFalse(handler.canApplyCombiningMark("1", "\u102D")) // Digit + mark
        
        // Independent vowel + virama (invalid)
        assertFalse(handler.canApplyCombiningMark("\u1023", "\u1039")) // ဣ + ်
        
        // Base + non-mark
        assertFalse(handler.canApplyCombiningMark("\u1000", "\u1001")) // က + ခ
    }
    
    // ========================================================================
    // Syllable Validation Tests
    // ========================================================================
    
    @Test
    fun testIsValidSyllable_Simple() {
        // Single consonant
        assertTrue(handler.isValidSyllable("\u1000")) // က
        
        // Consonant + vowel
        assertTrue(handler.isValidSyllable("\u1000\u102D")) // ကိ
        
        // Consonant + multiple marks
        assertTrue(handler.isValidSyllable("\u1000\u102D\u1038")) // ကိး
    }
    
    @Test
    fun testIsValidSyllable_Complex() {
        // Consonant + medial + vowel
        assertTrue(handler.isValidSyllable("\u1000\u103C\u102D")) // ကျိ
        
        // Consonant + virama + consonant (cluster)
        assertTrue(handler.isValidSyllable("\u1000\u1039\u1000")) // က္က
        
        // Complex syllable with multiple marks
        assertTrue(handler.isValidSyllable("\u101E\u103C\u102C\u1038")) // သျား
    }
    
    @Test
    fun testIsValidSyllable_Invalid() {
        // Empty string
        assertFalse(handler.isValidSyllable(""))
        
        // Starting with combining mark
        assertFalse(handler.isValidSyllable("\u102D\u1000")) // ိက
        
        // Starting with Latin
        assertFalse(handler.isValidSyllable("a\u102D")) // aိ
        
        // Virama not followed by consonant
        assertFalse(handler.isValidSyllable("\u1000\u1039")) // က် (incomplete)
    }
    
    // ========================================================================
    // Storage Order Tests
    // ========================================================================
    
    @Test
    fun testGetStorageOrderPriority() {
        // Virama should have highest priority
        assertEquals(1, handler.getStorageOrderPriority("\u1039"))
        
        // Medials should come before vowels
        val medialPriority = handler.getStorageOrderPriority("\u103C")
        val vowelPriority = handler.getStorageOrderPriority("\u102D")
        assertTrue(medialPriority < vowelPriority)
        
        // Vowels should come before tone marks
        val vowelPriority2 = handler.getStorageOrderPriority("\u102D")
        val tonePriority = handler.getStorageOrderPriority("\u1038")
        assertTrue(vowelPriority2 < tonePriority)
        
        // Non-marks should return 0
        assertEquals(0, handler.getStorageOrderPriority("\u1000"))
        assertEquals(0, handler.getStorageOrderPriority("a"))
    }
    
    // ========================================================================
    // Processing Tests
    // ========================================================================
    
    @Test
    fun testProcess_ReturnsMark() {
        // Currently, process() returns marks unchanged
        assertEquals("\u102D", handler.process("\u102D"))
        assertEquals("\u1039", handler.process("\u1039"))
        assertEquals("\u1031", handler.process("\u1031"))
    }
    
    // ========================================================================
    // Description Tests
    // ========================================================================
    
    @Test
    fun testGetMarkDescription() {
        assertTrue(handler.getMarkDescription("\u102D").contains("VOWEL SIGN I"))
        assertTrue(handler.getMarkDescription("\u1039").contains("VIRAMA"))
        assertTrue(handler.getMarkDescription("\u1031").contains("VOWEL SIGN E"))
        assertEquals("Unknown mark", handler.getMarkDescription("x"))
    }
}
