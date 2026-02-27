package com.github.jeongyong95.intellijencryptiondecryption.crypto

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class Base64ServiceTest {

    @Test
    fun `encode produces correct base64`() {
        assertEquals("SGVsbG8sIFdvcmxkIQ==", Base64Service.encode("Hello, World!"))
    }

    @Test
    fun `decode produces correct string`() {
        assertEquals("Hello, World!", Base64Service.decode("SGVsbG8sIFdvcmxkIQ=="))
    }

    @Test
    fun `encode and decode round trip`() {
        val input = "Hello, World! 안녕하세요! 🎉"
        val encoded = Base64Service.encode(input)
        val decoded = Base64Service.decode(encoded)
        assertEquals(input, decoded)
    }

    @Test
    fun `encode empty string`() {
        assertEquals("", Base64Service.encode(""))
    }

    @Test
    fun `decode empty string`() {
        assertEquals("", Base64Service.decode(""))
    }

    @Test
    fun `decode invalid base64 throws exception`() {
        assertThrows(Exception::class.java) {
            Base64Service.decode("not-valid-base64!!!")
        }
    }
}
