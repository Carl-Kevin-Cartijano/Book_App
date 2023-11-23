package com.thebrownfoxx.petrealm.ui.screens.pets.components

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
import com.thebrownfoxx.petrealm.models.Pet
import com.thebrownfoxx.petrealm.models.Sample
import com.thebrownfoxx.petrealm.ui.components.PetCard
import com.thebrownfoxx.petrealm.ui.theme.AppTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeablePetCard(
    pet: Pet,
    expanded: Boolean,
    onClick: () -> Unit,
    onInitiateAdopt: () -> Unit,
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
            PetCard(
                pet = pet,
                expanded = expanded,
                onClick = onClick,
                onAdopt = onInitiateAdopt,
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
fun PetCardPreview() {
    AppTheme {
        SwipeablePetCard(
            pet = Sample.Pet,
            expanded = true,
            onClick = {},
            onInitiateAdopt = {},
            onEdit = {},
            onInitiateRemove = { true },
            contentPadding = PaddingValues(16.dp),
        )
    }
}