package codeasus.projects.encryption.crypto

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object IOSSpecificAESCryptoUtil {
    private const val ALGORITHM_TYPE = "AES"
    private const val ENCRYPTION_MODE_AES_CBC_PKCS5_PADDING = "AES/CBC/PKCS5Padding"

    private lateinit var secretKey: SecretKey
    private lateinit var ivParameterSpec: IvParameterSpec

    fun init(base64SecretKey: String, base64IV: String) {
        base64ToSecretKey(base64SecretKey)
        base64ToIVParameterSpec(base64IV)
    }

    private fun base64ToSecretKey(base64SecretKey: String) {
        val byteArraySecretKey = Base64.decode(base64SecretKey, Base64.NO_WRAP)
        secretKey = SecretKeySpec(
            byteArraySecretKey,
            0,
            byteArraySecretKey.size,
            ALGORITHM_TYPE
        )
    }

    private fun base64ToIVParameterSpec(base64IV: String): IvParameterSpec {
        val byteArrayIV = Base64.decode(base64IV, Base64.NO_WRAP)
        ivParameterSpec = IvParameterSpec(byteArrayIV)
        return ivParameterSpec
    }

    fun encrypt(data: ByteArray): ByteArray {
        val cipher = Cipher.getInstance(ENCRYPTION_MODE_AES_CBC_PKCS5_PADDING)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec)
        return cipher.doFinal(data)
    }

    fun decrypt(data: ByteArray): ByteArray {
        val cipher = Cipher.getInstance(ENCRYPTION_MODE_AES_CBC_PKCS5_PADDING)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec)
        return cipher.doFinal(data)
    }
}