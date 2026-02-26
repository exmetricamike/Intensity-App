package com.intensityrecords.app.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Spa
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.intensityrecord.core.presentation.TextWhite
import intensityrecordapp.intensityapp.generated.resources.Res
import intensityrecordapp.intensityapp.generated.resources.app_header
import intensityrecordapp.intensityapp.generated.resources.app_header_1
import org.jetbrains.compose.resources.stringResource

@Composable
fun AppHeader(
    isWideScreen: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Rounded.Spa,
            contentDescription = "Logo",
            tint = TextWhite,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(Res.string.app_header),
                style = MaterialTheme.typography.headlineMedium.copy(color = Color.Gray),
            )
            Text(
                text = stringResource(Res.string.app_header_1),
                style = MaterialTheme.typography.headlineMedium,
            )
            if (!isWideScreen)
                Spacer(modifier = Modifier.height(18.dp))
        }
    }
}
