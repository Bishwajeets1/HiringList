package com.example.listdummy.network

import com.example.listdummy.model.HiringDataClass
import javax.inject.Inject

class HiringRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getHiringList(): Resource<List<HiringDataClass>> {
        return try {
            val response = apiService.getHiringList()
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error("Failed to fetch countries", e)
        }
    }
}