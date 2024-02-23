package com.mdg.notimematch.screens.confirmphoto

import androidx.palette.graphics.Palette

sealed interface ConfirmPhotoViewState {
    data object Loading: ConfirmPhotoViewState
    data class Ready(
        val palette: Palette,
        var selectedColor: Int
    ) : ConfirmPhotoViewState
}