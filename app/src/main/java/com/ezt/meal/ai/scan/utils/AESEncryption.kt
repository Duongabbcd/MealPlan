package com.ezt.meal.ai.scan.utils


import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import com.ezt.meal.ai.scan.BuildConfig
import com.google.gson.Gson
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object AESEncryption {
    private const val payLoadKey = "KqwaDto84XZYmsaasfTuyt8gWIS5sWUb"
    private const val Key = "abcdfhjagdshj@12"

    @SuppressLint("HardwareIds")
    fun getDeviceID(context: Context): String {
        val data =
            Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        return data
    }

    fun encodeOpenSsl(input: String, key: String? = null): String {
        val keyToUse = key ?: payLoadKey

        // AES-256 requires a 32-byte key
        val keyBytes = sha256(keyToUse.toByteArray(Charsets.UTF_8))
        val secretKey = SecretKeySpec(keyBytes, "AES")

        // AES block size is 16 bytes
        val iv = ByteArray(16)
        SecureRandom().nextBytes(iv)
        val ivSpec = IvParameterSpec(iv)

        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec)

        val encrypted = cipher.doFinal(input.toByteArray(Charsets.UTF_8))

        // Concatenate IV + encrypted data
        val combined = iv + encrypted

        return Base64.getEncoder().encodeToString(combined)
    }

    private fun sha256(data: ByteArray): ByteArray {
        return MessageDigest.getInstance("SHA-256").digest(data)
    }

    fun encrypt(data: Any): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val iv = ByteArray(16)
        val ivParams = IvParameterSpec(iv)
        val secretKey = SecretKeySpec(payLoadKey.toByteArray(), "AES")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParams)
        val encrypted = cipher.doFinal(Gson().toJson(data).toByteArray())
        val combined = ByteArray(iv.size + encrypted.size)
        System.arraycopy(iv, 0, combined, 0, iv.size)
        System.arraycopy(encrypted, 0, combined, iv.size, encrypted.size)
        val result = Base64.getEncoder().encodeToString(combined)
        println("result - log = $result")
        return result
    }
}



data class DeviceData(
    @SerializedName("user_id")
    @Expose
    var id: Int = 0,

    @SerializedName("device_id")
    @Expose
    var device_id: Int = 0,

    @SerializedName("client_id")
    @Expose
    var client_id: String = "",

    @SerializedName("type")
    @Expose
    var type: String = "1",

    @SerializedName("name")
    @Expose
    var name: String = "",

    @SerializedName("ip")
    @Expose
    var ip: String = "",

    @SerializedName("country_name")
    @Expose
    var countryName: String = "",

    @SerializedName("platform")
    @Expose
    var platform: String = "",

    @SerializedName("app_id")
    @Expose
    var app_id: String = "com.ezt.meal.ai.scan",

    @SerializedName("app_version")
    @Expose
    var app_version: String = BuildConfig.VERSION_NAME,

    @SerializedName("time")
    @Expose
    var time: Long = System.currentTimeMillis()/1000,


//    @SerializedName("time")
//    @Expose
//    var time: Long = System.currentTimeMillis()/1000,
//
//    @SerializedName("time")
//    @Expose
//    var time: Long = System.currentTimeMillis()/1000,
    )



data class DeviceRequestToken(
    @SerializedName("secret")
    @Expose
    var encodedPayload: String? = null
)