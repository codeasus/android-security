package codeasus.projects.playground

import android.os.Build
import androidx.annotation.RequiresApi
import codeasus.projects.security.crypto.aes.AESUtility
import codeasus.projects.security.crypto.keyprotector.KeyProtector
import codeasus.projects.security.crypto.util.dataToAESSecretKey
import java.nio.charset.StandardCharsets
import java.util.Base64
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

@RequiresApi(Build.VERSION_CODES.O)
fun main() {
    val sampleMessage = "This is a sample message 🍺"
    val messages = Array(1) { sampleMessage }

    benchmarkSpeed {
        messageEncryptionMethodOne(messages)
    }

    benchmarkSpeed {
        messageEncryptionMethodTwo(messages)
    }
}

class WeakAESKeyProtector : KeyProtector {

    private val mSecretKey = AESUtility.generateSecretKey()
    private val mIV = AESUtility.generateIV()

    override fun initialize() {
        TODO("Not yet implemented")
    }

    override fun encrypt(data: ByteArray): ByteArray {
        return AESUtility.encrypt(data, mSecretKey, mIV)
    }

    override fun encrypt(data: ByteArray, ivBytes: ByteArray): ByteArray {
        TODO("Not yet implemented")
    }

    override fun decrypt(cipherData: ByteArray): ByteArray {
        return AESUtility.decrypt(cipherData, mSecretKey, mIV)
    }

    override fun decrypt(cipherData: ByteArray, ivBytes: ByteArray): ByteArray {
        TODO("Not yet implemented")
    }
}

class CryptoKeyManagementServiceOne(private val keyProtector: KeyProtector) {

    private val _sessionSecretKey =
        keyProtector.encrypt(AESUtility.generateSecretKey().encoded)
    private val _sessionIV =
        keyProtector.encrypt(AESUtility.generateIV().iv)

    private fun decryptSessionCryptoParameters(): Pair<SecretKey, IvParameterSpec> {
        val decryptedSecretKey = keyProtector.decrypt(_sessionSecretKey)
        val decryptedIV = keyProtector.decrypt(_sessionIV)
        val secretKey = dataToAESSecretKey(decryptedSecretKey)
        val iv = AESUtility.dataToIV(decryptedIV)
        return Pair(secretKey, iv)
    }

    fun getSecretKeyAndIVPair(): Pair<SecretKey, IvParameterSpec> {
        return decryptSessionCryptoParameters()
    }
}


class CryptoKeyManagementServiceTwo {

    fun getSecretKeyAndIVPair(): Pair<SecretKey, IvParameterSpec> {
        val secretKey = AESUtility.generateSecretKey()
        val iv = AESUtility.generateIV()
        return Pair(secretKey, iv)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
class MessageEncryptionServiceOne {

    private val _cryptoKeyManagementServiceOne =
        CryptoKeyManagementServiceOne(WeakAESKeyProtector())

    fun encrypt(message: String): String {
        val cryptoParameters = _cryptoKeyManagementServiceOne.getSecretKeyAndIVPair()
        val b64EncodedData = message.toByteArray(StandardCharsets.UTF_8)
        val encryptedMessage = AESUtility.encrypt(
            b64EncodedData,
            cryptoParameters.first,
            cryptoParameters.second
        )
        return Base64.getEncoder().encodeToString(encryptedMessage)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
class MessageEncryptionServiceTwo {

    private val _cryptoKeyManagementServiceTwo = CryptoKeyManagementServiceTwo()

    fun encrypt(message: String): String {
        val cryptoParameters = _cryptoKeyManagementServiceTwo.getSecretKeyAndIVPair()
        val b64EncodedData = message.toByteArray(StandardCharsets.UTF_8)
        val encryptedMessage = AESUtility.encrypt(
            b64EncodedData,
            cryptoParameters.first,
            cryptoParameters.second
        )
        return Base64.getEncoder().encodeToString(encryptedMessage)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun messageEncryptionMethodOne(messages: Array<String>) {
    val messageEncryptionServiceOne = MessageEncryptionServiceOne()
    for (message in messages) {
        messageEncryptionServiceOne.encrypt(message)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun messageEncryptionMethodTwo(messages: Array<String>) {
    val messageEncryptionServiceTwo = MessageEncryptionServiceTwo()
    for (message in messages) {
        messageEncryptionServiceTwo.encrypt(message)
    }
}

fun benchmarkSpeed(function: () -> Unit) {
    val startTime = System.currentTimeMillis()
    function.invoke()
    println("Finished in ${System.currentTimeMillis() - startTime}")
}