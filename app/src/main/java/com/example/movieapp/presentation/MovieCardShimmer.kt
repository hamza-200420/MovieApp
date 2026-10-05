package com.example.movieapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MovieCardShimmer(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.shimmer(cornerRadius = 12.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .size(width = 36.dp, height = 24.dp)
                .shimmer(cornerRadius = 6.dp)
        )
    }
}