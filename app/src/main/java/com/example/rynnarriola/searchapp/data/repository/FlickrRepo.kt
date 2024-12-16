package com.example.rynnarriola.searchapp.data.repository

import com.example.rynnarriola.searchapp.data.api.NetworkService
import com.example.rynnarriola.searchapp.data.model.Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FlickrRepo @Inject constructor(
    private val networkService: NetworkService
) {

    fun getSearchFlickr(tags: String): Flow<List<Item>> {
        return flow {
            emit(networkService.getPhotos(tags = tags))
        }.map {
            it.items
        }
    }
}