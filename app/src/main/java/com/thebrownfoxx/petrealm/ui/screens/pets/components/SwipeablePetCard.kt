package com.thebrownfoxx.petrealm.ui.screens.pets.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DismissValue.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.petrealm.models.Pet
import com.thebrownfoxx.petrealm.models.Sample
import com.thebrownfoxx.petrealm.ui.components.PetCard
import com.thebrownfoxx.petrealm.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeablePetCard(
    pet: Pet,
    onInitiateRemove: suspend () -> Boolean,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
) {
    val dismissState = rememberDismissState()

    LaunchedEffect(dismissState.currentValue) {
        if (dismissState.currentValue != Default) {
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
            onInitiateRemove = { true },
            contentPadding = PaddingValues(16.dp),
        )
    }
}