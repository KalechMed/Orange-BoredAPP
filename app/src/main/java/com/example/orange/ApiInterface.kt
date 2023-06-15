package com.example.testtt

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET



data class ActivityResponse(
    val activity: String,
    val type: String,
    val participants: Int,
    val price: Double,
    val link: String?,
    val key: String,
    val accessibility: Double
)


// Define the API service interface
interface ApiService {
    @GET("api/activity")
    suspend fun getActivity(): ActivityResponse

}




object ApiClient {
    private const val BASE_URL = "https://www.boredapi.com/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}


