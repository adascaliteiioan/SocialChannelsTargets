package com.example.rinfassignement.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.rinfassignement.data.api.ChannelsApi
import com.example.rinfassignement.data.db.database.ChannelsDatabase
import com.example.rinfassignement.util.PREFS_NAME
import com.example.rinfassignement.util.PrefsHelper
import com.example.rinfassignement.util.UpdateManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(ChannelsApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideChannelApi(retrofit: Retrofit): ChannelsApi =
        retrofit.create(ChannelsApi::class.java)

    @Singleton
    @Provides
    fun provideChannelDatabase(app: Application): ChannelsDatabase =
        Room.databaseBuilder(app, ChannelsDatabase::class.java, "channel_db")
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideSharedPrefs(app: Application): SharedPreferences = app.getSharedPreferences(
        PREFS_NAME, Context.MODE_PRIVATE
    )

    @Singleton
    @Provides
    fun providesPrefsHelper(prefs: SharedPreferences): PrefsHelper = PrefsHelper(prefs)

    @Singleton
    @Provides
    fun providesUpdateManager(prefsHelper: PrefsHelper): UpdateManager = UpdateManager(prefsHelper)
}