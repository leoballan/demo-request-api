package com.vila.demorequestwebapi.di

import com.vila.demorequestwebapi.data.MockWebApi
import com.vila.demorequestwebapi.util.Constants
import com.vila.demorequestwebapi.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun providesMockWebApi():MockWebApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(MockWebApi::class.java)
    }


}