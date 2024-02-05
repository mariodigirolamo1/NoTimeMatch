package com.mdg.notimematch.localdb.di

import com.mdg.notimematch.localdb.room.RoomDBWrapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    @RoomDB
    fun provideRoomDB(): RoomDBWrapper = RoomDBWrapper()
}

annotation class RoomDB