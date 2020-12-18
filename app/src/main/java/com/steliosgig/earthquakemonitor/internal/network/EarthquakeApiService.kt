package com.steliosgig.earthquakemonitor.internal.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET


const val BASE_URL = "http://www.gein.noa.gr/"
const val MONTHLY_NEW = "services/monthlynew-list_el.php"

private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

private val okHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .build()


interface EarthquakeApiService {

    @GET(MONTHLY_NEW)
    suspend fun getEarthquakes(): String
}

object EarthquakeApi {
    val retrofitService: EarthquakeApiService by lazy { retrofit.create(EarthquakeApiService::class.java) }
}