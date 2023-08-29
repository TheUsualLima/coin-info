package com.example.coininfo.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Coin(
    @JsonProperty("id") val id: String,
    @JsonProperty("name") val name: String,
    @JsonProperty("symbol") val symbol: String,
    @JsonProperty("rank") val rank: Int,
    @JsonProperty("is_new") val isNew: Boolean,
    @JsonProperty("is_active") val isActive: Boolean,
    @JsonProperty("type") val type: String,
    @JsonProperty("logo") val logoUrl: String? = null,
    @JsonProperty("tags") val tags: List<Tag>? = null,
    @JsonProperty("team") val team: List<Team>? = null,
    @JsonProperty("description") val description: String? = null,
    @JsonProperty("message") val message: String? = null,
    @JsonProperty("open_source") val isOpenSource: Boolean? = null,
    @JsonProperty("started_at") val dateStarted: String? = null,
    @JsonProperty("development_status") val developmentStatus: String? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Tag(
    @JsonProperty("id") val id: String,
    @JsonProperty("name") val name: String,
    @JsonProperty("description") val description: String?,
    @JsonProperty("type") val type: String?,
    @JsonProperty("coin_counter") val coinCounter: Int,
    @JsonProperty("ico_counter") val icoCounter: Int
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Team(
    @JsonProperty("id") val id: String,
    @JsonProperty("name") val name: String,
    @JsonProperty("position") val position: String
)
