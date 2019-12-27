package com.grappus.android.api.providers

/**
 * Created by nimish@grappus.com on 12/12/2019.
 *
 * Description - App keyStore Security
 */

interface KeyStoreProvider {
    companion object {
        const val ALGORITHM = "RSA"
        const val ANDROID_KEY_STORE = "AndroidKeyStore"
        const val KEY_SIZE = 4096
        const val TRANSFORMATION = "RSA/ECB/PKCS1Padding"
        const val ENC_PREFIX = "enc_"
    }

    fun saveData(alias: String, input: String?)
    fun getData(alias: String): String?
}