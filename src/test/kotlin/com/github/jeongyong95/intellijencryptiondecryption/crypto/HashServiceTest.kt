package com.github.jeongyong95.intellijencryptiondecryption.crypto

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class HashServiceTest {

    @Test
    fun `MD5 produces correct hash`() {
        val result = HashService.hash("hello", HashAlgorithm.MD5)
        assertEquals("5d41402abc4b2a76b9719d911017c592", result)
    }

    @Test
    fun `SHA-1 produces correct hash`() {
        val result = HashService.hash("hello", HashAlgorithm.SHA_1)
        assertEquals("aaf4c61ddcc5e8a2dabede0f3b482cd9aea9434d", result)
    }

    @Test
    fun `SHA-256 produces correct hash`() {
        val result = HashService.hash("hello", HashAlgorithm.SHA_256)
        assertEquals("2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824", result)
    }

    @Test
    fun `SHA-512 produces correct hash`() {
        val result = HashService.hash("hello", HashAlgorithm.SHA_512)
        assertEquals(
            "9b71d224bd62f3785d96d46ad3ea3d73319bfbc2890caadae2dff72519673ca72323c3d99ba5c11d7c7acc6e14b8c5da0c4663475c2e5c3adef46f73bcdec043",
            result,
        )
    }

    @Test
    fun `different inputs produce different hashes`() {
        val hash1 = HashService.hash("hello", HashAlgorithm.SHA_256)
        val hash2 = HashService.hash("world", HashAlgorithm.SHA_256)
        assertNotEquals(hash1, hash2)
    }

    @Test
    fun `same input produces same hash`() {
        val hash1 = HashService.hash("hello", HashAlgorithm.SHA_256)
        val hash2 = HashService.hash("hello", HashAlgorithm.SHA_256)
        assertEquals(hash1, hash2)
    }

    @Test
    fun `unicode input hashes correctly`() {
        val result = HashService.hash("안녕하세요", HashAlgorithm.SHA_256)
        // Should produce a valid 64-char hex string
        assertEquals(64, result.length)
        assert(result.matches(Regex("[0-9a-f]+")))
    }
}
