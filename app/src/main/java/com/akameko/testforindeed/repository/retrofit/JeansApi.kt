package com.akameko.testforindeed.repository.retrofit

import androidx.room.Dao
import com.akameko.testforindeed.repository.pojos.Jeans
import io.reactivex.Single
import retrofit2.http.GET

@Dao
interface JeansApi {
    @get:GET("tests/jeans/jeans-default.json")
    var jeans: Single<List<Jeans>>
}