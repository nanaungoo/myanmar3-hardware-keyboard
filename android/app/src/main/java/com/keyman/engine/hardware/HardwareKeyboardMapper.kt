package com.keyman.engine.hardware

import android.view.KeyEvent

/**
 * Maps hardware keyboard input to Myanmar3 Unicode characters.
 * Follows Myanmar3 standard layout with support for combining marks.
 * 
 * This class serves as the orchestrator for hardware keyboard mapping,
 * delegating to specialized components for character mapping and 
 * combining mark handling.
 * 
 * @property context Android context for accessing resources if needed
 * 
 * @author Myanmar Keyboard Implementation Team
 * @since 1.0.0
 */
class HardwareKeyboardMapper(
    private val context: android.content.Context
) {
    
    private val layoutManager = LayoutManager(context)
    private val combiningMarkHandler = CombiningMarkHandler()
    
    /**
     * Maps a hardware key event to Myanmar Unicode character(s).
     * 
     * This method:
     * 1. Extracts the keycode and modifier state from the event
     * 2. Looks up the corresponding Myanmar character
     * 3. Handles combining marks appropriately
     * 4. Returns the Unicode string to be committed
     * 
     * @param keyCode Android keycode from KeyEvent
     * @param event KeyEvent containing modifier state and metadata
     * @return Mapped Unicode string or null if not handled by Myanmar3 layout
     * 
     * @example
     * ```kotlin
     * val mapper = HardwareKeyboardMapper(context)
     * val event = KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_Q)
     * val char = mapper.mapKey(KeyEvent.KEYCODE_Q, event) // Returns "ဈ"
     * ```
     */
    fun mapKey(keyCode: Int, event: KeyEvent): String? {
        // Check shift state using metaState for better compatibility
        val isShifted = (event.metaState and KeyEvent.META_SHIFT_ON) != 0
        val baseChar = layoutManager.getCharacter(keyCode, isShifted)
        
        return when {
            baseChar == null -> null
            combiningMarkHandler.isCombiningMark(baseChar) -> {
                // Combining marks are returned as-is
                // The Android input connection handles composition
                handleCombiningMark(baseChar)
            }
            else -> baseChar
        }
    }
    
    /**
     * Checks if this keycode is handled by the Myanmar3 layout.
     * 
     * This is used to determine whether the IME should consume
     * the key event or pass it through to the application.
     * 
     * @param keyCode Android keycode to check
     * @return true if the keycode has a Myanmar3 mapping
     */
    fun isHandledKey(keyCode: Int): Boolean {
        return layoutManager.hasMapping(keyCode)
    }
    
    /**
     * Handles combining mark input with proper sequencing.
     * 
     * In the current implementation, combining marks are returned
     * as-is since the Android input connection and text rendering
     * engine handle the actual composition and positioning.
     * 
     * Future enhancements could include:
     * - Validation of combining mark sequences
     * - Reordering for proper storage order
     * - Context-aware mark insertion
     * 
     * @param mark The combining mark character
     * @return The processed combining mark (currently unchanged)
     */
    private fun handleCombiningMark(mark: String): String {
        // For hardware keyboard, we emit combining marks directly
        // The rendering engine handles visual positioning
        return combiningMarkHandler.process(mark)
    }
    
    /**
     * Retrieves a list of all handled keycodes for debugging purposes.
     * 
     * @return List of Android keycodes that have Myanmar3 mappings
     */
    fun getHandledKeyCodes(): List<Int> {
        // Simplified for now - return empty list
        return emptyList()
    }
    
    /**
     * Switches  to the next available keyboard layout.
     * Rotation: Pyidaungsu → ZawCode → Myanmar3 → (repeat)
     */
    fun switchToNextLayout() {
        layoutManager.switchToNextLayout()
    }
    
    /**
     * Sets a specific keyboard layout.
     * 
     * @param layout The layout to switch to
     */
    fun setLayout(layout: LayoutManager.KeyboardLayout) {
        layoutManager.switchLayout(layout)
    }
    
    /**
     * Gets the currently active keyboard layout.
     * 
     * @return Current KeyboardLayout
     */
    fun getCurrentLayout(): LayoutManager.KeyboardLayout {
        return layoutManager.getCurrentLayout()
    }
    
    /**
     * Gets the display name of the current layout.
     * 
     * @return Human-readable layout name
     */
    fun getCurrentLayoutName(): String {
        return layoutManager.getCurrentLayoutName()
    }
}
