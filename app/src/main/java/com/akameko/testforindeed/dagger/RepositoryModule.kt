package com.akameko.testforindeed.dagger

import com.akameko.testforindeed.repository.retrofit.Repository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @Provides
    fun provideRepository(): Repository {
        return Repository()
    }
}