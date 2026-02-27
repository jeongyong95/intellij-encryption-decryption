package com.github.jeongyong95.intellijencryptiondecryption.crypto

enum class CryptoAlgorithm(
    val displayName: String,
    val transformation: String,
    val keySize: Int,
    val ivSize: Int,
) {
    AES_128_CBC("AES-128 CBC", "AES/CBC/PKCS5Padding", 16, 16),
    AES_192_CBC("AES-192 CBC", "AES/CBC/PKCS5Padding", 24, 16),
    AES_256_CBC("AES-256 CBC", "AES/CBC/PKCS5Padding", 32, 16),
    AES_128_GCM("AES-128 GCM", "AES/GCM/NoPadding", 16, 12),
    AES_192_GCM("AES-192 GCM", "AES/GCM/NoPadding", 24, 12),
    AES_256_GCM("AES-256 GCM", "AES/GCM/NoPadding", 32, 12),
    DES_CBC("DES CBC", "DES/CBC/PKCS5Padding", 8, 8),
    TRIPLE_DES_CBC("3DES CBC", "DESede/CBC/PKCS5Padding", 24, 8),
    ;

    override fun toString(): String = displayName
}

enum class HashAlgorithm(val displayName: String, val algorithmName: String) {
    MD5("MD5", "MD5"),
    SHA_1("SHA-1", "SHA-1"),
    SHA_256("SHA-256", "SHA-256"),
    SHA_512("SHA-512", "SHA-512"),
    ;

    override fun toString(): String = displayName
}
