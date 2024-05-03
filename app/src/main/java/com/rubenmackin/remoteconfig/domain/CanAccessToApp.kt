package com.rubenmackin.remoteconfig.domain

import com.rubenmackin.remoteconfig.data.Repository
import javax.inject.Inject

class CanAccessToApp @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(): Boolean {
        val minVersionAllowed = repository.getMinAllowedVersion()
        val appVersion = repository.getAppVersion()

        return appVersion.zip(minVersionAllowed).all { (appV, minV) ->
            appV >= minV
        }
    }
}