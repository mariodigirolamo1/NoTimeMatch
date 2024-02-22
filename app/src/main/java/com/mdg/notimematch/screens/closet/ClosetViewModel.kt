package com.mdg.notimematch.screens.closet

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
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
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

private const val TAG = "ClosetViewModel"

@HiltViewModel
class ClosetViewModel @Inject constructor(
    @RoomDB val localDB: LocalDB
) : ViewModel() {
    private var _viewState = MutableStateFlow<ClosetViewState>(ClosetViewState.Loading)
    val viewState = _viewState.asStateFlow()

    fun fetchGarments(){
        viewModelScope.launch(Dispatchers.IO) {
            _viewState.update { ClosetViewState.Ready(localDB.getAllGarments()) }
        }
    }
}