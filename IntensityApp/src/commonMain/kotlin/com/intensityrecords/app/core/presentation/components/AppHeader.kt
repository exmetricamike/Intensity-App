package com.intensityrecords.app.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Spa
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.intensityrecord.core.presentation.TextWhite
import intensityrecordapp.intensityapp.generated.resources.Res
import intensityrecordapp.intensityapp.generated.resources.montserrat_bold
import org.jetbrains.compose.resources.Font

@Composable
fun AppHeader(
    modifier: Modifier = Modifier,
    isWideScreen: Boolean
) {
    Row(
        modifier = if (isWideScreen) modifier else Modifier.fillMaxWidth(),
        horizontalArrangement = if (isWideScreen) Arrangement.Start else Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Rounded.Spa,
            contentDescription = "Logo",
            tint = TextWhite,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(horizontalAlignment = if (isWideScreen) Alignment.Start else Alignment.CenterHorizontally) {
            Text(
                text = "VAN DER VALK",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    letterSpacing = 1.sp,
                    fontFamily = FontFamily(Font(Res.font.montserrat_bold))
                ),
                color = Color.Gray
            )
            Text(
                text = "HOTEL WATERLOO",
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    letterSpacing = 0.5.sp,
                    fontFamily = FontFamily(Font(Res.font.montserrat_bold))
                ),
                color = TextWhite
            )
            if (!isWideScreen)
                Spacer(modifier = Modifier.height(18.dp))
        }
    }
}


@Composable
fun TopNavigationLayout(
    isWideScreen: Boolean,
    currentTab: String, // Replace Any? with your actual Tab type
    navController: NavHostController // Replace Any with your actual NavController type
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 24.dp) // Add padding for aesthetics
    ) {
        // 1. The Navigation Bar (Exact Center)
        Box(
            modifier = Modifier.align(Alignment.Center)
        ) {
            CustomBottomBar(
                isWideScreen = isWideScreen,
                currentTab = currentTab,
                navController = navController
            )
        }

        // 2. The App Header (Right Side)
        AppHeader(
            modifier = Modifier.align(Alignment.CenterStart),
            isWideScreen = isWideScreen
        )
    }
}
