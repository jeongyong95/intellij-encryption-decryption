package com.github.jeongyong95.intellijencryptiondecryption.crypto

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class CryptoServiceTest {

    private val password = "testPassword123!"
    private val plainText = "Hello, World! 안녕하세요!"

    @Test
    fun `encrypt and decrypt round trip for all algorithms`() {
        for (algorithm in CryptoAlgorithm.entries) {
            val encrypted = CryptoService.encrypt(plainText, password, algorithm)
            val decrypted = CryptoService.decrypt(encrypted, password, algorithm)
            assertEquals("Round trip failed for ${algorithm.displayName}", plainText, decrypted)
        }
    }

    @Test
    fun `encrypted output differs from plain text`() {
        val encrypted = CryptoService.encrypt(plainText, password, CryptoAlgorithm.AES_256_CBC)
        assertNotEquals(plainText, encrypted)
    }

    @Test
    fun `different passwords produce different ciphertexts`() {
        val encrypted1 = CryptoService.encrypt(plainText, "password1", CryptoAlgorithm.AES_256_CBC)
        val encrypted2 = CryptoService.encrypt(plainText, "password2", CryptoAlgorithm.AES_256_CBC)
        assertNotEquals(encrypted1, encrypted2)
    }

    @Test
    fun `wrong password fails to decrypt`() {
        val encrypted = CryptoService.encrypt(plainText, password, CryptoAlgorithm.AES_256_CBC)
        assertThrows(Exception::class.java) {
            CryptoService.decrypt(encrypted, "wrongPassword", CryptoAlgorithm.AES_256_CBC)
        }
    }

    @Test
    fun `encrypt and decrypt empty string`() {
        val encrypted = CryptoService.encrypt("", password, CryptoAlgorithm.AES_256_CBC)
        val decrypted = CryptoService.decrypt(encrypted, password, CryptoAlgorithm.AES_256_CBC)
        assertEquals("", decrypted)
    }

    @Test
    fun `encrypt produces different output each time due to random salt and IV`() {
        val encrypted1 = CryptoService.encrypt(plainText, password, CryptoAlgorithm.AES_256_CBC)
        val encrypted2 = CryptoService.encrypt(plainText, password, CryptoAlgorithm.AES_256_CBC)
        assertNotEquals(encrypted1, encrypted2)
    }
}
