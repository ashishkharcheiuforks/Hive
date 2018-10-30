package com.gpetuhov.android.hive.utils.dagger.modules

import android.content.Context
import com.gpetuhov.android.hive.application.HiveApp
import com.gpetuhov.android.hive.domain.auth.Auth
import com.gpetuhov.android.hive.domain.network.Network
import com.gpetuhov.android.hive.domain.repository.Repo
import com.gpetuhov.android.hive.domain.util.Messages
import com.gpetuhov.android.hive.managers.*
import com.gpetuhov.android.hive.utils.TestAuthManager
import com.gpetuhov.android.hive.utils.TestNetworkManager
import com.gpetuhov.android.hive.utils.TestMessagesProvider
import com.gpetuhov.android.hive.utils.TestRepository
import dagger.Module
import dagger.Provides
import org.mockito.Mockito
import javax.inject.Singleton

// This Dagger module is used in tests instead of AppModule

@Module
class TestAppModule {

    @Provides
    @Singleton
    fun providesContext(): Context = Mockito.mock(HiveApp::class.java)

    @Provides
    @Singleton
    fun providesLocationManager(context: Context): LocationManager = Mockito.mock(LocationManager::class.java)

    @Provides
    @Singleton
    fun providesMapManager(): MapManager = Mockito.mock(MapManager::class.java)

    @Provides
    @Singleton
    fun providesNotificationManager(): NotificationManager = Mockito.mock(NotificationManager::class.java)

    @Provides
    @Singleton
    fun providesAuth(): Auth = TestAuthManager()

    @Provides
    @Singleton
    fun providesNetwork(): Network = TestNetworkManager()

    @Provides
    @Singleton
    fun providesMessages(): Messages = TestMessagesProvider()

    @Provides
    @Singleton
    fun providesRepo(): Repo = TestRepository()
}