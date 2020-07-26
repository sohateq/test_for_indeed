package com.akameko.testforindeed.repository.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.akameko.testforindeed.repository.pojos.Jeans

@Database(entities = [Jeans::class], version = 1, exportSchema = false)
abstract class JeansDatabase : RoomDatabase() {
    abstract val jeansDao: JeansDao
}