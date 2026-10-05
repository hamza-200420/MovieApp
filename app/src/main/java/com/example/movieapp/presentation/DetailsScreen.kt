package com.example.movieapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.filled.Cast
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    viewModel: DetailsScreenViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onMovieClick: (Int) -> Unit,
    onLoginRequired: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var expanded by remember { mutableStateOf(false) }
    val tabs = listOf("Trailers", "More Like This", "Comments")
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(uiState.showLoginPrompt) {
        if (uiState.showLoginPrompt) {
            try {
                val result = snackbarHostState.showSnackbar(
                    message = "Please log in to save favourites",
                    actionLabel = "Login",
                    duration = SnackbarDuration.Short
                )
                if (result == SnackbarResult.ActionPerformed) onLoginRequired()
            } finally {
                viewModel.onLoginPromptShown()
            }
        }
    }

    if (uiState.isLoading) {
       DetailsScreenShimmer()
    } else {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            containerColor = MaterialTheme.colorScheme.background,
            topBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .drawBehind {
                            drawRect(
                                Color(0xFF212121).copy(alpha = scrollBehavior.state.collapsedFraction)
                            )
                        }
                ) {
                    AsyncImage(
                        model = uiState.movieDetails?.backdropPath?.let {
                            "https://image.tmdb.org/t/p/w1280$it"
                        },
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .matchParentSize()
                            .graphicsLayer { alpha = 1f - scrollBehavior.state.collapsedFraction }
                    )
                    LargeTopAppBar(
                        title = {
                            Text(
                                text = uiState.movieDetails?.title ?: "",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.graphicsLayer {
                                    alpha =
                                        ((scrollBehavior.state.collapsedFraction - 0.5f) * 2f).coerceIn(
                                            0f,
                                            1f
                                        )
                                }
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = onNavigateBack) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        },
                        actions = {
                            Icon(
                                imageVector = Icons.Filled.Cast,
                                contentDescription = null,
                                modifier = Modifier.padding(12.dp)
                            )
                        },
                        expandedHeight = 300.dp,
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent,
                            scrolledContainerColor = Color.Transparent,
                            titleContentColor = Color.White,
                            navigationIconContentColor = Color.White,
                            actionIconContentColor = Color.White
                        ),
                        scrollBehavior = scrollBehavior
                    )
                }
            }
        ) { innerPadding ->
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, start = 20.dp, end = 20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = uiState.movieDetails?.title ?: "",
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = { viewModel.toggleIcon() }) {
                            Icon(
                                imageVector = if (uiState.isSaved) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = if (uiState.isSaved) "Remove from favorites" else "Add to favorites",
                                tint = if (uiState.isSaved) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.Send,
                            contentDescription = "Share",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, start = 20.dp, bottom = 20.dp)
                            .horizontalScroll(rememberScrollState()),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = uiState.movieDetails?.voteAverage.toString().take(3),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowRight,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = uiState.movieDetails?.releaseDate?.take(4) ?: "",
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        InfoChip(
                            text = uiState.movieDetails?.certification ?: "",
                            MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        InfoChip(
                            text = uiState.movieDetails?.releaseCountry ?: "",
                            MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        InfoChip(text = "Subtitle", MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = { },
                            modifier = Modifier
                                .weight(1f)
                                .height(52.dp),
                            shape = RoundedCornerShape(50),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            Icon(imageVector = Icons.Filled.PlayCircle, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Play", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                        }
                        Button(
                            onClick = { },
                            modifier = Modifier
                                .weight(1f)
                                .height(52.dp)
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(50)
                                ),
                            shape = RoundedCornerShape(50),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        ) {
                            Icon(imageVector = Icons.Filled.Download, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Download",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )
                        }
                    }
                    Text(
                        text = "Genre: " + (uiState.movieDetails?.genres?.joinToString(", ") { it.name }
                            ?: ""),
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(20.dp),
                        fontWeight = FontWeight(500)
                    )
                    Box(
                        modifier = Modifier
                            .clickable { expanded = !expanded }
                            .padding(horizontal = 20.dp)
                    ) {
                        Text(
                            text = uiState.movieDetails?.overview ?: "",
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = if (expanded) Int.MAX_VALUE else 3,
                            overflow = TextOverflow.Clip,
                            fontSize = 12.sp,
                            lineHeight = 14.sp
                        )
                        if (!expanded) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .background(Color.White)
                                    .padding(start = 4.dp)
                            ) {
                                Text(
                                    text = "... View More",
                                    color = Color.Red,
                                    fontSize = 12.sp,
                                    lineHeight = 14.sp
                                )
                            }
                        }
                    }
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(15.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 20.dp)
                    ) {
                        items(uiState.movieDetails?.cast ?: emptyList()) { cast ->
                            CastMember(
                                img = cast.profilePath ?: "",
                                name = cast.name,
                                role = cast.character ?: ""
                            )
                        }
                    }
                    SecondaryTabRow(
                        selectedTabIndex = uiState.selectedTabIndex,
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.padding(horizontal = 20.dp),
                        indicator = {
                            TabRowDefaults.SecondaryIndicator(
                                modifier = Modifier.tabIndicatorOffset(uiState.selectedTabIndex),
                                height = 3.dp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    ) {
                        tabs.forEachIndexed { index, title ->
                            Tab(
                                selectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedContentColor = MaterialTheme.colorScheme.tertiary,
                                selected = uiState.selectedTabIndex == index,
                                onClick = { viewModel.updateTabIndex(index) },
                                text = {
                                    Text(
                                        text = title,
                                        fontSize = 14.sp,
                                        maxLines = 1,
                                        overflow = TextOverflow.Visible,
                                        softWrap = false
                                    )
                                }
                            )
                        }
                    }
                    when (uiState.selectedTabIndex) {
                        0 -> Column(modifier = Modifier.padding(top = 20.dp, start = 20.dp)) {
                            uiState.movieDetails?.trailers?.forEach { trailer ->
                                TrailersSection(vid = trailer.key, name = trailer.name)
                                Spacer(modifier = Modifier.height(12.dp))
                            }
                        }

                        1 -> Column(
                            modifier = Modifier.padding(
                                top = 20.dp,
                                start = 20.dp,
                                end = 20.dp
                            )
                        ) {
                            uiState.movieDetails?.similarMovies?.chunked(2)?.forEach { rowMovies ->
                                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                    rowMovies.forEach { movie ->
                                        Box(modifier = Modifier.weight(1f)) {
                                            MoreLikeThisItem(
                                                rating = movie.voteAverage,
                                                posterPath = movie.posterPath ?: "",
                                                onClick = { onMovieClick(movie.id) }
                                            )
                                        }
                                    }
                                    if (rowMovies.size == 1) {
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }
                                Spacer(modifier = Modifier.height(12.dp))
                            }
                        }

                        2 -> Text("Comments")
                    }
                }
                SnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier.align(Alignment.BottomCenter),
                )
            }
        }
    }
}

@Composable
fun InfoChip(text: String, color: Color) {
    Box(
        modifier = Modifier
            .border(width = 1.dp, color = color, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(text = text, color = color, fontSize = 10.sp)
    }
}

@Composable
fun CastMember(img: String, name: String, role: String) {
    Row(modifier = Modifier.height(60.dp)) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w1280$img",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            modifier = Modifier
                .width(60.dp)
                .padding(top = 5.dp)
        ) {
            Text(
                text = name,
                fontSize = 10.sp,
                fontWeight = FontWeight(600),
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 10.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = role,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.tertiary,
                fontWeight = FontWeight(400),
                lineHeight = 12.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun TrailersSection(vid: String, name: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(width = 150.dp, height = 112.dp)
                .clip(RoundedCornerShape(8.dp))
        ) {
            AsyncImage(
                model = "https://img.youtube.com/vi/$vid/0.jpg",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Icon(
                imageVector = Icons.Filled.PlayCircle,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = name,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun MoreLikeThisItem(rating: Double, posterPath: String, onClick: () -> Unit) {
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
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(6.dp)
                )
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                text = String.format(Locale.US, "%.1f", rating),
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 12.sp
            )
        }
    }
}