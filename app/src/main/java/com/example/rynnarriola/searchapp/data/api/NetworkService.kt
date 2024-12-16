package com.example.rynnarriola.searchapp.data.api

import com.example.rynnarriola.searchapp.data.model.FlickrResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    companion object{
        const val BASE_URL = "https://api.flickr.com/"
    }

    @GET("services/feeds/photos_public.gne")
    suspend fun getPhotos(
        @Query("tags") tags: String,
        @Query("format") format: String = "json",
        @Query("nojsoncallback") noJsonCallback: Int = 1
    ): FlickrResponse
}