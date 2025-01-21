package org.example.project

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import org.example.domain.model.Movie

@Composable
fun MovieCard(movie: Movie) {
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(16.dp)
            .clickable { showDialog = true },
        backgroundColor = MaterialTheme.colors.surface,
        shape = RoundedCornerShape(size = 12.dp),
    ) {
        Column {
            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://image.tmdb.org/t/p/w500/${movie.posterPath}")
                    .crossfade(true)
                    .build(),
                contentDescription = "Movie Image",
                contentScale = ContentScale.Crop,
            )
            Column(modifier = Modifier.padding(all = 10.dp)) {
                Text(
                    text = movie.title ?: "",
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    modifier = Modifier.padding(bottom = 8.dp),
                    text = movie.overview ?: "",
                    maxLines = 6,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(
                    text = movie.title ?: "",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                )
            },
            text = {
                Text(
                    modifier = Modifier.padding(bottom = 8.dp),
                    text = movie.overview ?: "",
                )
            },
            confirmButton = {
                Text(
                    text = "Close",
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { showDialog = false },
                )
            },
        )
    }
}
