package com.example.movieapp.presentation

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.movieapp.R
import com.example.movieapp.domain.model.Category
import com.example.movieapp.domain.model.Genre
import com.example.movieapp.domain.model.MovieFilter
import com.example.movieapp.domain.model.Region
import com.example.movieapp.domain.model.SortOption
import java.time.Year
import java.util.Locale

private val availableYears = (Year.now().value downTo Year.now().value - 5).toList()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(
    viewModel: ExploreScreenViewModel = hiltViewModel(),
    onMovieClick: (Int) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lazyMovies = uiState.movies.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier.weight(1f),
                placeholder = { Text("Search", color = Color.Gray) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                },
                singleLine = true,
                enabled = false,
                shape = RoundedCornerShape(25),
                colors = OutlinedTextFieldDefaults.colors(
                    disabledBorderColor = Color.LightGray,
                    disabledPlaceholderColor = Color.Gray,
                    disabledLeadingIconColor = Color.Gray,
                    disabledContainerColor = Color.Transparent
                )
            )
            Spacer(modifier = Modifier.width(12.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFFF5F5F5))
                    .clickable { viewModel.onFilterIconClicked() }
                    .size(56.dp), contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.group),
                    contentDescription = "Filter",
                    tint = Color(0xFFE21221)
                )
            }
        }

        val activeChips = uiState.activeFilterLabels
        if (activeChips.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                activeChips.forEach { label ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(Color(0xFFE21221))
                            .padding(horizontal = 20.dp, vertical = 8.dp)
                            .height(height = 38.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = label, color = Color.White, fontSize = 16.sp)
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        when (val refreshState = lazyMovies.loadState.refresh) {
            is LoadState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is LoadState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(refreshState.error.localizedMessage ?: "Something went wrong")
                        Button(onClick = { lazyMovies.retry() }) {
                            Text("Retry")
                        }
                    }
                }
            }

            else -> {
                if (lazyMovies.itemCount == 0) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No results for this filter")
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(
                            count = lazyMovies.itemCount,
                            key = { index -> "${lazyMovies.peek(index)?.id}_$index" }
                        ) { index ->
                            val movie = lazyMovies[index]
                            if (movie != null) {
                                ExploreMovieItem(
                                    rating = movie.voteAverage,
                                    posterPath = movie.posterPath ?: "",
                                    isFavorite = movie.id in uiState.favoriteIds,
                                    onClick = { onMovieClick(movie.id) },
                                    onFavoriteClick = { viewModel.toggleFavorite(movie) }
                                )
                            }
                        }

                        when (val appendState = lazyMovies.loadState.append) {
                            is LoadState.Loading -> {
                                item(span = { GridItemSpan(maxLineSpan) }) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        contentAlignment = Alignment.Center
                                    ) { CircularProgressIndicator() }
                                }
                            }

                            is LoadState.Error -> {
                                item(span = { GridItemSpan(maxLineSpan) }) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(
                                                appendState.error.localizedMessage
                                                    ?: "Couldn't load more"
                                            )
                                            Button(onClick = { lazyMovies.retry() }) { Text("Retry") }
                                        }
                                    }
                                }
                            }

                            else -> {}
                        }
                    }
                }
            }
        }
    }

    if (uiState.isFilterSheetVisible) {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ModalBottomSheet(
            onDismissRequest = { viewModel.onDismissFilterSheet() },
            sheetState = sheetState, containerColor = Color.White,
            dragHandle = {
                Box(
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 24.dp)
                        .width(38.dp)
                        .height(3.dp)
                        .clip(RoundedCornerShape(50))
                        .background(Color(0xFFE0E0E0))
                )
            }
        ) {
            FilterSheetContent(
                draftFilter = uiState.draftFilter,
                availableRegions = uiState.availableRegions,
                availableGenres = uiState.availableGenres,
                onCategorySelected = { viewModel.onCategorySelected(it) },
                onClearRegions = { viewModel.onClearRegions() },
                onRegionToggled = { viewModel.onRegionToggled(it) },
                onClearGenres = { viewModel.onClearGenres() },
                onGenreToggled = { viewModel.onGenreToggled(it) },
                onYearToggled = { viewModel.onYearToggled(it) },
                onSortSelected = { viewModel.onSortSelected(it) },
                onReset = { viewModel.onResetFilters() },
                onApply = { viewModel.onApplyFilters() }
            )
        }
    }
}

