package ru.practicum.android.diploma.di

import EventNewsRepositoryImpl
import com.blumenstreetdoo.nora_pub.data.MenuRepositoryImpl
import com.blumenstreetdoo.nora_pub.domain.api.EventNewsRepository
import com.blumenstreetdoo.nora_pub.domain.api.MenuRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<EventNewsRepository> {
        EventNewsRepositoryImpl()
    }
    single<MenuRepository> {
        MenuRepositoryImpl()
    }
    //single<SharedPreferencesRepository> {
    //    SharedPreferencesRepositoryImpl(get(), get())
    //}
}
