package com.example.ejemplomvvm.data

import com.example.ejemplomvvm.data.network.GamerPowerApi

class GiveawayRepository {
    suspend fun getGiveaways(): List<Giveaway> {
        return try {
            GamerPowerApi.retrofitService.getGiveaways()
        } catch (e: Exception) {
            // In a real app, handle the error appropriately (e.g., log it)
            emptyList()
        }
    }
}