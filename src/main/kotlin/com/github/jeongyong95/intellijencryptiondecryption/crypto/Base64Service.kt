package com.github.jeongyong95.intellijencryptiondecryption.crypto

import java.util.Base64

object Base64Service {

    fun encode(input: String): String =
        Base64.getEncoder().encodeToString(input.toByteArray(Charsets.UTF_8))

    fun decode(input: String): String =
        String(Base64.getDecoder().decode(input), Charsets.UTF_8)
}
