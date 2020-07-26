package com.akameko.testforindeed.dagger

import android.app.Application
import androidx.room.Room
import com.akameko.testforindeed.repository.room.JeansDao
import com.akameko.testforindeed.repository.room.JeansDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule(application: Application) {
    private val db: JeansDatabase
    @Singleton
    @Provides
    fun provideJeansDatabase(): JeansDatabase {
        return db
    }

    @Singleton
    @Provides
    fun provideJeansDao(jeansDatabase: JeansDatabase?): JeansDao {
        return db.jeansDao
    }

    init {
        db = Room.databaseBuilder(application.applicationContext, JeansDatabase::class.java, "database").allowMainThreadQueries().build()
    }
}