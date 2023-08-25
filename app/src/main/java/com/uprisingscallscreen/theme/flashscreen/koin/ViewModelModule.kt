package com.uprisingscallscreen.theme.flashscreen.koin

import com.uprisingscallscreen.theme.flashscreen.viewmodel.FavViewModel
import com.uprisingscallscreen.theme.flashscreen.viewmodel.ThemesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        ThemesViewModel(get())
    }

    viewModel {
        FavViewModel(get())
    }


}
