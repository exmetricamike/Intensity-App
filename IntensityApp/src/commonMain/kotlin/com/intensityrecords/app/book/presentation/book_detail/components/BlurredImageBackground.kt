package com.intensityrecord.book.presentation.book_detail.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.intensityrecord.core.presentation.DarkBlue
import com.intensityrecord.core.presentation.DesertWhite
import com.intensityrecord.core.presentation.PulseAnimation
import com.intensityrecord.core.presentation.SandYellow
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import com.intensityrecord.resources.*

@Composable
fun BlurredImageBackground(
    imageUrl: String?,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    // remember tells Compose to keep the value across recompositions of a composable.
    // without it, the value would reset every time the UI recomposes.
    // Create a state variable that survives recomposition, automatically updates the UI when it changes, and lets me read/write it like a normal variable.
    var imageLoadResult by remember {
        // mutableStateOf creates an observable state holder. When the value inside changes, Compose automatically re-renders any UI reading that value.
        mutableStateOf<Result<Painter>?>(null)
    }
    val painter = rememberAsyncImagePainter(
        model = imageUrl,
        onSuccess = {
            val size = it.painter.intrinsicSize
            imageLoadResult = if (size.width > 1 && size.height > 1) {
                Result.success(it.painter)
            } else {
                Result.failure(Exception("Invalid image dimensions"))
            }
        },
        onError = {
            it.result.throwable.printStackTrace()
        }
    )

    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
//                .border(
//                    width = 5.dp,
//                    color = Color.Magenta,
//                )
        )

        // LAMBDA for the upper part of the column

        {
            // Blurred image upper part of the column
            Box(
                modifier = Modifier
                    .weight(0.3f)
                    .fillMaxWidth()
                    .background(DarkBlue) // only if there is no image
            ) {
                Image(
                    painter = painter, //only if the image exists
                    contentDescription = stringResource(Res.string.book_cover),
                    contentScale = ContentScale.Crop, // crop the top and bottom so the image fits in width
                    modifier = Modifier
                        .fillMaxSize()
                        .blur(20.dp)
                )
            }

            // 70% of the screen is the content box
            Box(
                modifier = Modifier
                    .weight(0.7f)
                    .fillMaxWidth()
                    .background(DesertWhite)
//                    .border(
//                        width = 5.dp,
//                        color = Color.Red,
//                    )
            )
        }


        // -------------------------------------------------------
        // Go back button (this is still the container box)
        // -------------------------------------------------------
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 16.dp, start = 16.dp)
                .statusBarsPadding()
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(Res.string.go_back),
                tint = Color.White
            )
        }

        // this is the column that contains the book cover and it has a spacer to offset the book cover
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
//                .border(
//                width = 5.dp,
//                color = Color.Blue,
//            )
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.15f))
            ElevatedCard(
                modifier = Modifier
                    .height(230.dp)
                    .aspectRatio(2 / 3f),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.elevatedCardElevation(
                    defaultElevation = 15.dp
                )
            ) {
                AnimatedContent(
                    targetState = imageLoadResult
                ) { result ->
                    when (result) {
                        null -> Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            PulseAnimation(
                                modifier = Modifier
                                    .size(60.dp)
                            )
                        }

                        else -> {
                            Box {
                                Image(
                                    painter = if (result.isSuccess) painter else {
                                        painterResource(Res.drawable.book_error_2)
                                    },
                                    contentDescription = stringResource(Res.string.book_cover),
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.Transparent),
                                    contentScale = if (result.isSuccess) {
                                        ContentScale.Crop
                                    } else {
                                        ContentScale.Fit
                                    }
                                )
                                IconButton(
                                    onClick = onFavoriteClick,
                                    modifier = Modifier
                                        .align(Alignment.BottomEnd)
                                        .background(
                                            brush = Brush.radialGradient(
                                                colors = listOf(
                                                    SandYellow, Color.Transparent
                                                ),
                                                radius = 70f
                                            )
                                        )
                                ) {
                                    Icon(
                                        imageVector = if (isFavorite) {
                                            Icons.Filled.Favorite
                                        } else {
                                            Icons.Outlined.FavoriteBorder
                                        },
                                        tint = Color.Red,
                                        contentDescription = if (isFavorite) {
                                            stringResource(Res.string.remove_from_favorites)
                                        } else {
                                            stringResource(Res.string.mark_as_favorite)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
            content()
        }
    }
}