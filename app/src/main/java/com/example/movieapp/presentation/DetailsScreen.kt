package com.example.movieapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import java.util.Locale

@Composable
fun DetailsScreen(
    viewModel: DetailsScreenViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onMovieClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var expanded by remember { mutableStateOf(false) }
    val tabs = listOf("Trailers", "More Like This", "Comments")
    Column(modifier = Modifier.verticalScroll(rememberScrollState()))
    {
        Box(modifier = Modifier.height(height = 300.dp)) {
            AsyncImage(
                model = uiState.movieDetails?.backdropPath?.let {
                    "https://image.tmdb.org/t/p/w1280$it"
                },
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Spacer(modifier = Modifier.height(40.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
                    .statusBarsPadding(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Icon(
                    imageVector = Icons.Filled.Cast,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(25.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = uiState.movieDetails?.title ?: "",
                color = Color(0xFF212121),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = Icons.Outlined.Bookmark,
                contentDescription = "Bookmark",
                tint = Color(0xFF212121)
            )

            Icon(
                imageVector = Icons.AutoMirrored.Outlined.Send,
                contentDescription = "Share",
                tint = Color(0xFF212121)
            )

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 20.dp, bottom = 20.dp)
                .horizontalScroll(rememberScrollState()),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = uiState.movieDetails?.voteAverage.toString().take(3) ?: "",
                color = Color.Black,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowRight,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(18.dp)
            )
            Text(text = uiState.movieDetails?.releaseDate?.take(4) ?: "", color = Color.Black)
            InfoChip(text = uiState.movieDetails?.certification ?: "", color = Color.Black)
            InfoChip(text = uiState.movieDetails?.releaseCountry ?: "", color = Color.Black)
            InfoChip(text = "Subtitle", color = Color.Black)
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
                    containerColor = Color(0xFFE21221),
                    contentColor = Color.White
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
                        color = Color(0xFFE21221),
                        shape = RoundedCornerShape(50)
                    ),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color(0xFFE21221)
                )
            ) {
                Icon(imageVector = Icons.Filled.Download, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Download", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            }
        }
        Text(
            text = "Genre: " + (uiState.movieDetails?.genres?.joinToString(", ") { it.name } ?: ""),
            color = Color.Black,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 12.sp,
            modifier = Modifier.padding(20.dp),
            fontWeight = FontWeight(500)
        )
        Box(
            modifier = Modifier
                .clickable { expanded = !expanded }
                .padding(horizontal = 20.dp)) {
            Text(
                text = uiState.movieDetails?.overview ?: "",
                color = Color(0xFF424242),
                maxLines = if (expanded) Int.MAX_VALUE else 3,
                overflow = TextOverflow.Clip,
                fontSize = 12.sp, lineHeight = 14.sp
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
            contentColor = Color(0xFF9E9E9E),
            modifier = Modifier.padding(horizontal = 20.dp),
            indicator = {
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(uiState.selectedTabIndex),
                    height = 3.dp,
                    color = Color(0xFFE21221)
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selectedContentColor = Color(0xFFE21221),
                    unselectedContentColor = Color(0xFF9E9E9E),
                    selected = uiState.selectedTabIndex == index,
                    onClick = { viewModel.updateTabIndex(index) },
                    text = {
                        Text(
                            text = title,
                            fontSize = 14.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Visible,
                            softWrap = false,
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

            1 -> Column(modifier = Modifier.padding(top = 20.dp, start = 20.dp, end = 20.dp)) {
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
            model =
                "https://image.tmdb.org/t/p/w1280$img",
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
                color = Color(0xFF212121),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 10.sp

            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = role,
                fontSize = 12.sp,
                color = Color(0xFF616161),
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
            color = Color(0xFF212121)
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
                .background(color = Color.Red, shape = RoundedCornerShape(6.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                text = String.format(Locale.US, "%.1f", rating),
                color = Color.White,
                fontSize = 12.sp
            )
        }
    }
}