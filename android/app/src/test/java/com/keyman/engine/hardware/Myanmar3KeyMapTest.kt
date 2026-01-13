package com.keyman.engine.hardware

import android.view.KeyEvent
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

/**
 * Unit tests for Myanmar3KeyMap class.
 * 
 * These tests verify that all QWERTY keys map correctly to Myanmar Unicode
 * characters in both normal and shifted states.
 */
class Myanmar3KeyMapTest {
    
    private lateinit var keyMap: Myanmar3KeyMap
    
    @Before
    fun setup() {
        keyMap = Myanmar3KeyMap()
    }
    
    // ========================================================================
    // Top Row Tests
    // ========================================================================
    
    @Test
    fun testTopRow_Normal() {
        assertEquals("\u1008", keyMap.getCharacter(KeyEvent.KEYCODE_Q, false)) // ဈ
        assertEquals("\u101D", keyMap.getCharacter(KeyEvent.KEYCODE_W, false)) // ဝ
        assertEquals("\u1023", keyMap.getCharacter(KeyEvent.KEYCODE_E, false)) // ဣ
        assertEquals("\u104E", keyMap.getCharacter(KeyEvent.KEYCODE_R, false)) // ၎
        assertEquals("\u1024", keyMap.getCharacter(KeyEvent.KEYCODE_T, false)) // ဤ
        assertEquals("\u104C", keyMap.getCharacter(KeyEvent.KEYCODE_Y, false)) // ၌
        assertEquals("\u1025", keyMap.getCharacter(KeyEvent.KEYCODE_U, false)) // ဥ
        assertEquals("\u104D", keyMap.getCharacter(KeyEvent.KEYCODE_I, false)) // ၍
        assertEquals("\u103F", keyMap.getCharacter(KeyEvent.KEYCODE_O, false)) // ဿ
        assertEquals("\u100F", keyMap.getCharacter(KeyEvent.KEYCODE_P, false)) // ဏ
    }
    
    @Test
    fun testTopRow_Shifted() {
        assertEquals("\u1006", keyMap.getCharacter(KeyEvent.KEYCODE_Q, true)) // ဆ
        assertEquals("\u1010", keyMap.getCharacter(KeyEvent.KEYCODE_W, true)) // တ
        assertEquals("\u1014", keyMap.getCharacter(KeyEvent.KEYCODE_E, true)) // န
        assertEquals("\u1019", keyMap.getCharacter(KeyEvent.KEYCODE_R, true)) // မ
        assertEquals("\u1021", keyMap.getCharacter(KeyEvent.KEYCODE_T, true)) // အ
        assertEquals("\u1015", keyMap.getCharacter(KeyEvent.KEYCODE_Y, true)) // ပ
        assertEquals("\u1000", keyMap.getCharacter(KeyEvent.KEYCODE_U, true)) // က
        assertEquals("\u1004", keyMap.getCharacter(KeyEvent.KEYCODE_I, true)) // င
        assertEquals("\u101E", keyMap.getCharacter(KeyEvent.KEYCODE_O, true)) // သ
        assertEquals("\u1005", keyMap.getCharacter(KeyEvent.KEYCODE_P, true)) // စ
    }
    
    // ========================================================================
    // Home Row Tests
    // ========================================================================
    
    @Test
    fun testHomeRow_Normal() {
        assertEquals("\u1017", keyMap.getCharacter(KeyEvent.KEYCODE_A, false)) // ဗ
        assertEquals("\u103B", keyMap.getCharacter(KeyEvent.KEYCODE_S, false)) // ှ
        assertEquals("\u102E", keyMap.getCharacter(KeyEvent.KEYCODE_D, false)) // ီ
        assertEquals("\u1039", keyMap.getCharacter(KeyEvent.KEYCODE_F, false)) // ်
        assertEquals("\u103D", keyMap.getCharacter(KeyEvent.KEYCODE_G, false)) // ွ
        assertEquals("\u1036", keyMap.getCharacter(KeyEvent.KEYCODE_H, false)) // ံ
        assertEquals("\u1032", keyMap.getCharacter(KeyEvent.KEYCODE_J, false)) // ဲ
        assertEquals("\u1012", keyMap.getCharacter(KeyEvent.KEYCODE_K, false)) // ဒ
        assertEquals("\u1013", keyMap.getCharacter(KeyEvent.KEYCODE_L, false)) // ဓ
        assertEquals("\u1002", keyMap.getCharacter(KeyEvent.KEYCODE_SEMICOLON, false)) // ဂ
    }
    
