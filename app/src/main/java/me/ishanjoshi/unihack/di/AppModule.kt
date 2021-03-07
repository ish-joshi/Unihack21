package me.ishanjoshi.unihack.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideName() = "Ishan"

    @Singleton
    @Provides
    fun provideNumber(): Int = 100

}