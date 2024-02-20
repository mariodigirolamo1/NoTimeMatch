package com.mdg.notimematch.closet

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mdg.notimematch.localdb.LocalDB
import com.mdg.notimematch.localdb.di.RoomDB
import com.mdg.notimematch.localdb.room.entity.Garment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "ClosetViewModel"

@HiltViewModel
class ClosetViewModel @Inject constructor(
    @RoomDB val localDB: LocalDB
) : ViewModel() {
    private var _garments = MutableStateFlow<List<Garment>>(ArrayList())
    val garments = _garments.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _garments.tryEmit(localDB.getAllGarments())
        }
    }

    fun fetchGarments(){
        viewModelScope.launch(Dispatchers.IO) {
            _garments.tryEmit(localDB.getAllGarments())
        }
    }
}