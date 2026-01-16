package com.keyman.engine.pattern

import android.view.inputmethod.InputConnection
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class MyanmarSyllableSegmenterTest {

    @Mock
    lateinit var inputConnection: InputConnection

    private lateinit var segmenter: MyanmarSyllableSegmenter

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        segmenter = MyanmarSyllableSegmenter()
    }

    @Test
    fun testDeduplication_MarkSkip() {
        // Pa (1015) + E (1031) + E (1031)
        // Should result in Pa + E (First E kept, Second E skipped)
        
        segmenter.handleInput('\u1015', inputConnection)
        segmenter.handleInput('\u1031', inputConnection)
        verify(inputConnection).setComposingText("\u1015\u1031", 1)
        
        reset(inputConnection)
        // Type E again
        segmenter.handleInput('\u1031', inputConnection)
        
        // Should STILL be Pa + E.
        // Assuming implementation skips add.
        verify(inputConnection).setComposingText("\u1015\u1031", 1)
    }

    @Test
    fun testPrioritySorting_Weights() {
        // Pa(10) + E(40) + Ra-yit(20) + Asat(50)
        // Typed Sequence (Zawgyi style): E (1031) -> Pa (1015) -> Ra (103C) -> Asat (103A)
        // Expected Logic:
        // 1. E -> Floating State (No Anchor). Pending: [E]. Composing: NONE (or E hidden?).
        //    My impl: updateComposingText does NOT show pending if current is empty.
        //    So EXPECT: never().setComposingText.
        
        segmenter.handleInput('\u1031', inputConnection) // E
        verify(inputConnection, never()).setComposingText(anyString(), anyInt())
        
        reset(inputConnection)
        
        // 2. Pa -> Consonant (Anchor).
        //    - Commit Prev? No prev.
        //    - Add Pa. Current: [Pa].
        //    - Attach Pending: [Pa, E].
        //    - Clear Pending.
        //    - Display: Sorted Pa E.
        
        segmenter.handleInput('\u1015', inputConnection) // Pa
        // Buffer now Pa+E (appended). Sorted Pa+E.
        
        val captor = ArgumentCaptor.forClass(String::class.java)
        verify(inputConnection).setComposingText(captor.capture(), eq(1))
        
        // Pa (1015) E(1031).
        // Sorting: Pa(10) E(40).
        // Expected: Pa E.
        val expected = "\u1015\u1031"
        assertEquals(expected, captor.value)
        
        reset(inputConnection)
        segmenter.handleInput('\u103C', inputConnection) // Ra
        // Sorted Pa Ra E (\u1015\u103C\u1031)
        verify(inputConnection).setComposingText("\u1015\u103C\u1031", 1)
        
        reset(inputConnection)
        segmenter.handleInput('\u103A', inputConnection) // Asat
        // Sorted Pa Ra E Asat (\u1015\u103C\u1031\u103A)
        verify(inputConnection).setComposingText("\u1015\u103C\u1031\u103A", 1)
    }

    @Test
    fun testPostTypingSupport() {
        // Ka (1000) -> E (1031)
        // Standard Typing (Post-typing).
        // 1. Ka. Anchor. Display Ka.
        segmenter.handleInput('\u1000', inputConnection)
        verify(inputConnection).setComposingText("\u1000", 1)
        
        reset(inputConnection)
        
        // 2. E. Has Anchor (Ka).
        //    - Fallthrough to else -> Append to Current.
        //    - Current: [Ka, E].
        //    - Display: Ka E.
        segmenter.handleInput('\u1031', inputConnection)
        verify(inputConnection).setComposingText("\u1000\u1031", 1)
    }
}
