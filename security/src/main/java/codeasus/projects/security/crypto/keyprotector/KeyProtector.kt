package codeasus.projects.security.crypto.keyprotector

interface KeyProtector {

    fun init()

    fun encrypt(data: ByteArray): ByteArray

    fun decrypt(cipherData: ByteArray): ByteArray

    fun encrypt(data: ByteArray, ivBytes: ByteArray): ByteArray

    fun decrypt(cipherData: ByteArray, ivBytes: ByteArray): ByteArray
}