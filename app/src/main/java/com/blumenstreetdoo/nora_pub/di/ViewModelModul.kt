package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import com.blumenstreetdoo.nora_pub.ui.home.HomeViewModel
import com.blumenstreetdoo.nora_pub.ui.favorite.FavoriteViewModel
import com.blumenstreetdoo.nora_pub.ui.craft.CraftViewModel
import com.blumenstreetdoo.nora_pub.ui.craft.beerOnTap.BeerOnTapViewModel
import com.blumenstreetdoo.nora_pub.ui.craft.cansInFridge.CansInFridgeViewModel

val viewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::FavoriteViewModel)
    viewModelOf(::CraftViewModel)
    viewModelOf(::BeerOnTapViewModel)
    viewModelOf(::CansInFridgeViewModel)
}
