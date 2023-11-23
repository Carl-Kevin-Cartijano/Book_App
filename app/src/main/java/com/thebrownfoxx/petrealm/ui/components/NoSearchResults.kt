package com.thebrownfoxx.petrealm.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.SearchOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.components.VerticalSpacer
import com.thebrownfoxx.petrealm.ui.theme.AppTheme

@Composable
fun NoSearchResults(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize(),
    ) {
        Icon(
            imageVector = Icons.TwoTone.SearchOff,
            contentDescription = null,
            modifier = Modifier.size(48.dp),
        )
        VerticalSpacer(height = 8.dp)
        Text(
            text = "No results found",
            style = typography.titleLarge,
        )
    }
}

@Preview
@Composable
fun NoSearchResultsPreview() {
    AppTheme {
        NoSearchResults()
    }
}