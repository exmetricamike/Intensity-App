package com.intensityrecord.book.presentation.book_list.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.intensityrecord.core.presentation.DarkBlue
import com.intensityrecord.core.presentation.DesertWhite
import com.intensityrecord.core.presentation.SandYellow
import org.jetbrains.compose.resources.stringResource
import com.intensityrecord.resources.*

@Composable
fun BookSearchBar(
    searchQuery: String,
    /*onSearchQueryChange is a callback that gets called whenever the user types or updates the search field.
    It passes the updated String (the query) to the lambda.*/
    onSearchQueryChange: (String) -> Unit, // onSearchQuery lambda so when it changes it can bubble up the new query search to the parent composable
    onImeSearch: () -> Unit, // trigger search when there is a click on the search on the keyboard
    modifier: Modifier = Modifier
) {
    // This block of code is used to customize the appearance of text selection
    // within the Composable elements that are children of this CompositionLocalProvider.
    /* CompositionLocalProvider: Jetpack Compose mechanism that allows you to pass data down the Composable tree implicitly.
    Instead of passing parameters explicitly through every Composable function, you can "provide" a value at a higher level,
    and any Composable at a lower level in that part of the UI tree can "consume" or use that value.*/
    CompositionLocalProvider(
        // LocalTextSelectionColors is a CompositionLocal that holds the current TextSelectionColors
        // used by text fields and selectable text. By providing a new value here, we are overriding
        // the default selection colors Composables wrapped by this provider.
        LocalTextSelectionColors provides TextSelectionColors(
            handleColor = SandYellow,
            backgroundColor = SandYellow
        )
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            shape = RoundedCornerShape(100),
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = DarkBlue,
                focusedBorderColor = SandYellow
            ),
            placeholder = {
                Text(text = stringResource(Res.string.search_hint))
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.66f)
                )
            },
            singleLine = true,
            keyboardActions = KeyboardActions(
                onSearch = { onImeSearch() }
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            trailingIcon = {
                AnimatedVisibility(
                    visible = searchQuery.isNotBlank()
                ) {
                    IconButton(onClick = {
                        onSearchQueryChange("")
                    }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = stringResource(Res.string.close_hint), tint = MaterialTheme.colorScheme.onSurface)
                    }
                }
            },
            modifier = modifier.background(
                shape = RoundedCornerShape(100),
                color = DesertWhite
            ).minimumInteractiveComponentSize()
        )
    }
}