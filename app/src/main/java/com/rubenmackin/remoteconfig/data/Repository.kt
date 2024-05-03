package com.rubenmackin.remoteconfig.data

import android.content.Context
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.get
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.math.min

class Repository @Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig,
    @ApplicationContext private val context: Context
) {

    companion object {
        const val KEY_TITLE = "title"
        const val KEY_HEPPY = "happy"
        const val MIN_VERSION_RC = "min_version"
    }

    suspend fun getAppInfo(): String {
        remoteConfig.fetch(0)
        remoteConfig.activate().await()
        val title = remoteConfig.getString(KEY_TITLE)
        val happy = remoteConfig.getString(KEY_HEPPY)
        return "El titulo $title y ahora estas $happy"
    }

    fun getAppVersion(): List<Int> {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionName.split(".").map {
                it.toInt()
            }
        } catch (e: Exception) {
            listOf(0, 0, 0)
        }
    }

    suspend fun getMinAllowedVersion(): List<Int> {
        remoteConfig.fetch(0)
        remoteConfig.activate().await()
        val minVersion = remoteConfig.getString(MIN_VERSION_RC)
        if (minVersion.isBlank()) return listOf(0, 0, 0)
        return minVersion.split(".").map {
            it.toInt()
        }
    }
}