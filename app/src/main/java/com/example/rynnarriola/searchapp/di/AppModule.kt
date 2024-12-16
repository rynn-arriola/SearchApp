package com.example.rynnarriola.searchapp.di

import com.example.rynnarriola.searchapp.data.api.NetworkService
import com.example.rynnarriola.searchapp.data.repository.FlickrRepo
import com.example.rynnarriola.searchapp.util.DefaultDispatcherProvider
import com.example.rynnarriola.searchapp.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFlickrApi(): NetworkService {
        return Retrofit.Builder()
            .baseUrl(NetworkService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NetworkService::class.java)
    }

    fun provideRepo(networkService: NetworkService): FlickrRepo{
        return FlickrRepo(networkService)
    }

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()
}