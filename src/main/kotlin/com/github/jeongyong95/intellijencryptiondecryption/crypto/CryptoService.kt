package com.github.jeongyong95.intellijencryptiondecryption.crypto

import java.security.SecureRandom
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

object CryptoService {

    private const val PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA256"
    private const val PBKDF2_ITERATIONS = 65536
    private const val SALT_SIZE = 16
    private const val GCM_TAG_BITS = 128

    fun encrypt(plainText: String, password: String, algorithm: CryptoAlgorithm): String {
        val salt = ByteArray(SALT_SIZE).also { SecureRandom().nextBytes(it) }
        val key = deriveKey(password, salt, algorithm)
        val iv = ByteArray(algorithm.ivSize).also { SecureRandom().nextBytes(it) }

        val cipher = Cipher.getInstance(algorithm.transformation)
        val paramSpec = if (algorithm.transformation.contains("GCM")) {
            GCMParameterSpec(GCM_TAG_BITS, iv)
        } else {
            IvParameterSpec(iv)
        }
        cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec)
        val cipherText = cipher.doFinal(plainText.toByteArray(Charsets.UTF_8))

        val combined = salt + iv + cipherText
        return Base64.getEncoder().encodeToString(combined)
    }

    fun decrypt(cipherText: String, password: String, algorithm: CryptoAlgorithm): String {
        val combined = Base64.getDecoder().decode(cipherText)

        val salt = combined.copyOfRange(0, SALT_SIZE)
        val iv = combined.copyOfRange(SALT_SIZE, SALT_SIZE + algorithm.ivSize)
        val encrypted = combined.copyOfRange(SALT_SIZE + algorithm.ivSize, combined.size)

        val key = deriveKey(password, salt, algorithm)

        val cipher = Cipher.getInstance(algorithm.transformation)
        val paramSpec = if (algorithm.transformation.contains("GCM")) {
            GCMParameterSpec(GCM_TAG_BITS, iv)
        } else {
            IvParameterSpec(iv)
        }
        cipher.init(Cipher.DECRYPT_MODE, key, paramSpec)
        val plainBytes = cipher.doFinal(encrypted)

        return String(plainBytes, Charsets.UTF_8)
    }

    private fun deriveKey(password: String, salt: ByteArray, algorithm: CryptoAlgorithm): SecretKeySpec {
        val keyAlgorithm = when {
            algorithm.transformation.startsWith("DESede") -> "DESede"
            algorithm.transformation.startsWith("DES") -> "DES"
            else -> "AES"
        }

        val factory = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM)
        val spec = PBEKeySpec(password.toCharArray(), salt, PBKDF2_ITERATIONS, algorithm.keySize * 8)
        val secretKey = factory.generateSecret(spec)
        return SecretKeySpec(secretKey.encoded, keyAlgorithm)
    }
}
