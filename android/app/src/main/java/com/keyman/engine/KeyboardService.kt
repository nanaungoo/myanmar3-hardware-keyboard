package com.keyman.engine

import android.inputmethodservice.InputMethodService
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import com.keyman.engine.hardware.HardwareKeyboardMapper
import com.keyman.engine.pattern.PatternMatcher
import com.keyman.engine.pattern.ZawCodePatterns

/**
 * Main Input Method Service for Keyman keyboard with Myanmar3 hardware keyboard support.
 * 
 * This service extends Android's InputMethodService to provide custom keyboard
 * functionality using KeyMagic-style pattern matching for Myanmar Unicode input.
 * 
 * Key Features:
 * - Hardware keyboard interception and remapping
 * - Pattern-based Myanmar Unicode transformation
 * - Context-aware character reordering
 * - Keyboard switching via Ctrl+Tab
 * 
 * @author Myanmar Keyboard Implementation Team
 * @since 3.0.0
 */
class KeyboardService : InputMethodService() {
    
    // ========================================================================
    // Properties
    // ========================================================================
    
    // Hardware keyboard mapper
    private lateinit var hardwareKeyMapper: HardwareKeyboardMapper
    
    // Pattern matcher for KeyMagic-style rules
    private val patternMatcher = PatternMatcher().apply {
        addRules(ZawCodePatterns.getRules())
    }
    
    /**
     * Tracks whether Myanmar3 hardware keyboard mode is currently active.
     * This can be toggled by user settings or keyboard switching.
     */
    private var myanmar3HardwareActive = false
    
    /**
     * Tracks the state of modifier keys (Ctrl, Alt, Shift).
     * Used for keyboard switching shortcuts and special functions.
     */
    private var ctrlPressed = false
    private var altPressed = false
    
    // ========================================================================
    // Lifecycle Methods
    // ========================================================================
    
    override fun onCreate() {
        super.onCreate()
        // Initialize hardware keyboard mapper
        hardwareKeyMapper = HardwareKeyboardMapper(this)
        // Initialize keyboard state from preferences
        loadKeyboardPreferences()
    }
    
    override fun onStartInput(attribute: EditorInfo?, restarting: Boolean) {
        super.onStartInput(attribute, restarting)
        
        // Check if hardware keyboard is connected
        // and Myanmar3 should be activated
        checkAndActivateHardwareKeyboard()
    }
    
    override fun onStartInputView(info: EditorInfo?, restarting: Boolean) {
        super.onStartInputView(info, restarting)
        
        // Hide soft keyboard if hardware keyboard is active
        if (myanmar3HardwareActive && isHardwareKeyboardConnected()) {
            // Request to hide the soft keyboard
            requestHideSelf(0)
        }
    }
    
    // ========================================================================
    // Hardware Key Event Handling
    // ========================================================================
    
    /**
     * Handles key down events from hardware keyboard.
     * 
     * This method intercepts physical key presses and:
     * 1. Checks if Myanmar3 hardware keyboard is active
     * 2. Maps the key to a Myanmar Unicode character
     * 3. Commits the character to the input connection
     * 4. Handles special key combinations (e.g., Ctrl+Tab)
     * 
     * @param keyCode The Android keycode of the pressed key
     * @param event The KeyEvent containing additional information
     * @return true if the event was consumed, false to pass through
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        event?.let {
            // Check if Ctrl is pressed using metaState (more reliable)
            val isCtrlPressed = (it.metaState and KeyEvent.META_CTRL_ON) != 0 ||
                               (it.metaState and KeyEvent.META_CTRL_LEFT_ON) != 0 ||
                               (it.metaState and KeyEvent.META_CTRL_RIGHT_ON) != 0
            
            // Track modifier key states for fallback
            when (keyCode) {
                KeyEvent.KEYCODE_CTRL_LEFT, KeyEvent.KEYCODE_CTRL_RIGHT -> {
                    ctrlPressed = true
                    return false  // Let system handle modifier key
                }
                KeyEvent.KEYCODE_ALT_LEFT, KeyEvent.KEYCODE_ALT_RIGHT -> {
                    altPressed = true
                    return false  // Let system handle modifier key
                }
            }
            
            // Handle Ctrl+Tab for keyboard on/off toggle
            if ((isCtrlPressed || ctrlPressed) && keyCode == KeyEvent.KEYCODE_TAB) {
                switchToNextKeyboard()
                return true
            }
            
            // Handle Ctrl+Space for layout switching (ZawCode ↔ English)
            if ((isCtrlPressed || ctrlPressed) && keyCode == KeyEvent.KEYCODE_SPACE) {
                switchToNextLayout()
                return true
            }
            
            // Handle Enter key - commit composing text but keep buffer
            if (isMyanmar3HardwareActive() && keyCode == KeyEvent.KEYCODE_ENTER) {
                val ic = currentInputConnection
                if (ic != null) {
                    // Commit any composing text to prevent accumulation
                    ic.finishComposingText()
                    // CRITICAL: Clear pattern matcher buffer
                    patternMatcher.clear()
                }
                // Let system handle the Enter (newline)
                return super.onKeyDown(keyCode, event)
            }
            
            // Handle backspace in Myanmar mode
            if (isMyanmar3HardwareActive() && keyCode == KeyEvent.KEYCODE_DEL) {
                handleMyanmarBackspace()
                return true
            }
            
            // Handle Myanmar3 hardware keyboard input
            if (isMyanmar3HardwareActive()) {
                val mappedText = hardwareKeyMapper.mapKey(keyCode, it)
                
                if (mappedText != null) {
                    handleMyanmarInput(mappedText)
                    return true
                }
                
                // CRITICAL: Only consume unmapped printable keys in ZawCode mode
                // In English mode, let system handle everything (passthrough)
                if (isZawCodeLayout() && isPrintableKey(keyCode)) {
                    return true  // Consume to prevent double injection in ZawCode
                }
            }
        }
        
        // Let the system handle unmapped keys (including all English mode keys)
        return super.onKeyDown(keyCode, event)
    }
    
    /**
     * Checks if current layout is ZawCode (not English passthrough).
     */
    private fun isZawCodeLayout(): Boolean {
        return hardwareKeyMapper.getCurrentLayout() == 
               com.keyman.engine.hardware.LayoutManager.KeyboardLayout.ZAWCODE
    }
    
