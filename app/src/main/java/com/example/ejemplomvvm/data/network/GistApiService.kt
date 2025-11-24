package com.example.ejemplomvvm.data.network

import com.example.ejemplomvvm.data.Product
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface GistApiService {
    @GET("b78fdc06ef0a92a718fc7143bf6ade197434898b/gistfile1.txt")
    suspend fun getProducts(): List<Product>
}

object GistApi {
    private const val BASE_URL = "https://gist.githubusercontent.com/bellito00/11d7c6c2389238690f28dd3e47131554/raw/"

    val retrofitService: GistApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GistApiService::class.java)
    }
}