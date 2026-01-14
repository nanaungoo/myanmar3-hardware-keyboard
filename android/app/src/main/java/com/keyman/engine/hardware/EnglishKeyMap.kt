package com.keyman.engine.hardware

import android.view.KeyEvent

/**
 * English (passthrough) keyboard layout.
 * 
 * This layout provides standard English keyboard behavior by allowing
 * the Android system to handle all key events normally. It returns null
 * for all mappings, which signals the IME to use default system behavior.
 * 
 * This is useful for users who want to switch between Myanmar typing
 * (ZawCode) and standard English typing without changing keyboards.
 * 
 * @author Myanmar Keyboard Implementation Team
 * @since 2.1.0
 */
class EnglishKeyMap {
    
    /**
     * Gets the character for a given keycode and shift state.
     * 
     * For English layout, this always returns null to allow the system
     * to handle key events with default behavior.
     * 
     * @param keyCode Android keycode
     * @param isShifted Whether the Shift key is pressed
     * @return null (passthrough to system default)
     */
    fun getCharacter(keyCode: Int, isShifted: Boolean): String? {
        return null
    }
    
    /**
     * Checks if a keycode has any mapping.
     * 
     * For English layout, this always returns false since we use
     * passthrough behavior.
     * 
     * @param keyCode Android keycode to check
     * @return false (no custom mappings)
     */
    fun hasMapping(keyCode: Int): Boolean {
        return false
    }
    
    /**
     * Returns a list of all keycodes that have mappings.
     * 
     * For English layout, this returns an empty list since we don't
     * override any keys.
     * 
     * @return Empty list
     */
    fun getAllMappedKeyCodes(): List<Int> {
        return emptyList()
    }
}
