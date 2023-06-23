package com.example.planzen.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planzen.R
import com.example.planzen.ui.theme.PlanZenTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    gotoTaskScreen: () -> Unit = {}
) {
    LaunchedEffect(key1 = Unit) {
        delay(3000)

        gotoTaskScreen()
    }
    PlanZenTheme {
        // A surface container using the 'background' color from the theme
        SplashScreenSkeleton()
    }
}

@Preview
@Composable
fun SplashScreenSkeletonPreview() {
    PlanZenTheme {
        // A surface container using the 'background' color from the theme
        SplashScreenSkeleton()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplashScreenSkeleton() {
    Scaffold { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = Color(0xFF19A7CE)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(200.dp),
                painter = painterResource(id = R.drawable.undraw_logo),
                contentDescription = null
            )
            Text(
                modifier = Modifier
                    .padding(top = 4.dp),
                text = "PlanZen",
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}
