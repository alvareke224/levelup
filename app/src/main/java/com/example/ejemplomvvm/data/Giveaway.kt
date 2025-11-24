package com.example.ejemplomvvm.data

import com.google.gson.annotations.SerializedName

data class Giveaway(
    val id: Int,
    val title: String,
    val image: String,
    val description: String,
    val platforms: String,
    @SerializedName("open_giveaway_url")
    val openGiveawayUrl: String
)