@Composable
private fun FilterSheetContent(
    draftFilter: MovieFilter,
    availableRegions: List<Region>,
    availableGenres: List<Genre>,
    onCategorySelected: (Category) -> Unit,
    onClearRegions: () -> Unit,
    onRegionToggled: (Region) -> Unit,
    onClearGenres: () -> Unit,
    onGenreToggled: (Genre) -> Unit,
    onYearToggled: (Int?) -> Unit,
    onSortSelected: (SortOption) -> Unit,
    onReset: () -> Unit,
    onApply: () -> Unit
) {
    val sheetScrollConnection = rememberBottomSheetScrollConnection()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .nestedScroll(sheetScrollConnection)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp)
    ) {
        Text(
            text = "Sort & Filter",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFF75555),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(48.dp))

        FilterSectionTitle("Category")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(start = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Category.entries.forEach { category ->
                SelectableChip(
                    label = category.displayName,
                    selected = draftFilter.category == category,
                    onClick = { onCategorySelected(category) }
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        FilterSectionTitle("Regions")
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                SelectableChip(
                    label = "All Regions",
                    selected = draftFilter.regions.isEmpty(),
                    onClick = onClearRegions
                )
            }
            items(availableRegions, key = { it.isoCode }) { region ->
                SelectableChip(
                    label = region.englishName,
                    selected = region in draftFilter.regions,
                    onClick = { onRegionToggled(region) }
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        FilterSectionTitle("Genre")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(start = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SelectableChip(
                label = "All Genres",
                selected = draftFilter.genres.isEmpty(),
                onClick = onClearGenres
            )
            availableGenres.forEach { genre ->
                SelectableChip(
                    label = genre.name,
                    selected = genre in draftFilter.genres,
                    onClick = { onGenreToggled(genre) }
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        FilterSectionTitle("Time/Periods")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(start = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SelectableChip(
                label = "All Periods",
                selected = draftFilter.year == null,
                onClick = { onYearToggled(null) }
            )
            availableYears.forEach { year ->
                SelectableChip(
                    label = year.toString(),
                    selected = draftFilter.year == year,
                    onClick = { onYearToggled(year) }
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        FilterSectionTitle("Sort")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(start = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SortOption.entries.forEach { sort ->
                SelectableChip(
                    label = sort.displayName,
                    selected = draftFilter.sortBy == sort,
                    onClick = { onSortSelected(sort) }
                )
            }
        }
        Spacer(modifier = Modifier.height(48.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = onReset,
                modifier = Modifier
                    .weight(1f)
                    .size(height = 58.dp, width = 184.dp),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFCE7E9),
                    contentColor = Color(0xFFE21221)
                ),
            ) { Text("Reset", fontSize = 16.sp) }

            Button(
                onClick = onApply,
                modifier = Modifier
                    .weight(1f)
                    .size(height = 58.dp, width = 184.dp),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE21221),
                    contentColor = Color.White
                )
            ) { Text("Apply", fontSize = 16.sp) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SelectableChip(label: String, selected: Boolean, onClick: () -> Unit) {
    FilterChip(
        selected = selected, modifier = Modifier.height(38.dp),
        onClick = onClick,
        label = {
            Text(
                label,
                fontSize = 16.sp,
            )
        },
        shape = RoundedCornerShape(50),
        colors = FilterChipDefaults.filterChipColors(
            containerColor = Color.White,
            labelColor = Color(0xFFE21221),
            selectedContainerColor = Color(0xFFE21221),
            selectedLabelColor = Color.White
        ),
        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = selected,
            borderColor = Color(0xFFE21221),
            selectedBorderColor = Color(0xFFE21221),
            borderWidth = 1.dp,
            selectedBorderWidth = 1.dp
        )
    )
}

@Composable
private fun FilterSectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color(0xFF424242),
        modifier = Modifier.padding(horizontal = 20.dp)
    )
    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
private fun ExploreMovieItem(
    rating: Double,
    posterPath: String,
    onClick: () -> Unit,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit
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
                .background(color = Color(0xFFE21221), shape = RoundedCornerShape(6.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                text = String.format(Locale.US, "%.1f", rating),
                color = Color.White,
                fontSize = 12.sp
            )
        }
        IconButton(
            onClick = onFavoriteClick,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(4.dp)
                .size(32.dp)
        ) {
            Icon(
                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                tint = if (isFavorite) Color(0xFFE50914) else Color.White
            )
        }
    }
}


@Composable
fun rememberBottomSheetScrollConnection(): NestedScrollConnection =
    remember { BottomSheetScrollConnection() }

private class BottomSheetScrollConnection : NestedScrollConnection {
    override fun onPostScroll(
        consumed: Offset,
        available: Offset,
        source: NestedScrollSource
    ): Offset = if (available.y < 0f) available else Offset.Zero

    override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity =
        if (available.y < 0f) available else Velocity.Zero
}