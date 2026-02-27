package com.github.jeongyong95.intellijencryptiondecryption.crypto

import java.security.MessageDigest

object HashService {

    fun hash(input: String, algorithm: HashAlgorithm): String {
        val digest = MessageDigest.getInstance(algorithm.algorithmName)
        val hashBytes = digest.digest(input.toByteArray(Charsets.UTF_8))
        return hashBytes.joinToString("") { "%02x".format(it) }
    }
}
