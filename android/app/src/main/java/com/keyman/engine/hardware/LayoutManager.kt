package com.keyman.engine.hardware

import android.content.Context
import android.content.SharedPreferences

/**
 * Manages multiple keyboard layouts and handles switching between them.
 * 
 * This class acts as a central controller for managing different Myanmar
 * keyboard layouts (Pyidaungsu, ZawCode, etc.) and provides a unified
 * interface for character mapping regardless of the active layout.
 * 
 * Features:
 * - Multiple layout support (Pyidaungsu MM, ZawCode, Myanmar3)
 * - Layout switching with Ctrl+Space
 * - Persistent layout preference
 * - Unified character mapping API
 * 
 * @property context Android context for accessing SharedPreferences
 * 
 * @author Myanmar Keyboard Implementation Team
 * @since 2.0.0
 */
class LayoutManager(private val context: Context) {
    
    // ========================================================================
    // Layout Definitions
    // ========================================================================
    
    /**
     * Available keyboard layouts.
     */
    enum class KeyboardLayout(val displayName: String, val id: String) {
        PYIDAUNGSU("Pyidaungsu MM", "pyidaungsu"),
        ZAWCODE("ZawCode", "zawcode"),
        MYANMAR3("Myanmar3", "myanmar3");
        
        companion object {
            fun fromId(id: String): KeyboardLayout {
                return values().find { it.id == id } ?: PYIDAUNGSU
            }
        }
    }
    
    // ========================================================================
    // Properties
    // ========================================================================
    
    private val preferences: SharedPreferences by lazy {
        context.getSharedPreferences("keyman_prefs", Context.MODE_PRIVATE)
    }
    
    private var currentLayout: KeyboardLayout
    
    // Layout implementations
    private val pyidaungsuMap = PyidaungsuKeyMap()
    private val zawCodeMap = ZawCodeKeyMap()
    private val myanmar3Map = Myanmar3KeyMap()
    
    // ========================================================================
    // Initialization
    // ========================================================================
    
    init {
        // Load saved layout preference
        val savedLayoutId = preferences.getString("keyboard_layout", KeyboardLayout.PYIDAUNGSU.id)
        currentLayout = KeyboardLayout.fromId(savedLayoutId ?: KeyboardLayout.PYIDAUNGSU.id)
    }
    
    // ========================================================================
    // Public API
    // ========================================================================
    
    /**
     * Gets the Myanmar character for a given keycode and shift state
     * using the currently active layout.
     * 
     * @param keyCode Android keycode
     * @param isShifted Whether Shift key is pressed
     * @return Mapped Myanmar Unicode character or null if not mapped
     */
    fun getCharacter(keyCode: Int, isShifted: Boolean): String? {
        return when (currentLayout) {
            KeyboardLayout.PYIDAUNGSU -> pyidaungsuMap.getCharacter(keyCode, isShifted)
            KeyboardLayout.ZAWCODE -> zawCodeMap.getCharacter(keyCode, isShifted)
            KeyboardLayout.MYANMAR3 -> myanmar3Map.getCharacter(keyCode, isShifted)
        }
    }
    
    /**
     * Checks if a keycode is handled by the current layout.
     * 
     * @param keyCode Android keycode to check
     * @return true if the key has a mapping in current layout
     */
    fun hasMapping(keyCode: Int): Boolean {
        return when (currentLayout) {
            KeyboardLayout.PYIDAUNGSU -> pyidaungsuMap.hasMapping(keyCode)
            KeyboardLayout.ZAWCODE -> zawCodeMap.hasMapping(keyCode)
            KeyboardLayout.MYANMAR3 -> myanmar3Map.hasMapping(keyCode)
        }
    }
    
    /**
     * Gets the currently active keyboard layout.
     * 
     * @return Current KeyboardLayout
     */
    fun getCurrentLayout(): KeyboardLayout {
        return currentLayout
    }
    
    /**
     * Switches to a specific keyboard layout.
     * 
     * @param layout The layout to switch to
     */
    fun switchLayout(layout: KeyboardLayout) {
        currentLayout = layout
        saveLayoutPreference()
    }
    
    /**
     * Switches to the next keyboard layout in the rotation.
     * Rotation order: Pyidaungsu → ZawCode → Myanmar3 → (repeat)
     */
    fun switchToNextLayout() {
        currentLayout = when (currentLayout) {
            KeyboardLayout.PYIDAUNGSU -> KeyboardLayout.ZAWCODE
            KeyboardLayout.ZAWCODE -> KeyboardLayout.MYANMAR3
            KeyboardLayout.MYANMAR3 -> KeyboardLayout.PYIDAUNGSU
        }
        saveLayoutPreference()
    }
    
    /**
     * Gets the display name of the current layout.
     * 
     * @return Human-readable layout name
     */
    fun getCurrentLayoutName(): String {
        return currentLayout.displayName
    }
    
    /**
     * Gets all available layouts.
     * 
     * @return Array of all KeyboardLayout values
     */
    fun getAllLayouts(): Array<KeyboardLayout> {
        return KeyboardLayout.values()
    }
    
    // ========================================================================
    // Private Methods
    // ========================================================================
    
    /**
     * Saves the current layout preference to SharedPreferences.
     */
    private fun saveLayoutPreference() {
        preferences.edit()
            .putString("keyboard_layout", currentLayout.id)
            .apply()
    }
    
    // ========================================================================
    // Debugging & Statistics
    // ========================================================================
    
    /**
     * Gets debug information about the current state.
     * 
     * @return Map of debug information
     */
    fun getDebugInfo(): Map<String, Any> {
        return mapOf(
            "current_layout" to currentLayout.displayName,
            "current_layout_id" to currentLayout.id,
            "available_layouts" to KeyboardLayout.values().map { it.displayName },
            "has_mapping_count" to when (currentLayout) {
                KeyboardLayout.PYIDAUNGSU -> pyidaungsuMap.getAllMappedKeyCodes().size
                KeyboardLayout.ZAWCODE -> zawCodeMap.getAllMappedKeyCodes().size
                KeyboardLayout.MYANMAR3 -> myanmar3Map.getAllMappedKeyCodes().size
            }
        )
    }
}
