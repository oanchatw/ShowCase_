package com.example.showcase_.Api

import com.example.showcase_.Model.Model
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

interface ApiService {

    @GET("/posts")
    fun hitCountCheck(): Observable<List<Model.UserItem>>

    companion object {
        fun create(): ApiService {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://jsonplaceholder.typicode.com")
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }

}