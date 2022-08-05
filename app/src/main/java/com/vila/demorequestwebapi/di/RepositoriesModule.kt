package com.vila.demorequestwebapi.di

import com.vila.demorequestwebapi.data.repositories.WebRepositoryImp
import com.vila.demorequestwebapi.domain.repositories.WebRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

    @Binds
    abstract fun providesWebRepository(webRepositoryImp: WebRepositoryImp)
            : WebRepository

}