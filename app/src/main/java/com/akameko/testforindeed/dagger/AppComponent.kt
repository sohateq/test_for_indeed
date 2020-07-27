package com.akameko.testforindeed.dagger

import android.app.Application
import com.akameko.testforindeed.repository.retrofit.Repository
import com.akameko.testforindeed.repository.room.JeansDatabase
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(dependencies = [], modules = [AppModule::class, RoomModule::class, RepositoryModule::class])
interface AppComponent {

    val jeansDatabase: JeansDatabase

    val repository: Repository

    val application: Application?
}