package com.example.rynnarriola.searchapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.rynnarriola.searchapp.R
import com.example.rynnarriola.searchapp.data.model.Item
import com.example.rynnarriola.searchapp.ui.base.ShowError
import com.example.rynnarriola.searchapp.ui.base.ShowLoading
import com.example.rynnarriola.searchapp.util.UiState
import com.example.rynnarriola.searchapp.util.toHtmlFormattedString
import com.example.rynnarriola.searchapp.viewmodel.FlickrViewModel

@Composable
fun SearchScreen(
    viewModel: FlickrViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    Column(modifier = Modifier.padding(16.dp)) {
        // Search bar
        SearchBar(
            searchQuery = searchQuery,
            onSearch = { newQuery ->
                searchQuery = newQuery
                viewModel.searchImage(newQuery)
            }
        )

        // Display the UI state based on the provided data
        when (uiState) {
            is UiState.Success -> {
                ImageList((uiState as UiState.Success<List<Item>>).data)
            }

            is UiState.Loading -> {
                ShowLoading()
            }

            is UiState.Error -> {
                ShowError((uiState as UiState.Error).message)
            }
        }
    }

}


@Composable
fun SearchBar(searchQuery: String, onSearch: (String) -> Unit) {
    // You can customize the appearance of the search bar as needed
    // For simplicity, a basic TextField is used here
    // Modify it based on your design requirements
    TextField(
        value = searchQuery,
        onValueChange = {
            onSearch(it)
        },
        label = { Text(stringResource(R.string.search)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
fun ImageList(images: List<Item>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(images) { item ->
            SearchItemCard(item)
        }
    }
}

@Composable
fun SearchItemCard(item: Item) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        // Image
        val painter =
            rememberImagePainter(item.media.image) // Assuming media.m contains the image URL
        Image(
            painter = painter,
            contentDescription = "Image for ${item.title}",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Title
        Text(text =  item.title, style = MaterialTheme.typography.bodyLarge)

        // Description
        Text(text = item.description.toHtmlFormattedString(), style = MaterialTheme.typography.bodyMedium)

        // Author
        Text(text = item.author, style = MaterialTheme.typography.bodyMedium)

        // Publish Date
        Text(text = item.published, style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(16.dp))
    }
}