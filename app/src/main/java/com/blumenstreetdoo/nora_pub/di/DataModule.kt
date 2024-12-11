package ru.practicum.android.diploma.di

import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {

  //  single<Retrofit> {
  //      Retrofit.Builder()
  //          .baseUrl(HHBaseUrl)
  //          .addConverterFactory(GsonConverterFactory.create())
  //          .client(get())
  //          .build()
  //  }
//
  //  single<HhApi> {
  //      get<Retrofit>().create(HhApi::class.java)
  //  }
//
  //  single<NetworkClient> {
  //      HHApiClient(get(), get())
  //  }
//
  //  single {
  //      Room.databaseBuilder(androidContext(), AppDataBase::class.java, "app_database.db")
  //          .fallbackToDestructiveMigration()
  //          .build()
  //  }
//
  //  factory<Gson> { Gson() }
//
  //  factory<OkHttpClient> {
  //      Builder()
  //          .addInterceptor {
  //              val original = it.request()
  //              val request = original.newBuilder()
  //                  .header("Authorization", "Bearer $HH_ACCESS_TOKEN")
  //                  .header("HH-User-Agent", "YP Diploma Project (vaszol@mail.ru)")
  //                  .method(original.method(), original.body())
  //                  .build()
  //              it.proceed(request)
  //          }.build()
  //  }
//
  //  single { get<AppDataBase>().getFavoriteVacancyDao() }
//
  //  single<SharedPreferences> {
  //      get<Context>().getSharedPreferences("practicum_android_diploma_preferences", Context.MODE_PRIVATE)
  //  }
//
  //  single<SharedPreferencesConverter> {
  //      SharedPreferencesConverterImpl(gson = get())
  //  }
}
