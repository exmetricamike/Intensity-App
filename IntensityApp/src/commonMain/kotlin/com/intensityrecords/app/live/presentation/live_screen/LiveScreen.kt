package com.intensityrecords.app.live.presentation.live_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.intensityrecords.app.core.presentation.utils.LocalAppDimens
import com.intensityrecords.app.live.presentation.live_screen.component.LargePlayButton
import com.intensityrecords.app.live.presentation.live_screen.component.LiveTagBadge
import com.intensityrecords.app.live.presentation.live_screen.component.MainVideoPlayerCard
import org.jetbrains.compose.resources.Font
import org.koin.compose.viewmodel.koinViewModel
import com.intensityrecord.resources.Res
import com.intensityrecord.resources.montserrat_bold


@Composable
fun LiveScreenRoot(
    navController: NavController,
    isWideScreen: Boolean,
    viewModel: LiveScreenViewModel = koinViewModel()
) {
    LiveScreen(
        navController = navController,
        isWideScreen = isWideScreen
    )
}

@Composable
fun LiveScreen(navController: NavController, isWideScreen: Boolean) {
    FitnessAppTheme {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkGradient)
        ) {
            val dimens = LocalAppDimens.current

            val contentPadding = if (isWideScreen) 58.dp else 16.dp

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = dimens.horizontalContentPadding,
                        vertical = dimens.verticalContentPadding
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                item {
                    Text(
                        text = "LIVE",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.5.sp,
                        fontFamily = FontFamily(Font(Res.font.montserrat_bold))
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }

                item {
                    MainVideoPlayerCard(isWideScreen)
                }

                item {
                    Spacer(modifier = Modifier.height(32.dp))
                }

                item {
                    // This is your primary action, it should be easily focusable
                    LargePlayButton()
                }
            }

        }
    }
}