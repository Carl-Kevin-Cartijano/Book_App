package com.thebrownfoxx.petrealm.ui.components

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.material.icons.twotone.Clear
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.components.ExpandedTopAppBar
import com.thebrownfoxx.components.IconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onNavigateUp: (() -> Unit)? = null,
    background: @Composable BoxScope.() -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null,
    content: @Composable BoxScope.() -> Unit,
) {
    ExpandedTopAppBar(
        modifier = modifier,
        pinnedContent = {
            TextField(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .fillMaxWidth(),
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                placeholder = { Text(text = "Search") },
                singleLine = true,
                textStyle = typography.bodyLarge,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                ),
                leadingIcon = {
                    if (onNavigateUp != null) {
                        IconButton(
                            imageVector = Icons.TwoTone.ArrowBack,
                            contentDescription = null,
                            onClick = onNavigateUp,
                        )
                    } else {
                        Icon(
                            imageVector = Icons.TwoTone.Search,
                            contentDescription = null,
                        )
                    }
                },
                trailingIcon = {
                    if (searchQuery != "") {
                        IconButton(
                            imageVector = Icons.TwoTone.Clear,
                            contentDescription = null,
                            onClick = { onSearchQueryChange("") },
                        )
                    }
                }
            )
        },
        background = background,
        scrollBehavior = scrollBehavior,
        pinCollapsedContent = true,
        content = content,
    )
}