package com.example.coininfo.data

import com.fasterxml.jackson.annotation.JsonProperty

data class Coin(
    @JsonProperty("id") val id: String,
    @JsonProperty("name") val name: String,
    @JsonProperty("symbol") val symbol: String,
    @JsonProperty("rank") val rank: Int,
    @JsonProperty("is_new") val isNew: Boolean,
    @JsonProperty("is_active") val isActive: Boolean,
    @JsonProperty("type") val type: String
)
