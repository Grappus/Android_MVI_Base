package com.grappus.android.api.cache

import com.google.gson.Gson
import com.grappus.android.api.cache.BaseStore.Keys.AUTH_TOKEN
import com.grappus.android.api.cache.BaseStore.Keys.AUTH_USER
import com.grappus.android.api.cache.BaseStore.Keys.AUTH_USER_ID
import com.grappus.android.api.dispatcher.CommunicationBusProvider
import com.grappus.android.api.dispatcher.ResultEvent.AuthChanged
import com.grappus.android.api.entities.User
import com.grappus.android.api.providers.KeyStoreProvider
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by nimish@grappus.com on 10/12/2019.
 *
 * Description - AuthStore is meant for the Auth related data for the user
 */

@Singleton
class AuthStore @Inject constructor(
    private val keyStoreProvider: KeyStoreProvider,
    keyValueStore: KeyValueStore,
    gson: Gson,
    private val communicationBusProvider: CommunicationBusProvider
) : BaseStore(keyValueStore, gson) {

    var authToken: String? = null
        set(value) {
            if (field != value) {
                field = value
                keyStoreProvider.saveData(AUTH_TOKEN, value)
                communicationBusProvider.sendResult(AuthChanged(true))
            }
        }

    var user: User? = null
        set(value) {
            if (field != value) {
                field = value
                keyStoreProvider.saveData(AUTH_USER, getSerialisedData(value))
            }
        }

    var userId: Long? = null
        set(value) {
            if (field != value) {
                field = value
                keyStoreProvider.saveData(AUTH_USER_ID, value?.toString())
            }
        }

    val isLoggedIn: Boolean
        get() = user.isNotNull()

    init {
        authToken = keyStoreProvider.getData(AUTH_TOKEN)
        user = getDeserialisedData(keyStoreProvider.getData(AUTH_USER))
    }

    private fun User?.isNotNull(): Boolean {
        return !authToken.isNullOrEmpty() && user != null && this?.userId != 0L
    }

}