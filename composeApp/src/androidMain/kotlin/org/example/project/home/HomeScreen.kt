package org.example.project.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.project.ProductCard

@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = viewModel()
    val request by viewModel.requestState

    when {
        request.isLoading() -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }
        request.isSuccess() -> {
            LazyColumn(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                items(
                    request.getProducts().items,
                    key = { it.id },
                ) {
                    ProductCard(product = it)
                }
            }
        }
        request.isError() -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 24.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = request.getErrorMessage())
            }
        }
    }

    if (request.isLoading()) {
    }
}