    /**
     * Checks if a keycode represents a printable character.
     * These keys should be consumed when Myanmar keyboard is active.
     */
    private fun isPrintableKey(keyCode: Int): Boolean {
        return when (keyCode) {
            // Letter keys
            in KeyEvent.KEYCODE_A..KeyEvent.KEYCODE_Z -> true
            // Number keys
            in KeyEvent.KEYCODE_0..KeyEvent.KEYCODE_9 -> true
            // Numpad keys
            in KeyEvent.KEYCODE_NUMPAD_0..KeyEvent.KEYCODE_NUMPAD_9 -> true
            // Punctuation and symbols
            KeyEvent.KEYCODE_SPACE,
            KeyEvent.KEYCODE_COMMA,
            KeyEvent.KEYCODE_PERIOD,
            KeyEvent.KEYCODE_SEMICOLON,
            KeyEvent.KEYCODE_SLASH,
            KeyEvent.KEYCODE_APOSTROPHE,
            KeyEvent.KEYCODE_LEFT_BRACKET,
            KeyEvent.KEYCODE_RIGHT_BRACKET,
            KeyEvent.KEYCODE_BACKSLASH,
            KeyEvent.KEYCODE_MINUS,
            KeyEvent.KEYCODE_EQUALS,
            KeyEvent.KEYCODE_GRAVE -> true
            else -> false
        }
    }
    
    /**
     * Handles key up events from hardware keyboard.
     * 
     * This method:
     * 1. Tracks modifier key releases
     * 2. Consumes events for keys handled by Myanmar3 layout
     * 
     * @param keyCode The Android keycode of the released key
     * @param event The KeyEvent containing additional information
     * @return true if the event was consumed, false to pass through
     */
    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        event?.let {
            // Track modifier key states
            when (keyCode) {
                KeyEvent.KEYCODE_CTRL_LEFT, KeyEvent.KEYCODE_CTRL_RIGHT -> {
                    ctrlPressed = false
                    return false  // Don't consume - let system handle
                }
                KeyEvent.KEYCODE_ALT_LEFT, KeyEvent.KEYCODE_ALT_RIGHT -> {
                    altPressed = false
                    return false  // Don't consume - let system handle
                }
            }
            
            // Consume key up events for Myanmar3 handled keys
            if (isMyanmar3HardwareActive()) {
                // Consume if key has mapping
                if (hardwareKeyMapper.isHandledKey(keyCode)) {
                    return true
                }
                // OR consume printable keys in ZawCode mode only
                if (isZawCodeLayout() && isPrintableKey(keyCode)) {
                    return true
                }
            }
        }
        
