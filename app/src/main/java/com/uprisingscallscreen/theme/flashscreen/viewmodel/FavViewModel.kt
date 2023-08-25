package com.uprisingscallscreen.theme.flashscreen.viewmodel

import androidx.lifecycle.ViewModel
import com.uprisingscallscreen.theme.flashscreen.db.ThemeRepo
import com.uprisingscallscreen.theme.flashscreen.models.ThemesModel

class FavViewModel(var themesRepo: ThemeRepo) : ViewModel() {


    fun adThemeToFav(item: ThemesModel, favStatus: (Boolean) -> Unit) {

        themesRepo.addToFav(item, favStatus = { status ->
            favStatus.invoke(status)

        })


    }




}