package com.mdg.notimematch.screens.closet

import com.mdg.notimematch.localdb.room.entity.Garment

sealed interface ClosetViewState {
    data object Loading: ClosetViewState
    data class Ready(
        val garments: ArrayList<Garment>
    ): ClosetViewState
}