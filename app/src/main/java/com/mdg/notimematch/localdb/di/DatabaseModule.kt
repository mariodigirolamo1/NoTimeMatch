package com.mdg.notimematch.localdb.di

import com.mdg.notimematch.localdb.LocalDB
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
    @RoomDB
    @Provides
    fun provideRoomDB(): LocalDB = RoomDBWrapper()
}

annotation class RoomDB