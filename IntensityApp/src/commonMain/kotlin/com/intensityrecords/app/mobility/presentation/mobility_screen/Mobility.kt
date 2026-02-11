package com.intensityrecords.app.mobility.presentation.mobility_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.intensityrecord.core.presentation.DarkGradient
import com.intensityrecord.core.presentation.FitnessAppTheme
import com.intensityrecord.resources.Res
import com.intensityrecord.resources.montserrat_bold
import com.intensityrecords.app.mobility.domain.mobilityCategories
import com.intensityrecords.app.mobility.presentation.mobility_screen.component.MobilityCard
import org.jetbrains.compose.resources.Font
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun MobilityScreenRoot(
    navController: NavController,
    isWideScreen: Boolean,
    viewModel: MobilityScreenViewModel = koinViewModel()
) {
    MobilityScreen(
        navController = navController,
        isWideScreen = isWideScreen
    )
}

@Composable
fun MobilityScreen(navController: NavController,isWideScreen: Boolean) {
    FitnessAppTheme {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkGradient)
        ) {
            val contentPadding = if (isWideScreen) 58.dp else 16.dp

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = contentPadding, vertical = 14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "MOBILITY & RECOVERY",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp,
                    fontFamily = FontFamily(Font(Res.font.montserrat_bold))
                )

                Spacer(modifier = Modifier.height(20.dp))

                if (isWideScreen) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(24.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp),
                        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 16.dp),
                        modifier = Modifier.fillMaxWidth(0.85f)
                    ) {
                        items(mobilityCategories) { item ->
                            MobilityCard(item = item, isWideScreen = isWideScreen)
                        }
                    }

                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(1),
                        horizontalArrangement = Arrangement.spacedBy(24.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp),
                        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 16.dp),
                        modifier = Modifier.fillMaxWidth(0.85f)
                    ) {
                        items(mobilityCategories) { item ->
                            MobilityCard(item = item, isWideScreen = isWideScreen)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

            }

        }
    }
}