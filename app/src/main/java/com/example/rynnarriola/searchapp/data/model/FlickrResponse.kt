package com.example.rynnarriola.searchapp.data.model

import com.google.gson.annotations.SerializedName

data class FlickrResponse(
    @SerializedName("description")
    val description: String,
    @SerializedName("generator")
    val generator: String,
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("link")
    val link: String,
    @SerializedName("modified")
    val modified: String,
    @SerializedName("title")
    val title: String
)