    @Test
    fun testHomeRow_Shifted() {
        assertEquals("\u1031", keyMap.getCharacter(KeyEvent.KEYCODE_A, true)) // ေ
        assertEquals("\u103C", keyMap.getCharacter(KeyEvent.KEYCODE_S, true)) // ျ
        assertEquals("\u102D", keyMap.getCharacter(KeyEvent.KEYCODE_D, true)) // ိ
        assertEquals("\u1037", keyMap.getCharacter(KeyEvent.KEYCODE_F, true)) // ့
        assertEquals("\u102B", keyMap.getCharacter(KeyEvent.KEYCODE_G, true)) // ါ
        assertEquals("\u102F", keyMap.getCharacter(KeyEvent.KEYCODE_K, true)) // ု
        assertEquals("\u1030", keyMap.getCharacter(KeyEvent.KEYCODE_L, true)) // ူ
        assertEquals("\u1038", keyMap.getCharacter(KeyEvent.KEYCODE_SEMICOLON, true)) // း
    }
    
    // ========================================================================
    // Bottom Row Tests
    // ========================================================================
    
    @Test
    fun testBottomRow_Normal() {
        assertEquals("\u1007", keyMap.getCharacter(KeyEvent.KEYCODE_Z, false)) // ဇ
        assertEquals("\u100C", keyMap.getCharacter(KeyEvent.KEYCODE_X, false)) // ဌ
        assertEquals("\u1003", keyMap.getCharacter(KeyEvent.KEYCODE_C, false)) // ဃ
        assertEquals("\u1020", keyMap.getCharacter(KeyEvent.KEYCODE_V, false)) // ဠ
        assertEquals("\u101A", keyMap.getCharacter(KeyEvent.KEYCODE_B, false)) // ယ
        assertEquals("\u1009", keyMap.getCharacter(KeyEvent.KEYCODE_N, false)) // ဉ
        assertEquals("\u1026", keyMap.getCharacter(KeyEvent.KEYCODE_M, false)) // ဦ
        assertEquals("\u104A", keyMap.getCharacter(KeyEvent.KEYCODE_COMMA, false)) // ၊
        assertEquals("\u104B", keyMap.getCharacter(KeyEvent.KEYCODE_PERIOD, false)) // ။
    }
    
    @Test
    fun testBottomRow_Shifted() {
        assertEquals("\u1016", keyMap.getCharacter(KeyEvent.KEYCODE_Z, true)) // ဖ
        assertEquals("\u1011", keyMap.getCharacter(KeyEvent.KEYCODE_X, true)) // ထ
        assertEquals("\u1001", keyMap.getCharacter(KeyEvent.KEYCODE_C, true)) // ခ
        assertEquals("\u101C", keyMap.getCharacter(KeyEvent.KEYCODE_V, true)) // လ
        assertEquals("\u1018", keyMap.getCharacter(KeyEvent.KEYCODE_B, true)) // ဘ
        assertEquals("\u100A", keyMap.getCharacter(KeyEvent.KEYCODE_N, true)) // ည
        assertEquals("\u102C", keyMap.getCharacter(KeyEvent.KEYCODE_M, true)) // ာ
    }
    
    // ========================================================================
    // General Functionality Tests
    // ========================================================================
    
    @Test
    fun testHasMapping_ValidKeys() {
        assertTrue(keyMap.hasMapping(KeyEvent.KEYCODE_Q))
        assertTrue(keyMap.hasMapping(KeyEvent.KEYCODE_A))
        assertTrue(keyMap.hasMapping(KeyEvent.KEYCODE_Z))
    }
    
    @Test
    fun testHasMapping_InvalidKeys() {
        assertFalse(keyMap.hasMapping(KeyEvent.KEYCODE_1))
        assertFalse(keyMap.hasMapping(KeyEvent.KEYCODE_TAB))
        assertFalse(keyMap.hasMapping(KeyEvent.KEYCODE_ENTER))
    }
    
    @Test
    fun testGetAllMappedKeyCodes() {
        val keyCodes = keyMap.getAllMappedKeyCodes()
        
        // Should contain all letter keys
        assertTrue(keyCodes.contains(KeyEvent.KEYCODE_Q))
        assertTrue(keyCodes.contains(KeyEvent.KEYCODE_M))
        
        // Should be sorted
        assertEquals(keyCodes, keyCodes.sorted())
    }
    
    @Test
    fun testGetStatistics() {
        val stats = keyMap.getStatistics()
        
        assertNotNull(stats["total_normal_mappings"])
        assertNotNull(stats["total_shift_mappings"])
        assertNotNull(stats["total_mappings"])
        assertNotNull(stats["unique_keycodes"])
        
        // Total should be sum of normal and shift
        assertEquals(
            stats["total_mappings"],
            stats["total_normal_mappings"]!! + stats["total_shift_mappings"]!!
        )
    }
    
    @Test
    fun testUnmappedKeyReturnsNull() {
        assertNull(keyMap.getCharacter(KeyEvent.KEYCODE_SPACE, false))
        assertNull(keyMap.getCharacter(KeyEvent.KEYCODE_ENTER, false))
        assertNull(keyMap.getCharacter(KeyEvent.KEYCODE_1, true))
    }
}
