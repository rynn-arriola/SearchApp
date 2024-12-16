package com.example.rynnarriola.searchapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.rynnarriola.searchapp.R
import com.example.rynnarriola.searchapp.ui.base.Screen


@Composable
fun HomeScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {
                // Navigate to SearchScreen when clicked
                navController.navigate(Screen.SearchScreen.route)
            }
        ) {
            Text(text = stringResource(R.string.go_to_search))
        }
    }
}
