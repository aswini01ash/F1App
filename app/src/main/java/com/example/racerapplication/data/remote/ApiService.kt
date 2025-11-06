package com.example.racerapplication.data.remote

import com.example.racerapplication.data.model.DriverResponse
import com.example.racerapplication.data.model.RaceResponse
import retrofit2.http.GET

interface ApiService {
    @GET("v1/e8616da8-220c-4aab-a670-ab2d43224ecb")
    suspend fun getDrivers(): DriverResponse

    @GET("v1/9086a3f1-f02b-4d24-8dd3-b63582f45e67")
    suspend fun getRaces(): RaceResponse
}