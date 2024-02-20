package com.mdg.notimematch.screens.garmentdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mdg.notimematch.localdb.LocalDB
import com.mdg.notimematch.localdb.di.RoomDB
import com.mdg.notimematch.localdb.room.entity.Garment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GarmentDetailsViewModel @Inject constructor(
    @RoomDB
    val localDB: LocalDB
) : ViewModel() {
    private var _garment = MutableStateFlow<Garment?>(null)
    val garment = _garment.asStateFlow()

    fun fetchGarment(garmentId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            localDB.getGarmentById(uid = garmentId)
        }
    }
}