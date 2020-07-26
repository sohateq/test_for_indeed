package com.akameko.testforindeed.repository.retrofit

import com.akameko.testforindeed.repository.pojos.Jeans
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class Repository {
    private val retrofit: Retrofit
    private val api: JeansApi

    val jeans: Single<List<Jeans>>
        get() = api.jeans

    init {
        retrofit = Retrofit.Builder()
                .baseUrl("https://static.upstarts.work/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        api = retrofit.create(JeansApi::class.java)
    }
}