package com.uprisingscallscreen.theme.flashscreen.koin


import com.uprisingscallscreen.theme.flashscreen.db.ThemeRepo
import org.koin.dsl.module

val ListModules = module {


    single {
        ThemeRepo(get())
    }

    single {
    }


}

