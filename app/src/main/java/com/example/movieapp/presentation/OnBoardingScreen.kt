package com.example.movieapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieapp.R

@Composable
fun OnBoardingScreen(onButtonClick: () -> Unit) {
    Box(
        modifier = Modifier.background(
            Brush.verticalGradient(
                colors = listOf(
                    Color.Black,
                    Color(0xFF181A20)
                )
            )
        )
    ) {
        Image(
            painter = painterResource(R.drawable.on_boarding_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Welcome to Mova",
                fontSize = 40.sp,
                color = Color.White,
                fontWeight = FontWeight(700)
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                "The best movie streaming app of the century to make your days great!",
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight(400),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                onClick = onButtonClick, colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE21221),
                    contentColor = Color.White,
                ), modifier = Modifier.size(height = 58.dp, width = 140.dp)
            ) {
                Text(
                    "Get Started", fontSize = 16.sp, textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}