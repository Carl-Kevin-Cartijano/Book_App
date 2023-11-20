package com.thebrownfoxx.petrealm.ui.components

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.thebrownfoxx.components.extension.plus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchableLazyColumnScreen(
    topBarExpandedContent: @Composable BoxScope.() -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onNavigateUp: (() -> Unit)? = null,
    background: @Composable BoxScope.() -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    contentPadding: PaddingValues = PaddingValues(),
    content: LazyListScope.() -> Unit,
) {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    val lazyListState = rememberLazyListState()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        floatingActionButton = floatingActionButton,
        bottomBar = bottomBar,
        topBar = {
            SearchTopBar(
                searchQuery = searchQuery,
                onSearchQueryChange = onSearchQueryChange,
                onNavigateUp = onNavigateUp,
                background = background,
                scrollBehavior = scrollBehavior,
                content = topBarExpandedContent,
            )
        },
    ) { scaffoldContentPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            content = content,
            contentPadding = scaffoldContentPadding + contentPadding,
            state = lazyListState,
        )
    }

    LaunchedEffect(searchQuery) {
        lazyListState.animateScrollToItem(0)
    }
}