package com.thebrownfoxx.petrealm.ui.screens.pets.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.components.FilledButton
import com.thebrownfoxx.petrealm.models.Pet
import com.thebrownfoxx.petrealm.models.Sample
import com.thebrownfoxx.petrealm.ui.theme.AppTheme

@Composable
fun PetCard(
    pet: Pet,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Text(text = "Name: ${pet.name}")
            Text(text = "Type: ${pet.type}")
            Text(text = "Age: ${pet.age}")
            if (pet.owner != null) Text(text = "Owner: ${pet.owner.name}")
            FilledButton(text = "Remove", onClick = onRemove)
        }
    }
}

@Preview
@Composable
fun PetCardPreview() {
    AppTheme {
        PetCard(
            pet = Sample.Pet,
            onRemove = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}