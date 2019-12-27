package com.grappus.android.providers

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import androidx.annotation.RequiresApi
import com.grappus.android.api.cache.KeyValueStore
import com.grappus.android.api.providers.KeyStoreProvider
import com.grappus.android.api.providers.KeyStoreProvider.Companion.ALGORITHM
import com.grappus.android.api.providers.KeyStoreProvider.Companion.ANDROID_KEY_STORE
import com.grappus.android.api.providers.KeyStoreProvider.Companion.ENC_PREFIX
import com.grappus.android.api.providers.KeyStoreProvider.Companion.KEY_SIZE
import com.grappus.android.api.providers.KeyStoreProvider.Companion.TRANSFORMATION
import java.nio.charset.Charset
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.PrivateKey
import java.security.spec.RSAKeyGenParameterSpec
import javax.crypto.Cipher
import javax.inject.Inject

/**
 * Created by cp@grappus.com on 20/12/2019.
 * Description - Key store provider implementation
 */

class KeyStoreProviderImpl @Inject constructor(
    private val keyValueStore: KeyValueStore
) : KeyStoreProvider {

    private val keyStore = KeyStore.getInstance(ANDROID_KEY_STORE).apply { load(null) }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun generateKeyPair(alias: String) {
        val keyGenerator = KeyPairGenerator.getInstance(ALGORITHM, ANDROID_KEY_STORE)
        val keyGenParameterSpec = KeyGenParameterSpec.Builder(
            alias,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setAlgorithmParameterSpec(RSAKeyGenParameterSpec(KEY_SIZE, RSAKeyGenParameterSpec.F4))
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
            .build()
        keyGenerator.initialize(keyGenParameterSpec)
        keyGenerator.genKeyPair()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun encryptData(alias: String, input: String): String {
        generateKeyPair(alias)
        val publicKey = keyStore.getCertificate(alias).publicKey

        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)

        val encryptedData = cipher.doFinal(input.toByteArray(Charset.defaultCharset()))
        return Base64.encodeToString(encryptedData, Base64.NO_WRAP)
    }

    private fun decryptData(alias: String, encryptedString: String): String? {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        val key = keyStore.getKey(alias, null)?.let { it as PrivateKey }
        return if (key == null) {
            null
        } else {
            cipher.init(Cipher.DECRYPT_MODE, key)
            val data = Base64.decode(encryptedString, Base64.NO_WRAP)
            val bytes = cipher.doFinal(data)
            bytes.toString(Charset.defaultCharset())
        }
    }

    override fun saveData(alias: String, input: String?) {
        keyValueStore.run {
            if (input != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    val encryptedData = encryptData(alias, input)
                    saveString(ENC_PREFIX + alias, encryptedData)
                } else {
                    saveString(alias, input)
                }
            } else {
                remove(alias)
                remove(ENC_PREFIX + alias)
            }
        }
    }

    override fun getData(alias: String): String? {
        keyValueStore.run {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getString(ENC_PREFIX + alias)?.let {
                    decryptData(alias, it)
                }
            } else {
                getString(alias)
            }
        }
    }
}