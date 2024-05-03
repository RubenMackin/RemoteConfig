package com.rubenmackin.remoteconfig.data

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.rubenmackin.remoteconfig.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideRemoteConfig(): FirebaseRemoteConfig {
        return Firebase.remoteConfig.apply {
            setConfigSettingsAsync(
                remoteConfigSettings { minimumFetchIntervalInSeconds = 3600 }
            )

            setDefaultsAsync(R.xml.remote_config_defaults)
            fetchAndActivate()
        }
    }
}