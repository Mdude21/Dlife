package com.example.developerslife.Networking.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GifItems(
    @SerialName("result")
    val list: List<GifItem?>
)
