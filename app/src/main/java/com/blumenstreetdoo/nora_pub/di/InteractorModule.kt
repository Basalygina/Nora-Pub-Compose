package com.blumenstreetdoo.nora_pub.di

import com.blumenstreetdoo.nora_pub.domain.api.EventNewsInteractor
import com.blumenstreetdoo.nora_pub.domain.api.MenuInteractor
import com.blumenstreetdoo.nora_pub.domain.impl.EventNewsInteractorImpl
import com.blumenstreetdoo.nora_pub.domain.impl.MenuInteractorImpl
import org.koin.dsl.module

val interactorModule = module {
    factory<EventNewsInteractor> {
        EventNewsInteractorImpl(get())
    }
    factory<MenuInteractor> {
        MenuInteractorImpl(get())
    }
}
