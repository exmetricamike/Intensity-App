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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.intensityrecord.core.presentation.DarkGradient
import com.intensityrecord.core.presentation.FitnessAppTheme
import com.intensityrecord.resources.Res
import com.intensityrecord.resources.montserrat_bold
import com.intensityrecords.app.core.presentation.Title
import com.intensityrecords.app.core.presentation.utils.LocalAppDimens
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
fun MobilityScreen(navController: NavController, isWideScreen: Boolean) {
    FitnessAppTheme {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkGradient)
        ) {

            val dimens = LocalAppDimens.current


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = dimens.horizontalContentPadding, vertical = 2.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "MOBILITY & RECOVERY",
                    fontFamily = FontFamily(Font(Res.font.montserrat_bold)),
                    style = Title
                )

                Spacer(modifier = Modifier.height(20.dp))

                val gridWidth = if (isWideScreen) 0.85f else 1f

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(if (isWideScreen) 25.dp else 16.dp),
                    verticalArrangement = Arrangement.spacedBy(if(isWideScreen) 45.dp else 24.dp),
                    modifier = Modifier.fillMaxWidth(gridWidth).weight(1f),
                    contentPadding = PaddingValues(
                        top = 16.dp,
                        bottom = 120.dp, // This replaces the external Spacer
                        start = 4.dp,
                        end = 4.dp
                    )
                ) {
                    items(mobilityCategories) { item ->
                        MobilityCard(item = item, isWideScreen = isWideScreen, dimens = dimens)
                        Spacer(modifier = Modifier.height(40.dp))
                    }
                }
            }
        }
    }
}