package com.mdg.notimematch.closet

import androidx.lifecycle.ViewModel
import com.mdg.notimematch.localdb.LocalDB
import com.mdg.notimematch.localdb.di.RoomDB
import com.mdg.notimematch.localdb.room.entity.Garment
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ClosetViewModel @Inject constructor(
    @RoomDB val localDB: LocalDB
) : ViewModel() {
    fun getAllGarments(): List<Garment> {
        return localDB.getAllGarments()
    }

    fun takePicture(): Unit {
        // TODO: launch camera
    }
}