package com.thebrownfoxx.petrealm.ui.screens.owners.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.petrealm.models.Owner
import com.thebrownfoxx.petrealm.models.Sample
import com.thebrownfoxx.petrealm.ui.theme.AppTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeableOwnerCard(
    owner: Owner,
    expanded: Boolean,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onInitiateRemove: suspend () -> Boolean,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
) {
    val dismissState = rememberDismissState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(dismissState.currentValue) {
        if (dismissState.currentValue != DismissValue.Default) {
            val removed = onInitiateRemove()
            if (!removed) dismissState.reset()
        }
    }

    SwipeToDismiss(
        modifier = modifier,
        state = dismissState,
        background = {},
        dismissContent = {
            OwnerCard(
                owner = owner,
                expanded = expanded,
                onClick = onClick,
                onEdit = onEdit,
                onRemove = {
                    scope.launch {
                        dismissState.dismiss(DismissDirection.StartToEnd)
                        if (dismissState.currentValue != DismissValue.Default) {
                            val removed = onInitiateRemove()
                            if (!removed) dismissState.reset()
                        }
                    }
                },
                modifier = Modifier.padding(contentPadding),
            )
        },
    )
}

@Preview
@Composable
fun SwipeableOwnerPreview() {
    AppTheme {
        SwipeableOwnerCard(
            owner = Sample.Owner,
            expanded = false,
            onClick = {},
            onEdit = {},
            onInitiateRemove = { true },
            contentPadding = PaddingValues(16.dp),
        )
    }
}