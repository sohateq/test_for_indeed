package com.akameko.testforindeed.repository.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.akameko.testforindeed.repository.pojos.Jeans

@Dao
interface JeansDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(jeanses: List<Jeans?>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(jeans: Jeans?)

    @Delete
    fun delete(jeans: Jeans?)

    @Query("DELETE FROM jeans")
    fun deleteAll()

    @get:Query("SELECT * FROM jeans")
    val allItems:  List<Jeans?>?

    @get:Query("SELECT * FROM jeans")
    val allItemsLiveData: LiveData<List<Jeans>>

    @Query("SELECT * FROM jeans WHERE id == :id")
    fun getItemById(id: Int?): Jeans?
}