package com.blumenstreetdoo.nora_pub.di

import org.koin.dsl.module
import com.blumenstreetdoo.nora_pub.ui.home.HomeViewModel
import com.blumenstreetdoo.nora_pub.ui.profile.FavoriteViewModel
import com.blumenstreetdoo.nora_pub.ui.craft.CraftViewModel
import com.blumenstreetdoo.nora_pub.ui.profile.ProfileViewModel
import com.blumenstreetdoo.nora_pub.ui.reservation.ReservationViewModel
import org.koin.core.module.dsl.viewModel

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { CraftViewModel(get()) }
    viewModel { ReservationViewModel() }
    viewModel { ProfileViewModel(get()) }
}