        return super.onKeyUp(keyCode, event)
    }
    
    // ========================================================================
    // Text Commit Methods
    // ========================================================================
    
    /**
     * Handles Myanmar character input using pattern matching.
     * 
     * @param text The Myanmar Unicode character to process
     */
    private fun handleMyanmarInput(text: String) {
        val ic = currentInputConnection ?: return
        
        // Process input through pattern matcher
        val output = patternMatcher.processInput(text)
        
        // Show as composing text
        ic.setComposingText(output, 1)
    }
    
    /**
     * Handles backspace in Myanmar mode.
     */
    private fun handleMyanmarBackspace() {
        val ic = currentInputConnection ?: return
        
        // Use pattern matcher's backspace
        val output = patternMatcher.handleBackspace()
        
        if (output.isNotEmpty()) {
            ic.setComposingText(output, 1)
        } else {
            ic.finishComposingText()
            ic.deleteSurroundingText(1, 0)
        }
    }
    
    /**
     * Commits Myanmar text to the current input connection.
     * 
     * @param text The Myanmar Unicode text to commit
     * @deprecated Use handleMyanmarInput instead for proper syllable composition
     */
    private fun commitMyanmarText(text: String) {
        val ic = currentInputConnection
        if (ic != null) {
            ic.commitText(text, 1)
        }
    }
    
    // ========================================================================
    // Keyboard State Management
    // ========================================================================
    
    /**
     * Checks if Myanmar3 hardware keyboard mode is currently active.
     * 
     * @return true if Myanmar3 hardware keyboard should process key events
     */
    private fun isMyanmar3HardwareActive(): Boolean {
        return myanmar3HardwareActive
    }
    
    /**
     * Activates Myanmar3 hardware keyboard mode.
     * This enables Myanmar character mapping for hardware key events.
     */
    fun activateMyanmar3Hardware() {
        myanmar3HardwareActive = true
        saveKeyboardPreferences()
    }
    
    /**
     * Deactivates Myanmar3 hardware keyboard mode.
     * Hardware keys will use default system mapping.
     */
    fun deactivateMyanmar3Hardware() {
        myanmar3HardwareActive = false
        saveKeyboardPreferences()
    }
    
    /**
     * Toggles Myanmar3 hardware keyboard mode on/off.
     */
    fun toggleMyanmar3Hardware() {
        myanmar3HardwareActive = !myanmar3HardwareActive
        saveKeyboardPreferences()
    }
    
    /**
     * Switches to the next available keyboard layout.
     * This is triggered by Ctrl+Tab on hardware keyboard.
     */
    private fun switchToNextKeyboard() {
        // Toggle Myanmar3 mode on/off
        toggleMyanmar3Hardware()
        
        // Show a toast notification
        showKeyboardSwitchNotification()
    }
    
    /**
     * Switches to the next keyboard layout (Pyidaungsu → ZawCode → Myanmar3).
     * This is triggered by Ctrl+Space on hardware keyboard.
     */
    private fun switchToNextLayout() {
        hardwareKeyMapper.switchToNextLayout()
        
        // Show notification with current layout name
        showLayoutSwitchNotification()
    }
    
    /**
     * Shows a notification when keyboard layout is switched.
     */
    private fun showKeyboardSwitchNotification() {
        val mode = if (myanmar3HardwareActive) "Myanmar3" else "Standard"
        android.widget.Toast.makeText(this, "Keyboard: $mode", android.widget.Toast.LENGTH_SHORT).show()
    }
    
    /**
     * Shows a notification when layout is switched (Pyidaungsu/ZawCode/Myanmar3).
     */
    private fun showLayoutSwitchNotification() {
        val layoutName = hardwareKeyMapper.getCurrentLayoutName()
        android.widget.Toast.makeText(this, "Layout: $layoutName", android.widget.Toast.LENGTH_SHORT).show()
    }
    
    // ========================================================================
    // Hardware Detection
    // ========================================================================
    
    /**
     * Checks if a hardware keyboard is currently connected.
     * 
     * This detects Bluetooth or USB keyboards attached to the device.
     * 
     * @return true if a physical keyboard is detected
     */
    private fun isHardwareKeyboardConnected(): Boolean {
        val config = resources.configuration
        return config.keyboard != android.content.res.Configuration.KEYBOARD_NOKEYS &&
               config.hardKeyboardHidden != android.content.res.Configuration.HARDKEYBOARDHIDDEN_YES
    }
    
    /**
     * Checks if Myanmar3 hardware keyboard should be automatically activated.
     * This is called when input starts to determine the appropriate mode.
     */
    private fun checkAndActivateHardwareKeyboard() {
        if (isHardwareKeyboardConnected()) {
            // Auto-activate if preference is set
            val autoActivate = getSharedPreferences("keyman_prefs", MODE_PRIVATE)
                .getBoolean("auto_activate_myanmar3", true)
            
            if (autoActivate) {
                activateMyanmar3Hardware()
            }
        }
    }
    
    // ========================================================================
    // Preferences Management
    // ========================================================================
    
    /**
     * Loads keyboard preferences from SharedPreferences.
     */
    private fun loadKeyboardPreferences() {
        val prefs = getSharedPreferences("keyman_prefs", MODE_PRIVATE)
        myanmar3HardwareActive = prefs.getBoolean("myanmar3_active", false)
    }
    
    /**
     * Saves keyboard preferences to SharedPreferences.
     */
    private fun saveKeyboardPreferences() {
        val prefs = getSharedPreferences("keyman_prefs", MODE_PRIVATE)
        prefs.edit()
            .putBoolean("myanmar3_active", myanmar3HardwareActive)
            .apply()
    }
    
    // ========================================================================
    // Debug and Utility Methods
    // ========================================================================
    
    /**
     * Gets debug information about the current keyboard state.
     * Useful for troubleshooting and logging.
     * 
     * @return Map of state information
     */
    fun getDebugState(): Map<String, Any> {
        return mapOf(
            "myanmar3_active" to myanmar3HardwareActive,
            "hardware_connected" to isHardwareKeyboardConnected(),
            "ctrl_pressed" to ctrlPressed,
            "alt_pressed" to altPressed,
            "handled_keycodes" to hardwareKeyMapper.getHandledKeyCodes().size
        )
    }
}
