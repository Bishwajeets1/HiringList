package com.example.listdummy.network

import com.example.listdummy.model.HiringDataClass
import retrofit2.http.GET

interface ApiService {
    @GET("hiring.json")
    suspend fun getHiringList(): List<HiringDataClass>
}