package com.example.movieapp.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import java.util.Locale

@Composable
fun FavouriteScreen(
    viewModel: FavouriteScreenViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onItemCLick: (Int, String) -> Unit,
    onLoginRequired: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        if (!viewModel.isAlreadyLogin()) onLoginRequired()
    }
    val context = LocalContext.current
    LaunchedEffect(uiState.logoutError) {
        if (uiState.logoutError.isNotEmpty()) {
            Toast.makeText(context, uiState.logoutError, Toast.LENGTH_SHORT).show()
            viewModel.clearLogoutError()
        }
    }
    if (!viewModel.isAlreadyLogin()) return
    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "My Bookmarks",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
            IconButton(
                onClick = { viewModel.logoutUser(onLoggedOut = onLoginRequired) },
                enabled = !uiState.isLoggingOut
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Logout,
                    contentDescription = "Logout"
                )
            }
        }

        when {
            uiState.isLoading -> {
               FavouriteScreenShimmer()
            }

            uiState.movies.isEmpty() -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No favourites yet")
                }
            }

            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.movies, key = { it.movieId }) { movie ->
                        FavouriteItem(
                            rating = movie.voteAverage,
                            posterPath = movie.posterPath ?: "",
                            onClick = { onItemCLick(movie.movieId, movie.mediaType) },
                            onDeleteClick = { viewModel.removeFavorite(movie.movieId, movie.mediaType) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FavouriteItem(
    rating: Double,
    posterPath: String,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2f / 3f)
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500$posterPath",
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .padding(8.dp)
                .background(color = Color.Red, shape = RoundedCornerShape(6.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                text = String.format(Locale.US, "%.1f", rating),
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 12.sp
            )
        }
        IconButton(
            onClick = onDeleteClick,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(4.dp)
                .size(32.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Remove from favorites",
                tint = Color(0xFFE21221)
            )
        }
    }
}