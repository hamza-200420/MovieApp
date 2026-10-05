package com.example.movieapp.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun DetailsScreenShimmer(
    modifier: Modifier = Modifier,
    castCount: Int = 6
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .shimmer(cornerRadius = 0.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 20.dp, end = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(28.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .shimmer(cornerRadius = 8.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .shimmer(cornerRadius = 50.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .shimmer(cornerRadius = 50.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, start = 20.dp, end = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(18.dp)
                    .clip(CircleShape)
                    .shimmer(cornerRadius = 50.dp)
            )
            Box(
                modifier = Modifier
                    .width(36.dp)
                    .height(14.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmer(cornerRadius = 4.dp)
            )
            Box(
                modifier = Modifier
                    .width(24.dp)
                    .height(14.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmer(cornerRadius = 4.dp)
            )
            Box(
                modifier = Modifier
                    .width(48.dp)
                    .height(20.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .shimmer(cornerRadius = 8.dp)
            )
            Box(
                modifier = Modifier
                    .width(48.dp)
                    .height(20.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .shimmer(cornerRadius = 8.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp)
                    .clip(RoundedCornerShape(50))
                    .shimmer(cornerRadius = 50.dp)
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp)
                    .clip(RoundedCornerShape(50))
                    .shimmer(cornerRadius = 50.dp)
            )
        }

        Box(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .width(180.dp)
                .height(12.dp)
                .clip(RoundedCornerShape(4.dp))
                .shimmer(cornerRadius = 4.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(3) { index ->
                val lineWidth = when (index) {
                    0 -> 1f
                    1 -> 0.95f
                    else -> 0.6f
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth(lineWidth)
                        .height(12.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .shimmer(cornerRadius = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            userScrollEnabled = false
        ) {
            items(castCount) {
                Row(modifier = Modifier.height(60.dp)) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .shimmer(cornerRadius = 50.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(
                        modifier = Modifier
                            .width(60.dp)
                            .padding(top = 5.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .shimmer(cornerRadius = 3.dp)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .height(10.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .shimmer(cornerRadius = 3.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            repeat(3) { index ->
                val w = when (index) {
                    0 -> 70.dp
                    1 -> 100.dp
                    else -> 70.dp
                }
                Box(
                    modifier = Modifier
                        .width(w)
                        .height(16.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .shimmer(cornerRadius = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(150.dp)
                    .height(112.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .shimmer(cornerRadius = 8.dp)
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(16.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmer(cornerRadius = 4.dp)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}