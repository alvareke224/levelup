package com.example.ejemplomvvm.data.network

import com.example.ejemplomvvm.data.Giveaway
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface GamerPowerApiService {
    @GET("giveaways")
    suspend fun getGiveaways(): List<Giveaway>
}

object GamerPowerApi {
    private const val BASE_URL = "https://www.gamerpower.com/api/"

    val retrofitService: GamerPowerApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GamerPowerApiService::class.java)
    }
}