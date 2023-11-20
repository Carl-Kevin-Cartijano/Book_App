package com.thebrownfoxx.petrealm.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    error: String? = null,
    enabled: Boolean = true,
    required: Boolean = false,
) {
    Column(modifier = modifier) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = label?.let {
                {
                    Row {
                        Text(text = it)
                        if (required) Text(text = "*", color = colorScheme.error)
                    }
                }
            },
            enabled = enabled,
            isError = error != null,
            singleLine = true,
        )
        AnimatedVisibility(visible = error != null) {
            Text(
                text = error ?: "",
                color = colorScheme.error,
                style = typography.labelSmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp),
            )
        }
    }
}