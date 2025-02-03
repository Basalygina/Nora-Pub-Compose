package com.blumenstreetdoo.nora_pub.di

import EventNewsRepositoryImpl
import com.blumenstreetdoo.nora_pub.data.FavoriteBeerRepositoryImpl
import com.blumenstreetdoo.nora_pub.data.MenuRepositoryImpl
import com.blumenstreetdoo.nora_pub.domain.api.EventNewsRepository
import com.blumenstreetdoo.nora_pub.domain.api.MenuRepository
import com.blumenstreetdoo.nora_pub.domain.favorite.FavoriteBeerRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<EventNewsRepository> {
        EventNewsRepositoryImpl()
    }
    single<MenuRepository> {
        MenuRepositoryImpl()
    }

    single<FavoriteBeerRepository> { FavoriteBeerRepositoryImpl(get()) }
}
