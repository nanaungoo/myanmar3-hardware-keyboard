package com.keyman.engine.util

import org.junit.Assert.assertEquals
import org.junit.Test

class MyanmarSyllableFixerTest {

    @Test
    fun testPreBaseEReordering() {
        // Step 1: E + Consonant -> Consonant + E
        // ေ(1031) + က(1000) -> က + ေ
        assertEquals("\u1000\u1031", MyanmarSyllableFixer.normalizeMyanmarText("\u1031\u1000"))
        
        // E + Consonant + Medial -> Consonant + Medial + E
        // ေ + က + ျ(103B) -> က + ျ + ေ
        assertEquals("\u1000\u103B\u1031", MyanmarSyllableFixer.normalizeMyanmarText("\u1031\u1000\u103B"))
    }

    @Test
    fun testRayitReordering() {
        // Fix: Ra-yit (Medit Ra 103C) typed before Consonant -> Move after
        // ြ + က -> က + ြ
        assertEquals("\u1000\u103C", MyanmarSyllableFixer.normalizeMyanmarText("\u103C\u1000"))
    }
    
    @Test
    fun testAsinFix() {
        // Fix: U (1025) + Asat (103A) -> Small Nya (1009) + Asat (103A)
        // ဥ် -> ဉ်
        assertEquals("\u1009\u103A", MyanmarSyllableFixer.normalizeMyanmarText("\u1025\u103A"))
    }

    @Test
    fun testMedialOrder() {
        // Step 2: Ha + Wa -> Wa + Ha
        // Ha (24) + Wa (23) -> Wa + Ha
        assertEquals("\u103D\u103E", MyanmarSyllableFixer.normalizeMyanmarText("\u103E\u103D"))
        
        // Ha + Ra -> Ra + Ha
        // Ha (24) + Ra (22) -> Ra + Ha (22 < 24)
        assertEquals("\u103C\u103E", MyanmarSyllableFixer.normalizeMyanmarText("\u103E\u103C"))
        
        // Wa + Ra -> Ra + Wa
        // Wa (23) + Ra (22) -> Ra + Wa (22 < 23)
        assertEquals("\u103C\u103D", MyanmarSyllableFixer.normalizeMyanmarText("\u103D\u103C"))
    }
    
    @Test
    fun testRemoveDuplicates() {
        // Step 6: Aa + Aa -> Aa
        assertEquals("\u102C", MyanmarSyllableFixer.normalizeMyanmarText("\u102C\u102C"))
        assertEquals("\u102C", MyanmarSyllableFixer.normalizeMyanmarText("\u102C\u102C\u102C"))
    }
    
    @Test
    fun testComplexSequence() {
        // Combined usage
        val input = "\u1031\u1000\u103B\u102D\u102F\u1037"
        // Priorities:
        // K (1000) -> 10
        // Ya (103B) -> 20
        // I (102D) -> 30
        // U (102F) -> 30
        // E (1031) -> 40
        // Dot (1037) -> 60
        // Stable Sort: K, Ya, I, U, E, Dot
        val expected = "\u1000\u103B\u102D\u102F\u1031\u1037"
        assertEquals(expected, MyanmarSyllableFixer.normalizeMyanmarText(input))
    }
    

    

}
