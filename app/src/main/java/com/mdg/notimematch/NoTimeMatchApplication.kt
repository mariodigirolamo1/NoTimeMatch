package com.mdg.notimematch

import android.app.Application
import com.mdg.notimematch.localdb.LocalDB
import com.mdg.notimematch.localdb.di.RoomDB
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class NoTimeMatchApplication: Application(){

    @Inject
    @RoomDB
    lateinit var localDB: LocalDB

    override fun onCreate() {
        super.onCreate()
        localDB.setup(applicationContext = applicationContext)
    }
}